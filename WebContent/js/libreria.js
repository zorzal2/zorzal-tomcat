//Package Pragma
if(typeof Pragma == 'undefined')Pragma={};

/********************************************
	Menejo de selectores sobre Inventarios
*********************************************/
var currentSelection = null;

function setCurrentSelection( name ){
	currentSelection  =  name;
}

function getCurrentSelection(){
	return currentSelection;
}

function onSelection(eventName) {	
	var id = $(eventName).value;
	if(id!=''){
		var caption = $(eventName).displayValue;
		eval("window.document.forms[0].id"+getCurrentSelection()+".value = id");
		eval("window.document.forms[0].txt"+getCurrentSelection()+".value = caption");
		$(eventName).value = '';
	}
}

function createDefaultHandle(eventName) {
	return function() {
		return onSelection(eventName);
	};
}

function onFilterSelection(){
	var id = $("selectionEvent").value;
	var caption = $("selectionEvent").displayValue;
	eval("window.document.getElementsByName('filter(id"+getCurrentSelection()+")')[0].value = id");
		eval("window.document.getElementsByName('filter(txt"+getCurrentSelection()+")')[0].value = caption");
}


function onCIIUFilterSelection(){
	var code = $("selectionEvent").code;
	eval("window.document.getElementsByName('filter(id"+getCurrentSelection()+")')[0].value = code");
	eval("window.document.getElementsByName('filter(txt"+getCurrentSelection()+")')[0].value = code");
}

function handleCIIUSelection(newSelectionEvent) {
	try{
		eval("window.opener.document.forms[0].selectionEvent.displayValue = newSelectionEvent.displayValue");	
		eval("window.opener.document.forms[0].selectionEvent.value = newSelectionEvent.id");
		eval("window.opener.document.forms[0].selectionEvent.code = newSelectionEvent.code");
		window.close();
	}catch( error){
		alert("El contexto de selecci\u00F3n no es v\u00E1lido");
		window.close();
	}
}

function handleSelection(newSelectionEvent) {
	var eventName = newSelectionEvent.eventName || "selectionEvent";
	//por compatibilidad
	var eventElement = window.opener.document.forms[0][eventName];
	if(eventElement==null) eventElement = window.opener.document.forms[0]["selectionEvent"];
	try{
		eventElement.displayValue = newSelectionEvent.displayValue;	
		eventElement.value = newSelectionEvent.id;
		window.close();
	}catch( error){
		alert("El contexto de selecci\u00F3n no es v\u00E1lido");
		window.close();
	}
}

function handleSelectionEntidad(newSelectionEvent) {
	try{
		eval("window.opener.document.forms[0].selectionEvent.displayValue = newSelectionEvent.displayValue");	
		eval("window.opener.document.forms[0].selectionEvent.value = newSelectionEvent.id");
		eval("window.opener.document.forms[0].selectionEvent.cuit = newSelectionEvent.cuit");
		eval("window.opener.document.forms[0].selectionEvent.tabla = newSelectionEvent.tabla");
		window.close();
	}catch( error){
		alert("El contexto de selecci\u00F3n no es v\u00E1lido");
		window.close();
	}
}



function handleSelectionGrupo(newSelectionEvent) {
	var eventName = newSelectionEvent.eventName || "selectionEvent";
	try{
		window.opener.document.forms[0][eventName].displayValue = newSelectionEvent.displayValue;
		window.opener.document.forms[0][eventName].instrumento = newSelectionEvent.instrumento;
		window.opener.document.forms[0][eventName].value = newSelectionEvent.id;
		window.close();
	}catch( error){
		alert("El contexto de selecci\u00F3n no es v\u00E1lido");
		window.close();
	}
}


function popUpGrupos(table) {
	var selectedGroups = window.document.forms[0].groups;
	var groupQueryString = "";
	if(selectedGroups){
		var i = 0;
		for(i=0;i<selectedGroups.length-1;i++){
			groupQueryString = groupQueryString + selectedGroups[i].value + ",";
		}	
		if(selectedGroups.length>0)
			groupQueryString = groupQueryString + selectedGroups[selectedGroups.length -1 ].value;
	}	
	var url= "GruposSelectorInventario.do?idTable="+table + "&selectedGroups=" + groupQueryString;

	var currentHandler = function() {
		var selectionEvent =  $("selectionEvent");
		var Table =  $("tablaGrupos");
	  	var rows = Table.getElementsByTagName("tr");
		if ((rows == 0) || (rows == null)) {	  
	  		alert("zero");
	  	}else{
	  		rows = rows.length - 1;
	  	}
	  	var TR = Table.insertRow(rows + 1);//agrego 1 ya q tengo el titulo
	  	var cellValue;
	  	var cellName;
	  	var oldIdName;
		//Hidden
		var Input1 = document.createElement("input");
		Input1.type = "hidden";
	  	Input1.name = 'groups';
	  	Input1.value = selectionEvent.value;
	  
		var TD1 = document.createElement("td");
		TD1.appendChild( document.createTextNode(selectionEvent.displayValue) );
		TD1.appendChild(Input1);
		TR.appendChild(TD1);
		
		var TD2 = document.createElement("td");
		TD2.appendChild( document.createTextNode(selectionEvent.instrumento) );
 		TR.appendChild(TD2);
 		
 
	  	var TD3 = document.createElement("td");
		var Button = window.getDeleteButton('tablaGrupos');
		Button.name = "Delete";
		var Image = document.createElement("img");
		Image.src = "images/eliminar.gif";
		Button.appendChild(Image);
		TD3.appendChild(Button);	
		TR.appendChild(TD3);
	};
	
	AbrirSelectorPopUp(url,'Grupo',currentHandler);
}		



