var Controller;
/**
	(* Controlador para la pantalla de filtros. *)

	Requiere

		js/toolbar/utils.js
		js/toolbar/classes.js
		js/toolbar/filters/model.js
		js/toolbar/filters/view.js
	
	Metodos publicos:
	
		attachView(aView)
		attachModel(aModel)
		newFilter()
		removeFilter(filterId)
		changeFilterType(filterId, newFilterTypeId)
		changeFilterValue(filterId, newValue)
		submit()
		
	Notas:
		
		Codigo correspondiente al archivo jsp/toolbar/filters.jsp

*/

Controller = new function() {
	var model = null;
	var view = null;
	
	this.attachView = function(aView) {
		view = aView;
		if(model) view.attachModel(model);
	}
	this.attachModel = function(aModel) {
		model = aModel;
		if(view) view.attachModel(model);
	}
	
	this.newFilter = function() {
		var column = model.columnMap(view.selectedColumn());
		var filter = new Filter(
			column, //columna: seleccionada
			column.supportedFilterTypes[0], 	//tipo de filtro: default.
			''			//valor: vacio
			);
		var filterId = model.addFilter(filter);
		//actualizo la vista
		view.addFilter(filter, filterId);
	}
	this.removeFilter = function(filterId) {
		model.removeFilterById(filterId);
		view.removeFilter(filterId);
	}
	this.changeFilterType = function(filterId, newFilterTypeId) {
		if(!filterId) alert('error')
		model.filterMap(filterId).filterType = FilterType[newFilterTypeId];
	}
	this.changeFilterValue = function(filterId, newValue) {
		var filter = model.filterMap(filterId);
		if(filter.filterType.filterValueDataType==FilterValueDataType.DATE) {
			filter.value = parseDate(newValue) || "Invalid Date";
		} else {
			if(filter.filterType.filterValueDataType==FilterValueDataType.BOOLEAN) {
				filter.value = parseBoolean(newValue) || "Invalid Boolean";
			} else {
				filter.value = newValue;
			}
		}
	}
	this.submit = function() {
		this.synchronizeValues();
		if(this.validate()) {
			request("ToolbarFilterSetAll.do", {filtersXML: this.encode()});
		}
	}
	this.validate = function() {
		for(var filterId in model.filterMap()) {
			var filter = model.filterMap(filterId);
			if(filter.value==null || filter.value=='') {
				ShowAlert("validation_required", {labelName: filter.column.description});
				return false;
			}
			
			var filterDataType = filter.filterType.filterValueDataType;
			if(
				filterDataType == FilterValueDataType.DATE &&
				!(filter.value.constructor==Date)
			) {
				ShowAlert("validation_date_format", {labelName: filter.column.description});
				return false;
			}

			if(
				filterDataType == FilterValueDataType.LONG &&
				!isLong(filter.value)
			) {
				ShowAlert("validation_number_format", {labelName: filter.column.description});
				return false;
			}
			if(
				filterDataType == FilterValueDataType.BOOLEAN &&
				!isBoolean(filter.value)
			) {
				ShowAlert("validation_boolean_format", {labelName: filter.column.description});
				return false;
			}

			if(
				filterDataType == FilterValueDataType.NUMBER &&
				!isNumber(filter.value)
			) {
				ShowAlert("validation_number_format", {labelName: filter.column.description});
				return false;
			}
		}
		return true;
	}
	//Sincroniza el modelo con la vista. Necesario porque el evento onchange de HTML
	//no se dispara ante una modificacion por codigo.
	this.synchronizeValues = function() {
		filters = model.filterMap();
		for(var filterId in model.filterMap()) {
			var filter = filters[filterId];
			
			if(filter.filterType.filterValueDataType==FilterValueDataType.DATE) {
				filter.value = parseDate(view.selectedFilterValue(filterId));
			} else {
				filter.value = view.selectedFilterValue(filterId);
			}
			
			//Aca podria chequearse tambien el filterType
		}
	}
	this.encode = function() {
		function encodeFilter(filterId) {
			var template = 
				'    <filter\n'+
				'        column-id="%filterColumnId%"\n'+
				'        filter-type-id="%filterTypeId%">%filterValue%</filter>';
			var filter = model.filterMap(filterId);

			var realValue;
			if(filter.filterType.filterValueDataType==FilterValueDataType.DATE) {
				realValue = filter.value.getTime();
			} else {
				realValue = filter.value;
			}

			return apply(template, {
				filterColumnId: filter.column.id,
				filterTypeId: filter.filterType.id,
				filterValue: escapeXML(realValue)
			})
		}
		
		var template = 
			'<filters user-id="%userId%" toolbar-id="%toolbarId%">\n'+
			'%filters%\n'+
			'</filters>';
		var filters = [];
		for(var filterId in model.filterMap()) {
			filters.push(encodeFilter(filterId));
		}
		return apply(template, {
			userId: model.getUserId(),
			toolbarId: model.getToolbarId(),
			filters: filters.join('\n')
		});
	}
}
