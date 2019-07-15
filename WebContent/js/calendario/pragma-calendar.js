
//Mapa de calendarios
var calendarios = new Array();
var fillDates = null;

function InputCalendar( id ){
	this.id = id;
	this.calendarName =  resolveCalendarName( id);
	this.calendarContainerName =  resolveCalendarContainerName( id );
	this.calendar = null;

	this.showCalendar = function(){
		if(this.calendar == null){
			this.calendar  = getCalendarInstance(this.calendarName, this.calendarContainerName );
			this.calendar.selectEvent.subscribe(dateSelectionHandler, this.calendar, true); 
		}
		this.setCalendarPosition();
		this.calendar.render();
		this.calendar.show();
	}

	this.setCalendarPosition = function(){
		var container = window.document.getElementById( this.calendarContainerName );
		var input = window.document.getElementById( this.id +'Button' );
		//alert((input.offsetLeft + 10) +'px' );
		//alert( (input.offsetTop + 10) +'px'); 
		
		//container.style.left = (input.offsetLeft + 10) +'px';
		//container.style.top = (input.offsetTop + 10) +'px';
		
		//Put calendar according current position of container
		var eL=0;var eT=0;
		for(var p=input; p&&p.tagName!='BODY'; p=p.offsetParent){
			eL+=p.offsetLeft;
		    eT+=p.offsetTop;
		}
		var eH = input.offsetHeight;
		var dH = container.style.pixelHeight;
		var sT = document.body.scrollTop;
		container.style.left = eL;
		if(eT-dH >= sT && eT+eH+dH > document.body.clientHeight+sT)
			container.style.top=eT-dH;
		else
      		container.style.top=eT+eH;
	}
	
	this.updateInputValue = function( value ){
		window.document.getElementById( this.id ).value = value;
	}
	
	this.getPositionLeft = function(){
		var input = window.document.getElementById( this.id );
		input.offsetLeft;
	}
	
	this.getPositionTop = function(){
		var input = window.document.getElementById( this.id );
		input.offsetTop;
	}
	
}


//Retorna el nombre asignado al calendario relacionado a @inputId
function resolveCalendarName( inputId ){
	return inputId + "Calendar";
}

//Retorna el nombre asignado al container del calendario relacionado a @inputId
function resolveCalendarContainerName( inputId ){
	return inputId + "CalendarContainer";
}

/*
	Crea una nueva instancia de calendario con el id @id configurado en castellano. 
	Debe definirse un div con id = @container para hacer el render del calendario.
*/
function getCalendarInstance(id, container){
	var calendar = new YAHOO.widget.Calendar(id, container,{ title:"Seleccionar fecha", close:true });
	calendar.cfg.setProperty("MONTHS_SHORT",   ["Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"]); 
	calendar.cfg.setProperty("MONTHS_LONG",    ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"]); 
	calendar.cfg.setProperty("WEEKDAYS_1CHAR", ["D", "L", "M", "M", "J", "V", "S"]); 
	calendar.cfg.setProperty("WEEKDAYS_SHORT", ["Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"]); 
	calendar.cfg.setProperty("WEEKDAYS_MEDIUM",["Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab"]); 
	calendar.cfg.setProperty("WEEKDAYS_LONG",  ["Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"]); 
	return calendar;
}	

/*
	Handler utilizado para actualizar el input del formulario con id = @id ante el 
	evento de seleccion
*/
function dateSelectionHandler(type,args,obj) { 
	var dates = args[0];  
	var date = dates[0]; 
    var year = date[0], month = date[1], day = date[2]; 
	var txtDate = day + "/" + month + "/" + year; 
	var inputCalendar = calendarios[ this.id];
	inputCalendar.updateInputValue( txtDate );
	this.hide();
	if (fillDates != '') {
//		alert('aca');
//		alert(fillDates);
		eval(fillDates);
	}
} 

/* 
	Crea una instancia de calendario y ejecuta el render del mismo.
*/
function showCalendar(inputId, fillDate) {
	var calendarKey = resolveCalendarName( inputId );
	var inputCalendar = calendarios[calendarKey];
	fillDates = fillDate;
	if(inputCalendar == null){
		inputCalendar = new InputCalendar( inputId );
		calendarios[ calendarKey ] = inputCalendar;
	}
	inputCalendar.showCalendar();
}