function AbrirSelectorPopUp(url, selectionName , handler, eventName){

	setCurrentSelection(selectionName);
	
	eventName = eventName || "selectionEvent";
	
	//Escucha el evento de seleccion
    var observer = $(eventName).observer;

   	if(observer == null){
	  	if(handler == null )
	    	$(eventName).observer = new Form.Element.Observer($(eventName), 1, createDefaultHandle(eventName));
	    else
		    $(eventName).observer = new Form.Element.Observer($(eventName), 1, handler);
	}
	
    //Abre la ventana
   	window.open(url,"","resizable=1,scrollbars=1,status=0,help=no,center=yes");

}

function AbrirFilterSelectorPopUp(url, selectionName , handler){

	setCurrentSelection(selectionName);
	
	//Escucha el evento de seleccion
    var observer = $("selectionEvent").observer;
   
   	if(observer == null){
	  	if(handler == null )
	    	$("selectionEvent").observer = new Form.Element.Observer($("selectionEvent"), 1, onFilterSelection);
	    else
		    $("selectionEvent").observer = new Form.Element.Observer($("selectionEvent"), 1, handler);
	}
	
    //Abre la ventana
   	window.open(url,"","resizable=1,scrollbars=1,status=0,help=no,center=yes");

}


<!--

//************************************************//
// Funcion que pasa desde un popUp a la ventana   //
// que lo abri? los valores de una entidad		  //
// elegida en un selector.		  				  //
// El popUp se cierra.							  //
//												  //
// entidad = nombre entidad seleccionada.		  //
// id = id de la entidad seleccionada 			  //
// caption = texto que se mostrar? en el textbox  //
//           asociado en la ventana que abri? el  //
//           popUp                                //
//************************************************//
function cargarDatos(entidad, id, caption) {
	try{
		eval("window.opener.document.forms[0].id"+entidad+".value = id");
    	eval("window.opener.document.forms[0].txt"+entidad+".value = caption");
		window.close();
	}catch( error){
		alert("El contexto de selecci\u00F3n no es v\u00E1lido");
		window.close();
	}
}

function cargarDatosEntidad(tableName, id, cuit, caption) {
	createRowSelector(tableName,id,cuit,caption);
	window.close();

}
	
//************************************************//
// Funcion que muestra los datos cargados sobre   //
// un popup en la pantalla llamadora     		  //
//												  //
// entidad = nombre del campo que quiero          //
// actualizar en la ventana llamadora   		  //
// caption = valor que se muestra en llamadora    //
//************************************************//
function cargarDatosPopUp(entidad, caption) {
    eval("window.opener.document.forms[0]."+entidad+".value = caption");
}

//************************************************//
// Funcion que redirecciona la pagina 			  //
// 												  //
// objectName = ubicacion para redirigir		  //
//************************************************//
function redirectPage(objectName){

//alert(objectName);
	if (document.all(objectName).value=='' || document.all(objectName).value==null){
		alert('Debe seleccionar una tarea');
	}
	else{
		document.location=document.all(objectName).value; 
	}	
}
//************************************************//
// Funcion que cambia el action de un formulario  //
// y hace submit del mismo						  //				
// 												  //
// newAction = nueva accion a ejecutar			  //
//************************************************//
function changeFormAction(newAction) {
	var myLocation = window.location.href;							// URL

	var index2 = myLocation.lastIndexOf('/');						// posici?n ultima '/'
	var index = (myLocation.substring(0, index2)).lastIndexOf('/'); // ante?ltima

	var appName = myLocation.substring(index+1, index2); 			// nombre de la aplicaci?n (desde la URL)
	
	document.forms[0].action = "/"+appName+"/"+newAction+".do";
	document.forms[0].submit();
}

//**********************************************//
// Funcion que setea la visibilidad de un       //
// elemento en la p?gina.						//
//												//
// objId : id del elemento a mostrar/ocultar    //
// estado: true/false (mostrar/ocultar)		    //
//**********************************************//
function toggleVisibility(objId, estado) {
	var obj = document.getElementById(objId);
	if (estado)
		obj.style.visibility = 'visible';
	else {
		obj.style.visibility = 'hidden';
	}
}

//**********************************************//
// Funcion que valida un formulario y en caso   //
// resultar satisfactoria hace submit.			//
//												//
// nombreForm: nombre del formulario a validar. //
//												//
//**********************************************//
function guardar(nombreForm) {
	var code = "validate"+nombreForm+"(document."+nombreForm+");";
	var validate = eval(code);

	if (validate) {
		var submitCode = "document."+nombreForm+".submit();"
		eval(submitCode);
	}	
}

//**********************************************//
// Funcion hace un submit al fomulario          //
//												//
//**********************************************//
function submitForm() {
	window.document.forms[0].submit();
}

//**********************************************//
// Funcion hace un submit al fomulario de un    //
// PopUp y carga los valores que necesito en    //
// la ventana llamadora                         //
//**********************************************//
function submitPopUpForm(entidad,caption) {
	cargarDatosPopUp(entidad,caption);
	window.document.forms[0].submit();
}

