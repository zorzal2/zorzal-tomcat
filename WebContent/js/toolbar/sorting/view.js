var View; 
/**
	(* Vista para la pantalla de ordenamiento. *)

	Requiere
	
		js/toolbar/utils.js
		js/toolbar/classes.js
		js/toolbar/sorting/model.js
	
	Metodos publicos:
	
		attachModel(aModel)
		refresh()
		refreshList()
		addSortCriterion(sortCriterion, sortCriterionId) 
		removeSortCriterion(sortCriterionId)
		selectedColumn() : columnId
		
	Notas:
		
		Codigo correspondiente al archivo jsp/toolbar/sorting.jsp

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
		drawSortCriteria();
	}
	this.refreshList = function() {
		html.load();
		drawSortCriteria();
	}
	
	this.addSortCriterion = function(sortCriterion, sortCriterionId) {
		baseAddSortCriterion(sortCriterion, sortCriterionId);
	}
	this.removeSortCriterion = function(sortCriterionId) {
		html.sortCriterionRows.removeChild(document.getElementById('sortCriterionRow'+sortCriterionId));
	}
	this.selectedColumn = function() {
		return html.columnList.value;
	}
	//private
	var model = null;
	var html = {
		load: function() {
			this.sortCriterionRowPrototype = document.getElementById('sortCriterionRowPrototype');
			this.sortCriterionRows = document.getElementById('sortCriterionRows');
			this.columnList = document.getElementById('columnList');
			this.sortCriterionRows.removeChild(this.sortCriterionRowPrototype);
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
	function drawSortCriteria() {
		//borro lo que encuentre
		while(html.sortCriterionRows.hasChildNodes()) {
			html.sortCriterionRows.removeChild(html.sortCriterionRows.firstChild);
		}
		//pueblo la tabla
		var sortCriteria = model.sortCriteria();
		for(var i=0; i<sortCriteria.length; i++) {
			baseAddSortCriterion(sortCriteria[i], model.getSortCriterionId(sortCriteria[i]));
		}
	}
	function baseAddSortCriterion(sortCriterion, id) {
		//Replico la fila prototipica
		var newRow = html.sortCriterionRowPrototype.cloneNode(true);
		newRow.style.display = "";
		newRow.id = 'sortCriterionRow'+id;
		html.sortCriterionRows.appendChild(newRow);
		//Completo la fila diferidamente por compatibilidad
		//con los browsers que tardan en aplicar los cambios
		CallLater(
			function() {
				//boton de eliminacion
				var newSortCriterion_delete = document.getElementById('newSortCriterion_delete');
				newSortCriterion_delete.sortCriterionId = id;
				newSortCriterion_delete.id = 'sortCriterionRow'+id+'_delete';
				//boton de push up
				var newSortCriterion_pushUp = document.getElementById('newSortCriterion_pushUp');
				newSortCriterion_pushUp.sortCriterionId = id;
				newSortCriterion_pushUp.id = 'sortCriterionRow'+id+'_pushUp';
				//boton de pull down
				var newSortCriterion_pullDown = document.getElementById('newSortCriterion_pullDown');
				newSortCriterion_pullDown.sortCriterionId = id;
				newSortCriterion_pullDown.id = 'sortCriterionRow'+id+'_delete';
				//completo la columna
				var newSortCriterion_column = document.getElementById('newSortCriterion_column');
				newSortCriterion_column.innerHTML = sortCriterion.column.description;
				newSortCriterion_column.id = 'sortCriterionRow'+id+'_column';
				//completo los tipos de orden
				var newSortCriterion_sortOptions = document.getElementById('newSortCriterion_sortOptions');
				newSortCriterion_sortOptions.sortCriterionId = id;
				newSortCriterion_sortOptions.value = SortOption.forName(sortCriterion.sortOption);
				newSortCriterion_sortOptions.id = 'sortCriterionRow'+id+'_sortOptions';
			}
		);
	}
}