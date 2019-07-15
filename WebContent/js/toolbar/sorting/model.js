var Model; 
/**
	(* Modelo para la pantalla de ordenamiento. *)
	
	Requiere

		js/toolbar/classes.js

	Metodos publicos:
	
		setUserId(userId)
		getUserId() : userId
		setToolbarId(toolbarId)
		getToolbarId() : toolbarId
		addColumn(column)
		removeColumn(column)
		addSortCriterion(sortCriterion) : sortCriterionId
		addSortCriterion(sortCriterion, order) : sortCriterionId
		removeSortCriterion(sortCriterion)
		removeSortCriterionById(sortCriterionId)
		pushUpCriterion(sortCriterion) : boolean
		pullDownCriterion(sortCriterion) : boolean
		getOrder(sortCriterion) : int[0:sortCriteria().length)
		getSortCriterionId(sortCriterion) : sortCriterionId
		sortCriterionMap() : Diccionario(sortCriterionId -> sortCriterion)
		sortCriteria() : Array<sortCriterion>
		sortCriterionMap(sortCriterionId) : sortCriterion
		columnMap() : Diccionario(columnId -> column)
		columnMap(columnId) : column
		
	Notas:
		
		Codigo correspondiente al archivo jsp/toolbar/sorting.jsp

		Diccionario(X -> Y) denota un objeto javascript
		con Xs como propiedades y Ys como valores.
*/

Model = new function() {
	//private
	var columnMap = {};
	var sortCriterionMap = {};
	var criteria = []; // Array<sortCriterionId>
	
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
	
	this.addSortCriterion = function(sortCriterion, order) {
		if(order==null) order = criteria.length;
		var id = newSortCriterionId();
		sortCriterionMap[id] = sortCriterion;
		criteria[order]= id;
		return id;
	}
	this.removeSortCriterion = function(sortCriterion) {
		this.removeSortCriterionById(this.getSortCriterionById(sortCriterion));
	}
	this.removeSortCriterionById = function(sortCriterionId) {
		//reacomodo los ordenes
		var pos = this.getOrder(sortCriterionMap[sortCriterionId]);
		for(var i=pos+1; i<criteria.length; i++, pos++) {
			criteria[pos]=criteria[i];
		}
		criteria.pop();
		//elimino del mapa
		delete sortCriterionMap[sortCriterionId];
	}
	this.getOrder = function(sortCriterion) {
		var sortCriterionId = this.getSortCriterionId(sortCriterion);
		for(var i=0; i<criteria.length; i++) {
			if(criteria[i]==sortCriterionId) return i;
		}
	}
	this.pushUpCriterion = function(sortCriterion) {
		var pos = this.getOrder(sortCriterion);
		if(pos==0) return false;
		var sortCriterionId = this.getSortCriterionId(sortCriterion);
		var newPos = pos-1;
		var tmp = criteria[newPos];
		criteria[newPos] = sortCriterionId;
		criteria[pos] = tmp;
		return true;
	}
	this.pullDownCriterion = function(sortCriterion) {
		var pos = this.getOrder(sortCriterion);
		var newPos = pos+1;
		if(newPos==criteria.length) return false;
		var sortCriterionId = this.getSortCriterionId(sortCriterion);
		var tmp = criteria[newPos];
		criteria[newPos] = sortCriterionId;
		criteria[pos] = tmp;
		return true;
	}
	this.getSortCriterionId = function(sortCriterion) {
		for(var id in sortCriterionMap) {
			if(sortCriterionMap[id]==sortCriterion) return id;
		}
		return null;
	}
	this.sortCriterionMap = function(optId) {
		if(optId) return sortCriterionMap[optId];
		return sortCriterionMap;
	}
	this.sortCriteria = function() {
		ret = [];
		for(var i=0; i<criteria.length; i++) {
			ret.push(sortCriterionMap[criteria[i]]);
		}
		return ret;
	}
	this.columnMap = function(optId) {
		if(optId) return columnMap[optId];
		else return columnMap;
	}

	//private
	var	lastSortCriterionId =0;
	function newSortCriterionId() {
		return ++lastSortCriterionId;
	}
}