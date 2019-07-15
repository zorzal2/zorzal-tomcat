//Ordenamiento

function Column(id, description) {
	this.id=id
	this.description = description;
}

var SortOption = {
	ASCENDING: 'Ascending',
	DESCENDING: 'Descending',
	
	forName: function(name) {
		return this[(''+name).toUpperCase()]
	}
};

function SortCriterion(column, sortOption) {
	this.column = column;
	this.sortOption = sortOption;
}