//**********************************************//
// Funcion que setea el parametro 'paramName'   //
// del queryString con el valor 'value'         //
// 											    //
// implementado para modificar la url      		//
// que se le pasa como par?metro			    //
//**********************************************//
 

function setParamValue(paramName,value,url) {
	var myLocation = url;
	
	// no tiene parametros
	if(myLocation.indexOf("?") < 0)
	{
		myLocation += "?" + paramName + "=" + value;
	}
	else
	{
		// tiene par?metros, no tiene paramName
		if(myLocation.indexOf(paramName) < 0)
		{
			myLocation += "&" + paramName + "=" + value;
		}
		// tiene par?metros y tiene paramName
		else
		{
			var paramNameIndex = myLocation.indexOf(paramName);
			var nextParamIndex = myLocation.indexOf("&",paramNameIndex);

			// tiene adem?s otros par?metros
			if ( nextParamIndex > paramNameIndex-1) {
				stringToReplace = myLocation.substring(paramNameIndex, nextParamIndex);
				myLocation = myLocation.replace(stringToReplace, paramName + "=" + value);
			}
			// paramName es el ?nico par?metro
			else {
				stringToReplace = myLocation.substring(paramNameIndex, myLocation.length);
				myLocation = myLocation.replace(stringToReplace, paramName + "=" + value );
			}
		}
	}

	window.opener.location = myLocation;
	return myLocation;
}

//************************************************//
// Abre un PopUp que hasta no cerrarse no permite //
// hacer foco en ninguna otra ventana.			  //
//************************************************//
function AbrirPopupBloqueante(url,ancho, alto) {
    var sFeatures="dialogHeight: " + alto + "px; dialogWidth: " + ancho + "px;edge: Raised; center: Yes; resizable: Yes;"
    window.showModalDialog(url, self, sFeatures);
}
/**
 * Submit form para popUp bloqueante
 */
function requerimiento(formulario) {
	var url = formulario.action;
//	alert("url " + url);
	var param = Form.serialize(formulario);
//	alert("param " + param);
	var funcionRet = function() {
						window.close();
					  };
//	alert("funcionRet " + funcionRet);						  
	var myAjax = new Ajax.Request(
		url, 
		{
			method: 'post', 
			parameters: param,
			onComplete: funcionRet
		});
}

//////////////////////////////////////////////////////////////////////////////////////////////////
function AbrirPopup(url, ancho, alto, scroll, name) {

	name = name || "PopUpWindow";

	if (scroll != null && scroll==true) {
		var hasScroll = "yes";
	}
	else {
		var hasScroll = "no";
	}
	var w = 480, h = 340;

	if (document.all) {
	   /* the following is only available after onLoad */
	   w = document.body.clientWidth;
	   h = document.body.clientHeight;
	}
	else if (document.layers) {
	   w = window.innerWidth;
	   h = window.innerHeight;
	}

	var leftPos = (w-ancho)/2, topPos = (h-alto)/2;

    hWnd = window.open(url, name, "left="+ leftPos +",top="+ topPos +",width="+ancho+",height="+alto+",resizable=yes,scrollbars="+ hasScroll +",status=no,help=no,center=yes");
    if ((document.window != null) && (!hWnd.opener))
        hWnd.opener = window;
    hWnd.resizeTo(ancho, alto);
    hWnd.focus();
	return hWnd;
}

//************************************************//
// Cierra un PopUp 								  //
//************************************************//
function cerrarPopUp() {
	window.close();
	//window.opener.document.location.reload();
}

//************************************************//
// Chequea una variable como condici?n de Cierre  //
// de un PopUp 								  	  //
//************************************************//
function checkVariableClose(variable) {

	if (document.all(variable).value != '') {
		cerrarPopUp();
	}
}

function AbrirPopupLista(url, ancho, alto) {
    hWnd = window.open(UrlUnica(url), "Lista", "left=100,top=150,width="+ancho+",height="+alto+",resizable=yes,scrollbars=yes,status=no,help=no,center=yes");
    if ((document.window != null) && (!hWnd.opener))
        hWnd.opener = window;
    hWnd.resizeTo(ancho, alto);
    hWnd.focus();
}

function AbrirPopupFormulario(url, ancho, alto) {

	hWnd = window.open(url, "Reporte", "left=100,top=150,width="+ancho+",height="+alto+",resizable=yes,scrollbars=yes,status=no,help=no,center=yes");
    if ((document.window != null) && (!hWnd.opener))
        hWnd.opener = window;
    hWnd.resizeTo(ancho, alto);
    hWnd.focus();
}

function AbrirPagina(url_firma) {
    wFirma = window.open(url_firma, "Firma", "left=250,top=200,width=250,height=170,resizable=no,scrollbars=no,status=no,help=no,center=yes");
    if ((document.window != null) && (!wFirma.opener))
        wFirma.opener = window;
}
function AbrirMensaje() {
	var hWnd = window.open("Mensaje.asp","Mensaje","left=250,top=200,width=300,height=210,resizable=no,scrollbars=no,center=yes");
	if ((document.window != null) && (!hWnd.opener))
		hWnd.opener = document.window;
	hWnd.focus();
}

function redirect(url) {
	parent.frames[1].document.location.href=''+url+'';
}
	
