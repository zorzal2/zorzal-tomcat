/*
	Centraliza las validaciones requeridas por un formulario
*/

function Validator( formName){
	
	//error handler
	this.errorHandler = new DisplayError();
	
	//formulario	
	this.formobj=document.forms[formName];
	
	if(!this.formobj){
		alert("No existe el form "+formName);
		return;
	}
	if(this.formobj.onsubmit){
		this.formobj.old_onsubmit = this.formobj.onsubmit;
		this.formobj.onsubmit=null;
	}else{
		this.formobj.old_onsubmit = null;
	}

	this.validate = function(){
		this.cleanErrors();
		for(var itr=0;itr < this.formobj.elements.length;itr++){
			var validationSet = this.formobj.elements[itr].validationset ;
			if(validationSet && !validationSet.validate( this.errorHandler))
				return false;
		}
		return true;
	}	

	this.cleanErrors = function(){
		this.errorHandler.reset(); //limpia la lista de errores
		for(var itr=0;itr < this.formobj.elements.length;itr++){
			var validationSet = this.formobj.elements[itr].validationset ;
			if(validationSet)
				validationSet.cleanErrors( this.errorHandler );
		}
	}	



	this.formobj.onsubmit= this.validate;

	
	
	this.setErrorHandler = function(errorHandler){
		this.errorHandler = errorHandler();
	}

	this.addValidation = function(itemname, validator , message){
		var itemobj = this.formobj[itemname];
		if(!itemobj){
	  		alert("BUG: no existe un input con id = "+itemname);
			return;
		}
		if(!itemobj.validationset){
			itemobj.validationset = new ValidationSet(itemobj);
		}
		
  		itemobj.validationset.addValidation(message,validator);
	}

	this.addNaturalValidation = function( input , message){
		var validator = new IntegerValidator();
		//validator.setGreaterThan(0);
		this.addValidation(input, validator, message);
	}
	
	this.toString = function(){
		return "Form validator for " + this.formobj;
	}
}


//Representa una validacion especifica par aun input dado
function Validation( errorMessage, validator , input){

	this.errorMessage = errorMessage;
	this.validator = validator;
	this.input = input;
	
	this.validate = function( ){
		return this.validator.validate( input.value )
	}
	
	this.getMessage = function(){
		return this.errorMessage;
	}
	
	this.getInputId = function(){
		return this.input.name;
	}
}


function ValidationSet(inputitem){

    this.vSet=new Array();
	this.itemobj = inputitem;

	this.addValidation = function(message, validator){
  		this.vSet[this.vSet.length]= new Validation( message, validator , this.itemobj);
	}

	this.validate = function( errorHandler ){
		var valid = true;
		for(var itr=0;itr<this.vSet.length;itr++){
			var validation = this.vSet[itr];
			if(!validation.validate()){	
				errorHandler.handleError( validation );
		   		valid = false;
		   	}
	 	}
		return valid;
	}
	
	this.cleanErrors = function( errorHandler ){
		for(var itr=0;itr<this.vSet.length;itr++){
			var validation = this.vSet[itr];
			errorHandler.cleanError( validation );
	 	}
	}
	
}


/* 
	Input validator
	Debe implementar el m?todo validate( value )
*/

function IntegerValidator(){
	this.greaterThan = null;
	this.lessThan = null;
	
	this.validate = function(value){
		return false;	
	}
}


/*
	Error handler
	Debe implementar handleError(form, input, errorMessage)
*/
function DisplayError(){
	
	this.errorList = null;
	
	this.handleError = function( validation){
		var message = validation.getMessage();
		this.errorList.push( message);
		this.getErrorContainer(validation).innerHTML =  this.buildErrorInnerHTML(message);
	}
	
	this.reset = function(){
		this.errorList = new Array();
	}
	
	this.getErrorContainer = function( validation){
		return window.document.getElementById( validation.getInputId()+"Error" );
	}
	
	this.buildErrorInnerHTML = function( message ){
		return "<font color='red'>" + message + "</font>";
	}
	
	this.cleanError = function( validation){
		this.getErrorContainer(validation).innerHTML = "";	
	}
	
	this.resumeErrors = function(){
	
	
	}
}