var Model; 
/**
	(* Modelo para la pantalla de filtros. *)
	
	Requiere

		js/toolbar/classes.js

	Metodos publicos:
	
		setUserId(userId)
		getUserId() : userId
		setToolbarId(toolbarId)
		getToolbarId() : toolbarId
		addColumn(column)
		removeColumn(column)
		addFilter(filter) : filterId
		removeFilter(filter)
		removeFilterById(filterId)
		getFilterId(filter) : filterId
		filterMap() : Diccionario(filterId -> filter)
		filterMap(filterId) : filter
		columnMap() : Diccionario(columnId -> column)
		columnMap(columnId) : column
		
	Notas:
		
		Codigo correspondiente al archivo jsp/toolbar/filters.jsp

		Diccionario(X -> Y) denota un objeto javascript
		con Xs como propiedades y Ys como valores.
*/

Model = new function() {
	//private
	var columnMap = {};
	var filterMap = {};
	
	var userId;
	var toolbarId;
	
	//puiblic
	this.getUserId = function() {
		return userId;
	}
	this.setUserId = function(userid) {
		userId = userid;
	}
	this.getToolbarId = function() {
		return toolbarId;
	}
	this.setToolbarId = function(toolbarid) {
		toolbarId = toolbarid;
	}

	this.addColumn = function(column) {
		columnMap[column.id] = column;
	}
	this.removeColumn = function(column) {
		delete columnMap[column.id];
	}
	
	this.addFilter = function(filter) {
		var id = newFilterId();
		filterMap[id] = filter;
		return id;
	}
	this.removeFilter = function(filter) {
		this.removeFilterById(this.getFilterById(filter));
	}
	this.removeFilterById = function(filterId) {
		delete filterMap[filterId];
	}
	this.getFilterId = function(filter) {
		for(var id in filterMap) {
			if(filterMap[id]==filter) return id;
		}
		return null;
	}
	this.filterMap = function(optId) {
		if(optId) return filterMap[optId];
		return filterMap;
	}
	this.columnMap = function(optId) {
		if(optId) return columnMap[optId];
		else return columnMap;
	}

	//private
	var	lastFilterId =0;
	function newFilterId() {
		return ++lastFilterId;
	}
}