function UrlUnica(url) {
    var c = ":";
    var hora;
    var fecha = new Date();
    hora = fecha.getHours() + c;
    hora += fecha.getMinutes() + c;
    hora += fecha.getSeconds() + c;
    hora += fecha.getMilliseconds();
    for ( i=url.length;i>=0;i--) {
    	if (url.charAt(i)=='?') {
        		return url+'&HORA='+hora;
        	}
        }
    return url+'?HORA='+hora;
}

function HTMLStr (cadena) {
//faltan los de carcteres con ascentos
        str= cadena;
		str = ReplaceC(str, '&lt;', '<');
		str = ReplaceC(str, '&quot;', '"');
		str = ReplaceC(str, '&gt;', '>');
		str = ReplaceC(str, '&amp;', '&');
		str = ReplaceC(str, '&nbsp;', ' ');
		return str;
    }
//**********************************************//
// Funcion que elimina todos los espacios que 	//
// hay en una cadena.				            //
// La funcion 'replace' de JavaScript, solo 	//
// reemplaza la primera aparicion del 	        //
// caracter Buscado.			                //
//**********************************************//

function Replace( cpStr ){
	while ( cpStr.search(' ') >= 0 )
		cpStr = cpStr.replace(' ','');
	return cpStr;
}
//**********************************************//
// Reemplaza c por n dentro de una cadena.      //
//**********************************************//
	function ReplaceC( cpStr , c, n){
		while ( cpStr.search(c) >= 0 )
			cpStr = cpStr.replace(c,n);
		return cpStr;
}
//**********************************************//
// Funcion que realiza lo mismo que el LTrim de //
// ASP.						//
//**********************************************//
function ltrim( cpStr ){
	while ( cpStr.charAt(0) == ' ' )
		cpStr = cpStr.replace(' ','');
	return cpStr;
}
//**********************************************//
// Funcion que da vuelta una cadena, es 	//
// utilizada por el 'rtrim'			//
//**********************************************//
function invertStr( cpStr ){
var cpAux = '';
	for ( i=cpStr.length;i>=0;i--)
		cpAux=cpAux+cpStr.charAt(i);
	return cpAux;
}
//**********************************************//
// Funcion que realiza lo mismo que el RTrim de //
// ASP.						//
//**********************************************//
function rtrim( cpStr ){
	return invertStr( ltrim( invertStr( cpStr ) ) );
}
//**********************************************//
// Funcion que realiza lo mismo que el Trim de	//
// ASP.											//
//**********************************************//
function trim( cpStr ){
	return rtrim(ltrim( cpStr ) );
}
//**********************************************//
// Funcion que Verifica que exista un signo '@' //
// y que este no sea el primero.	        	//
//**********************************************//
function verifEMail(Mail){
	Mail = Replace( Mail );
	if( Mail.search("@") >= 1) return true;
	return false;
}
//**********************************************//
// Funcion que resetea un Formulario	    	//
//**********************************************//
function frmReset( frm ){frm.reset();}
//**********************************************//
// Funcion que valida si una cadena es nula		//
//**********************************************//
function IsNull( val ) {
	var isValid = false;

	if (val+"" == "null")
		isValid = true;

	return isValid;
}
//**********************************************//
// Funcion que valida si una cadena es indefinida//
//**********************************************//
function IsUndef( val ) {
	var isValid = false;

	if (val+"" == "undefined")
	isValid = true;

	return isValid;
}
//**********************************************//
// Funcion que valida si una cadena esta vac?a	//
//**********************************************//
function IsBlank( str ) {
	var isValid = false;
	
	if (str.length != 0 && trim(str) == ""){
		return(true);
	}
	if ( IsNull(str) || IsUndef(str) || (str+"" == ""))
		isValid = true;

	return isValid;
}
//**********************************************//
// Funcion que valida si una cadena es una		//
//	direccion de mail valida					//
//**********************************************//
function IsValidEmail( str ) {
	if (str+"" == "undefined" || str+"" == "null" || str+"" == "")
		return false;

	var isValid = true;

	str += "";

	namestr = str.substring(0, str.indexOf("@"));
	domainstr = str.substring(str.indexOf("@")+1, str.length);

   	if (IsBlank(str) || (namestr.length == 0) ||
			(domainstr.indexOf(".") <= 0) ||
			(domainstr.indexOf("@") != -1) ||
			!IsAlpha(str.charAt(str.length-1)))
		isValid = false;

   	return isValid;
}
//**********************************************//
// Funcion que valida si una cadena es un		//
// numero de telefono valido					//
//**********************************************//
function isPhone(elm) {
	var elmstr = elm.value + "";
    if (elmstr.length != 12) return false;
    for (var i = 0; i < elmstr.length; i++) {
        if ((i < 3 && i > -1) ||
            (i > 3 && i < 7) || 
            (i > 7 && i < 12)) {
            if (elmstr.charAt(i) < "0" || 
                elmstr.charAt(i) > "9") return false;
        }
        else if (elmstr.charAt(i) != "-") return false;
    }        
	return true;
}
//**********************************************//
// Funcion que valida si el numero ingresado es	//
// entero positivo								//
//**********************************************//
function isInt(elm) {
	var elmstr = elm + ""; 
    if (elmstr == "") return false;
    for (var i = 0; i < elmstr.length; i++) {
        if (elmstr.charAt(i) < "0" ||  elmstr.charAt(i) > "9" || elmstr.charAt(i) == "," || elmstr.charAt(i) == ".") {
			return false;
        }
    }
	return true;
}
//**********************************************//
// Funcion que valida si el numero ingresado es	//
// doble positivo								//
//**********************************************//
function isDouble(elm) {
	var elmstr = elm + ""; 
    if (elmstr == "") return false;
    for (var i = 0; i < elmstr.length; i++) {
        if (IsAlpha(elmstr.charAt(i))) {
			return false;
        }
    }
    if (!IsNum(elmstr)){
		return false;
    }
	return true;
}
//**********************************************//
// Funcion que valida si el numero ingresado es	//
//	positivo									//
//**********************************************//
function isPositive(elm) {
	var elmstr = elm + ""; 
    if (elmstr == "") return false;
    for (var i = 0; i < elmstr.length; i++) {
        if (elmstr.charAt(i)=="-") {
			return false;
        }
    }
	return true;
}
//**********************************************//
// Funcion que valida si una cadena es numerica	//
//**********************************************//
function IsNum( numstr ) {
	if (numstr+"" == "undefined" || numstr+"" == "null" || numstr+"" == "")
	return false;

	var isValid = true;
	var decCount = 0;

	numstr += "";

	for (i = 0; i < numstr.length; i++) {
		if (numstr.charAt(i) == ".")
			decCount++;

    	if (!((numstr.charAt(i) >= "0") && (numstr.charAt(i) <= "9") ||
				(numstr.charAt(i) == "-") || (numstr.charAt(i) == "."))) {
       				isValid = false;
       				break;
		} else if ((numstr.charAt(i) == "-" && i != 0) ||
				(numstr.charAt(i) == "." && numstr.length == 1) ||
			  (numstr.charAt(i) == "." && decCount > 1)) {
       				isValid = false;
       				break;
      }
	}
	return isValid;
}
//**********************************************//
// Funcion que resetea una cadena es alfabetica	//
//**********************************************//
function IsAlpha( str ) {
	if (str+"" == "undefined" || str+"" == "null" || str+"" == "")
		return false;

	var isValid = true;

	str += "";

	for (i = 0; i < str.length; i++) {
		if ( !( ((str.charAt(i) >= "a") && (str.charAt(i) <= "z")) ||
     			((str.charAt(i) >= "A") && (str.charAt(i) <= "Z")) ||
      			((str.charAt(i) == " ")) ) ) {
         				isValid = false;
         				break;
    	}
	}
	return isValid;
}
//**********************************************//
// Funcion que devuelve el tipo y la version 	//
// del navegador (n3 - n4 - ie4, etc)			//
//	Anonimo										//
//**********************************************//
function Navegador() {
	var browserName = navigator.appName;
	var browserVer = parseInt (navigator.appVersion);
	var agt = navigator.appVersion.toLowerCase();
	var agent;
	
	if (browserName == "Netscape" && browserVer >= 3) {
		agent="n3";
	} else { 
		agent="n2"; 
	}	
	
	if (browserName == "Microsoft Internet Explorer" && browserVer >= 4) {		
		if (agt.indexOf("msie 4.")!=-1){
			agent="ie4"
		}
		if (agt.indexOf("msie 5.")!=-1){
			agent="ie5"
		}	
		if (agt.indexOf("msie 6.")!=-1){
			agent="ie6"
		}		
	}
	return agent;
}
//**********************************************//
// Funcion que devuelve todas las propiedades 	//
// del objecto que le pasemos					//
//**********************************************//
function propiedades(objet) {
	msg='';
	for (property in objet)
		msg+=property+':'+objet[property]+' - ';
		alert(msg);
}
//************************************************//
// La siguiente funcion valida la fecha ingresada //
// Nota: para cambiar el formato de "dd/mm/aaaa"  //
// a "mm/dd/aaaa" cambiar la variabledel		  //
// "strDatestyle" a "US" o viceversa.			  //
// Nota2: antes de validar la fecha hacer que se  //
// validen desde el formulario IsBlank y IsNum	  //	
//************************************************//
function checkdate(objName) {
	//var strDatestyle = "US"; //Estilo Americano mm/dd/aaaa
	var strDatestyle = "EU";  //Estilo europeo dd/mm/aaaa
	var strDate;
	var strDateArray;
	var strDay;
	var strMonth;
	var strYear;
	var intday;
	var intMonth;
	var intYear;
	var booFound = false;
	var datefield = objName;
	//var strSeparatorArray = new Array("-"," ","/",".");
	var strSeparatorArray = new Array("/");
	var intElementNr;
	var err = 0;
	var strMonthArray = new Array(12);
	strMonthArray[0] = "01";
	strMonthArray[1] = "02";
	strMonthArray[2] = "03";
	strMonthArray[3] = "04";
	strMonthArray[4] = "05";
	strMonthArray[5] = "06";
	strMonthArray[6] = "07";
	strMonthArray[7] = "08";
	strMonthArray[8] = "09";
	strMonthArray[9] = "10";
	strMonthArray[10] = "11";
	strMonthArray[11] = "12";
	strDate = datefield.value;
	
	if (strDate.length < 1) {
		return true;
	}
	
	for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
		if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {
			strDateArray = strDate.split(strSeparatorArray[intElementNr]);
			if (strDateArray.length != 3) {
				err = 1;
				return false;
			}else {
				strDay = strDateArray[0];
				strMonth = strDateArray[1];
				strYear = strDateArray[2];
			}
		booFound = true;
	   }
	}
	if (booFound == false) {
		if (strDate.length>5) {
			strDay = strDate.substr(0, 2);
			strMonth = strDate.substr(2, 2);
			strYear = strDate.substr(4);
	   }
	}
	
	if (isNaN(strYear)) {
		return false;
	}
	
	if (strYear.length != 4) {
		return false;
	}
	
	// Fecha estilo Norteamericano mm/dd/aaaa
	if (strDatestyle == "US") {
		strTemp = strDay;
		strDay = strMonth;
		strMonth = strTemp;
	}
	

	intday = parseInt(strDay, 10);

	if (isNaN(intday)) {
		err = 2;
		return false;
	}
	
	intMonth = parseInt(strMonth, 10);
	if (isNaN(intMonth)) {
		for (i = 0;i<12;i++) {
			if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) {
				intMonth = i+1;
				strMonth = strMonthArray[i];
				i = 12;
			}
		}
		if (isNaN(intMonth)) {
			err = 3;
			return false;
	   }
	}
	
	intYear = parseInt(strYear, 10);
	
	if (isNaN(intYear)) {
		err = 4;
		return false;
	}
	
	if (intMonth>12 || intMonth<1) {
		err = 5;
		return false;
	}
	
	if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) {
		err = 6;
		return false;
	}
	
	if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
		err = 7;
		return false;
	}
	
	if (intMonth == 2) {
		if (intday < 1) {
			err = 8;
			return false;
		}
		if (LeapYear(intYear) == true) {
			if (intday > 29) {
				err = 9;
				return false;
			}
		}else {
			if (intday > 28) {
				err = 10;
				return false;
			}
		}
	}
		return true;
	}
	
	function LeapYear(intYear) {
		if (intYear % 100 == 0) {
			if (intYear % 400 == 0) { return true; }
		}else {
		if ((intYear % 4) == 0) { return true; }
	}
	return false;
}

