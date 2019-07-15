var Controller;
/**
	(* Controlador para la pantalla de ordenamiento. *)

	Requiere

		js/toolbar/utils.js
		js/toolbar/classes.js
		js/toolbar/sorting/model.js
		js/toolbar/sorting/view.js
	
	Metodos publicos:
	
		attachView(aView)
		attachModel(aModel)
		newSortCriterion()
		changeSortOption(sortCriterionId, newSortOption)
		pushUpCriterion(sortCriterionId)
		pullDown(sortCriterionId)
		removeSortCriterion(sortCriterionId)
		submit()
		
		
	Notas:
		
		Codigo correspondiente al archivo jsp/toolbar/sorting.jsp

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
	
	this.newSortCriterion = function() {
		var column = model.columnMap(view.selectedColumn());
		//chequeo que no este eligiendo una columna por la cual ya se esta ordenando
		var sortCriteria = model.sortCriterionMap();
		for(var sortCriterionId in sortCriteria) {
			var sortCriterion = sortCriteria[sortCriterionId];
			if(sortCriterion.column.id==column.id) {
				//la columna ya existe
				ShowAlert("column_already_selected", {column: column.description});
				return;
			}
		}
		var sortCriterion = new SortCriterion(
			column, //columna: seleccionada
			SortOption.ASCENDING //default
		);
		var sortCriterionId = model.addSortCriterion(sortCriterion);
		//actualizo la vista
		view.addSortCriterion(sortCriterion, sortCriterionId);
	}
	this.removeSortCriterion = function(sortCriterionId) {
		model.removeSortCriterionById(sortCriterionId);
		view.removeSortCriterion(sortCriterionId);
	}
	this.changeSortOption = function(sortCriterionId, newSortOption) {
		if(!sortCriterionId) alert('error')
		model.sortCriterionMap(sortCriterionId).sortOption = SortOption.forName(newSortOption);
	}
	this.pushUpCriterion = function(sortCriterionId) {
		if(model.pushUpCriterion(model.sortCriterionMap(sortCriterionId)))
			view.refreshList();
	}
	this.pullDownCriterion = function(sortCriterionId) {
		if(model.pullDownCriterion(model.sortCriterionMap(sortCriterionId)))
			view.refreshList();
	}
	this.submit = function() {
		request("ToolbarSortCriterionSetAll.do", {criteriaXML: this.encode()});
	}
	this.encode = function() {
		function encodeSortCriterion(sortCriterionId) {
			var template = 
				'    <sortCriterion\n'+
				'       column-id="%sortCriterionColumnId%"\n'+
				'       sort-option="%sortOption%"\n'+
				'		order="%sortOrder%"/>';
			var sortCriterion = model.sortCriterionMap(sortCriterionId);
			return apply(template, {
				sortCriterionColumnId: sortCriterion.column.id,
				sortOption: sortCriterion.sortOption,
				sortOrder: model.getOrder(sortCriterion)+1
			})
		}
		
		var template = 
			'<sortCriteria user-id="%userId%" toolbar-id="%toolbarId%">\n'+
			'%sortCriteria%\n'+
			'</sortCriteria>';
		var sortCriteria = [];
		for(var sortCriterionId in model.sortCriterionMap()) {
			sortCriteria.push(encodeSortCriterion(sortCriterionId));
		}
		return apply(template, {
			userId: model.getUserId(),
			toolbarId: model.getToolbarId(),
			sortCriteria: sortCriteria.join('\n')
		});
	}
}