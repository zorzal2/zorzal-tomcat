var View; 
/**
	(* Vista para la pantalla de filtros. *)

	Requiere
	
		js/toolbar/utils.js
		js/toolbar/classes.js
		js/toolbar/filters/model.js
	
	Metodos publicos:
	
		attachModel(aModel)
		refresh()
		addFilter(filter, filterId) 
			- 	Notar que el id de un filtro no es una propiedad propia
				sino contextual.
		removeFilter(filterId)
		selectedColumn() : columnId
		selectedFilterValue(filterId) : String
		
	Notas:
		
		Codigo correspondiente al archivo jsp/toolbar/filters.jsp

*/

View = new function() {
	//public	
	this.attachModel = function(aModel) {
		model = aModel;
		this.refresh();
	}
	this.refresh = function() {
		html.load();
		fillCombo();
		drawFilters();
	}
	
	this.addFilter = function(filter, filterId) {
		baseAddFilter(filter, filterId);
	}
	this.removeFilter = function(filterId) {
		html.filterRows.removeChild(document.getElementById('filterRow'+filterId));
	}
	this.selectedColumn = function() {
		return html.columnList.value;
	}
	this.selectedFilterValue = function(filterId) {
		return document.getElementById('filterRow'+filterId+'_value').value;
	}
	//private
	var model = null;
	var html = {
		load: function() {
			this.filterRowPrototype = document.getElementById('filterRowPrototype');
			this.filterRows = document.getElementById('filterRows');
			this.columnList = document.getElementById('columnList');
			this.filterRows.removeChild(this.filterRowPrototype);
			this.load = function(){}
		}
	}
	function fillCombo() {
		//limpio lo que haya
		while(html.columnList.hasChildNodes()) {
			html.columnList.removeChild(html.columnList.firstChild);
		}
		var columns = model.columnMap();
		for(var columnId in columns) {
			var option = document.createElement("OPTION");
			option.text=columns[columnId].description;
			option.value=columnId;
			html.columnList.options.add(option);
		}
	}
	function drawFilters() {
		//borro lo que encuentre
		while(html.filterRows.hasChildNodes()) {
			html.filterRows.removeChild(html.filterRows.firstChild);
		}
		//pueblo la tabla
		var filters = model.filterMap();
		for(var filterId in filters) {
			baseAddFilter(filters[filterId], filterId);
		}
	}
	function baseAddFilter(filter, id) {
		//Replico la fila prototipica
		var newRow = html.filterRowPrototype.cloneNode(true);
		newRow.style.display = "";
		newRow.id = 'filterRow'+id;
		html.filterRows.appendChild(newRow);
		//Completo la fila diferidamente por compatibilidad
		//con los browsers que tardan en aplicar los cambios
		CallLater(
			function() {
				//completo la columna
				var newFilter_delete = document.getElementById('newFilter_delete');
				newFilter_delete.filterId = id;
				newFilter_delete.id = 'filterRow'+id+'_delete';
				var newFilter_column = document.getElementById('newFilter_column');
				newFilter_column.innerHTML = filter.column.description;
				newFilter_column.id = 'filterRow'+id+'_column';
				//completo los tipos de filtro
				var newFilter_filterTypes = document.getElementById('newFilter_filterTypes');
				newFilter_filterTypes.filterId = id;
				newFilter_filterTypes.id = 'filterRow'+id+'_filterTypes';
				var supportedFilterTypes = filter.column.supportedFilterTypes;
				for(var i=0; i<supportedFilterTypes.length; i++) {
					var filterType = supportedFilterTypes[i];
				
					var option = document.createElement("OPTION");
					option.text=filterType.description;
					option.value=filterType.id;
					newFilter_filterTypes.options.add(option);
				}
				newFilter_filterTypes.value = filter.filterType.id;
				//completo el valor
				var newFilter_value = document.getElementById('newFilter_value');
				newFilter_value.id = 'filterRow'+id+'_value';
				newFilter_value.filterId = id;
				if(filter.filterType.filterValueDataType==FilterValueDataType.DATE)  {
					//Es una fecha. formatear.
					if(filter.value.constructor==Date)
						newFilter_value.value = formatDate(filter.value);
				} else newFilter_value.value = filter.value;
				//Configuro el calendario
				//TODO chequear que el calendario sea necesario
				var newFilter_CalendarButton = document.getElementById('newFilter_CalendarButton');
				newFilter_CalendarButton.id = newFilter_value.id+'Button';
				var newFilter_CalendarContainer = document.getElementById('newFilter_CalendarContainer');
				newFilter_CalendarContainer.id = newFilter_value.id+'CalendarContainer';
				
				if(filter.filterType.filterValueDataType==FilterValueDataType.DATE) {
					newFilter_CalendarButton.inputId = newFilter_value.id;
					newFilter_CalendarButton.style.display = '';
				}

			}
		);
	}
}