/* --------------------------------------------------
 La siguiente funcion busca en la cadena del foco
 la concurrencia al nombre del campo para ponerle
 el foco
-------------------------------------------------- */
function ponerEnFoco(foco,form ){
	var documento
	var form
		
	if(!IsBlank(foco)){
		documento = 'document.'+form+'.'+foco+'.focus();'
		eval(documento);
		return(i);
	}
}
/* --------------------------------------------------
-------------------------------------------------- */
function IsMenorACien(string){
	var aNum, iEnt,iDec,i;
	aNum = string.split(",");
	iEnt = aNum[0];
	iDec = aNum[1];
	if(iEnt>100){
		return false
	}else{
		if((iEnt=100)&&(iDec>0)){
			return false
		}
	}
	return true
}
/* --------------------------------------------------
-------------------------------------------------- */
function IsMayorACero(strNum){
	var iNum = parseInt(strNum);
	if (iNum > 0){
		return false
	}
	return true
}
//***********************************************//
//Funciones 
//**********************************************//
function ValidarFecha(objName){
	if (!IsBlank(objName.value)&&(checkdate(objName))){
		return true;
	}
	return false;		
}
//**********************************************//
function ValidarFechaFiltros(objName){
	if (checkdate(objName)){
		return true;
	}
	return false;		
}
//**********************************************//
function ValidarPorcentaje(objName){
	var iPorc = parseInt(objName.value);
	if (isDouble(objName.value)&&(iPorc>=0)&&(iPorc<=100)){
		return(true);
	}
	return(false);
}
//**********************************************//
function ValidaSumPorc(iPorc1,iPorc2){
	var iSuma = 0;	
	iSuma = parseInt(iPorc1) + parseInt(iPorc2);
	if (iSuma>100){		
		return(false);
	}
	return(true);
}
//**********************************************//
function ValidarDensidad(objName){
	if (isDouble(objName.value)&&(parseInt(objName.value) >= 0)){	
		return true;
	}
	return false;
}
//**********************************************//
function ValidarVolumen(objName){
	if (isInt(objName.value)&&(parseInt(objName.value) > 0)){	
		return true;
	}
	return false;
}
//**********************************************//
function ValidarVolumenCero(objName){
	if (isInt(objName.value)){	
		return true;
	}
	return false;
}
//**********************************************//
function ValidarGAP(objName){
	if (isInt(objName.value)&&(parseInt(objName.value) >= 0)){	
		return true;
	}
	return false;
}
//**********************************************//
function ValidarSales(objName){
	if (isDouble(objName.value)&&(parseInt(objName.value) >= 0)){	
		return true;
	}
	return false;
}

