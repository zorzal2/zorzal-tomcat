//Filtrado

var FilterValueDataType = {
	DATE: 'Date',
	NUMBER: 'Number',
	BOOLEAN: 'Boolean',
	STRING: 'String',
	LONG: 'Long',
	
	forName: function(name) {
		return this[(''+name).toUpperCase()]
	}
};

function FilterType(id, description, filterValueDataType) {
	this.id = id;
	this.description = description;
	this.filterValueDataType = filterValueDataType;
	FilterType[id] = this
}

function Column(id, description, supportedFilterTypes) {
	this.id=id
	this.description = description;
	this.supportedFilterTypes = supportedFilterTypes || [];
}

function Filter(column, filterType, value) {
	this.column = column;
	this.filterType = filterType;
	this.value = value;
}