//**********************************************//
function ValidarNumero(objName){
	if (isDouble(objName.value)&&(parseInt(objName.value) >= 0)){	
		return true;
	}
	return false;
}
//**********************************************//
function ValidarEnterosPositivos(objName){
	if (!IsNull(objName.value)&&(!IsAlpha(objName.value))&&(objName.value>0)){
		return true;
	}
	return false;
}
//**********************************************//
//-- Converts date passed in dd/mm/yyyy format to Date object.
//-- hora: optional parameter
function newDate(passedValue, hora) {  
	var firstSlash = passedValue.indexOf("/");
	var lastSlash  = passedValue.lastIndexOf("/");
	   
	var day   = passedValue.substring( 0, firstSlash);
	var month = passedValue.substring( firstSlash+1, lastSlash); 
	var year  = passedValue.substring( lastSlash+1);
	
	// el mes va de 0 a 11, por eso le restamos uno
	month = Number(month) - 1;
	
	var fecha = new Date(year,month,day);

	if (!IsUndef(hora))
	 fecha.setHours(hora);
	   	
	return fecha;
}


function formatMoney(value) {
	lowValue = Math.floor(value);
	var cents = 100*(value-lowValue) + 0.6;
	if (cents > 99) {
		returnNumber = lowValue ++;
		returnNumber += ".00";
	}else {
		returnNumber = lowValue + ".";
		returnNumber += Math.floor(cents/10);
		returnNumber += Math.floor(cents%10);
	}
	return returnNumber;
}
//**********************************************//
function esError(expresion) {
	var resultado='';
	try { 
		resultado = eval(expresion);
		return false;
	} 
	catch(er) {
	   return true;
	} 
}	
//**********************************************//
function contCaracteres(oArea){
	var iCont = oArea.value.length;
	var maxlimit = 255;	
	
	if (iCont >= maxlimit){
		alert("No se pueden ingresar m\u00E1s de 255 caracteres.");
		oArea.value = oArea.value.substring(0, maxlimit);
	}
}
//**********************************************//
function numberStr(numero)
{
	var pos;
	var nNum = numero; 
	var nStr;            

	nStr = String(Math.abs (numero));
	
	pos = nStr.indexOf('.');
	if (pos == -1)	{
		pos = nStr.length;
	}
	while (pos > 0)	{
		pos -= 3;
		if (pos <= 0) break;
		nStr = nStr.substring(0,pos) + ',' + nStr.substring(pos, nStr.length);
	}
	
	nStr = (nNum < 0) ? '-' + nStr : nStr; 
	
	//Si no tiene decimales se agregan 2 decimales con 00
	if (nStr.indexOf('.') == -1)	{
		nStr=nStr+'.00';
	}
	return (nStr);
}

/////////////////////////////////////////////////////////////////////////////////////////
// return the value of the radio button that is checked
// return an empty string if none are checked, or
// there are no radio buttons
function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

// set the radio button with the given value as being checked
// do nothing if there are no radio buttons
// if the given value does not exist, all the radio buttons
// are reset to unchecked
function setCheckedValue(radioObj, newValue) {
	if(!radioObj)
		return;
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = (radioObj.value == newValue.toString());
		return;
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
		if(radioObj[i].value == newValue.toString()) {
			radioObj[i].checked = true;
		}
	}
}

//************************************************//
// Marca como checked/unchecked un conjunto de    //
// checkboxes de una pagina						  //
//												  //
// objName = nombre del conjunto de checkbox 	  //
//************************************************//
check=false;
function checkAll(objName) { 
	if (check) {
		check=false;
	}
	else{
		check=true;
	}
	
    var i; 
    var formOptions = document.getElementsByName(objName);	 
    var cantOptions = formOptions.length; 
    for(i=0; i < cantOptions; i++) { 
		formOptions[i].checked = check; 
    } 
 }
	 


function switchLayer(Layer_Name)
{
  var GECKO = document.getElementById? 1:0 ;
  var NS = document.layers? 1:0 ;
  var IE = document.all? 1:0 ;
  var estado = false;	

  if (GECKO) {
  		estado = document.getElementById(Layer_Name).style.display == 'block';
  		document.getElementById(Layer_Name).style.display = (estado) ? 'none' : 'block';
  }   
  else if (NS) {
	  	estado = document.layers[Layer_Name].display == 'block';
       	document.layers[Layer_Name].display = (estado) ? 'none' : 'block';
  }
  else if (IE)  {
  		estado = document.all[Layer_Name].style.display == 'block';
        document.all[Layer_Name].style.display=(estado) ? 'none' : 'block';
  }
  return estado;
}

function switchLayerBeneficiaria(Layer_NameOn,Layer_NameOff,obj)
{
  var GECKO = document.getElementById? 1:0 ;
  var NS = document.layers? 1:0 ;
  var IE = document.all? 1:0 ;
  var estado = false;	
	
  if (GECKO) {
  		if (obj.checked) {
  			document.getElementById(Layer_NameOn).style.display = 'block';
  			document.getElementById(Layer_NameOff).style.display = 'none';
  		}
  		else {
  			document.getElementById(Layer_NameOn).style.display = 'none';
  			document.getElementById(Layer_NameOff).style.display = 'block';
  		}
  }   
  else if (NS) {
  		if (obj.checked) {
	       	document.layers[Layer_NameOn].display = 'block';
    	   	document.layers[Layer_NameOff].display = 'none';
    	}
    	else {
	       	document.layers[Layer_NameOn].display = 'none';
    	   	document.layers[Layer_NameOff].display = 'block';
    	}
  }
  else if (IE)  {
		if (obj.checked) {
			alert("checked");
	        document.all[Layer_NameOn].style.display= 'block';
    	    document.all[Layer_NameOff].style.display= 'none';
    	}
    	else {
    		alert("nope");
	        document.all[Layer_NameOn].style.display= 'none';
    	    document.all[Layer_NameOff].style.display= 'block';
    	}
  }
}

	function switchLayerLocalizacion(id,number) {
	
		if (number == '2') {
			var estado = switchLayer(id);
			}
		else {
			var estado = true;
		}
		if (estado) {
			var fieldJurisdiccion = document.getElementsByName('localizacion.idJurisdiccion')[0];
			var jurisdiccion = fieldJurisdiccion.options[fieldJurisdiccion.selectedIndex].text;
			document.all.viewLocal.innerHTML = "Jurisdicci&oacute;n: "+ jurisdiccion;
		}
		else {
			document.all.viewLocal.innerHTML = "";
		}		
	}
	
	function paisArgentina() {
		var fieldPais = document.getElementsByName('localizacion.pais')[0].value;
		if(fieldPais == '') {
			document.getElementsByName('localizacion.pais')[0].value = "Argentina";
		}
	}
	
	
//************************************************//
// Funcion que hace un request pasando como		  //
// parametros con metodo POST los atributos del   //
// objeto dado.					  				  //
// Ej:											  //
//	request('/Edit.do', {id: 124, name: 'Form1'}) //
//************************************************//
function request(action, parameters, target) {
	var requestForm = document.createElement('form');
	document.body.appendChild(requestForm);
	requestForm.method='post';
	requestForm.action=action;
	if(target) requestForm.target=target;
	if(parameters!=null) {
		for(paramName in parameters) {
			var input = document.createElement("INPUT");
	        input.type = "hidden";
    	    input.name = paramName;
	        input.value = parameters[paramName];
	        requestForm.appendChild(input);
		}
	}
	requestForm.submit();
}
function requestId(action, id) {
	request(action, {id: id});
}
//************************************************//
// Instancia un template del tipo 'Hola %usuario%'//
// con los atributos del objeto dado.			  //
// Ej:											  //
//  apply('Hola, %usuario%!', {usuario: 'Pepe'})  //
// Devuelve 'Hola, Pepe!'						  //
//************************************************//
function apply(template, object) {
	for(prop in object) {
		template = template.replace(new RegExp("%"+prop+"%", 'g'), object[prop]);
	}
	return template;
}
/*************************/
/* Construccion de URLs */
/***********************/

Pragma.URL = function() {

	var _protocol = null;
	var _host = null;
	var _port = null;
	var _pathFile = null;
	var _parameters = null;
	var _fragment = null;

	//Accessors
	this.getFragment = function() {		return this;	}
	this.setFragment = function(fragment) {  _fragment = fragment; return this;	}
	this.getHost = function() {		return _host;	}
	this.setHost = function(host) {		_host = host; return this;	}
	this.getPathFile = function() {		return _pathFile;	}
	this.setPathFile = function(pathFile) {		_pathFile = pathFile; return this;	}
	this.getPort = function() {		return _port;	}
	this.setPort = function(port) {		_port = port; return this;	}
	this.getProtocol = function() {		return _protocol;	}
	this.setProtocol = function(protocol) {		_protocol = protocol; return this;	}
	this.getParameters = function() {		return _parameters || {};	}

	this.setParameter = function(name, value) {
		if(value!=null) {
			if(!_parameters) _parameters = {};
			_parameters[name] = value;
		}
		return this;
	}
	this.getParameter = function(name) {
		if(!_parameters) return null;
		return _parameters[name];
	}
	
	this.toString = function() {
	
		var parts = {
			protocol: '',
			host: '',
			port: '',
			pathFile: '',
			parameters: '',
			fragment: ''
		};
		
		if(_host) {
			if(_protocol) parts.protocol = _protocol + "://";
			if(_port) parts.port = ':'+_port;
			parts.host = _host;
		}
		if(_pathFile) {
			parts.pathFile = escape(_pathFile);
			if(_host && _pathFile[0]!='/') parts.pathFile = '/'+parts.pathFile;
		}
		if(_parameters) {
			var terms = [];
			for(var paramName in _parameters) {
				var term = escape(paramName);
				term += '=';
				term += escape(_parameters[paramName]);
				terms.push(term);
			}
			parts.parameters = '?' + terms.join('&');
		}
		if(_fragment) {
			parts.fragment = '#' + _fragment;
		}
		return apply('%protocol%%host%%port%%pathFile%%parameters%%fragment%', parts);
	}
}



function cargarTareasWF(selectTask,response) {
	//selectTask.options.length = 0;
	if (response.length==0)	{
		selectTask.options[0] = new Option('---- sin acciones ----','');
	}
	else {
		selectTask.options[0] = new Option('----- seleccione -----','');
		for (var i=0; i<response.length; i++)	{
			selectTask.options[selectTask.length] = new Option(response[i].desc,response[i].value);
		}
	}
	selectTask.selectedIndex=0;
}

function cargarTareasWFInit(selectTask) {
	selectTask.options[0] = new Option('----- cargando... -----','');
}

function obtenerTareasWF(objSelect,idWF) {
  if (objSelect.tieneTareas)
	return;
   else
	 objSelect.tieneTareas=true;
  cargarTareasWFInit(objSelect);
  new Ajax.Request('WFTareasInstanciaProceso.do?id='+idWF,	{
    method:'get', 
    onSuccess: function(transport) {
	  var response = eval(transport.responseText);
      cargarTareasWF(objSelect,response);
    },
    onFailure: function(){
		window.status='No se pudieron obtener las tareas.';
	}
  });
}
