	function createCellSelect(cellName, cellValue) {
		var myTd = document.createElement("td");
		var mySelect = document.createElement("select");
		mySelect.name = cellName;
	  	myTd.appendChild(mySelect);
	  	return myTd;		
	}
	
	function createCell() {
		var myTd = document.createElement("td");
	  	return myTd;		
	}

	function createSelect(cellName, cellValue) {
		var mySelect = document.createElement("select");
		mySelect.name = cellName;
	  	return mySelect;		
	}

	function createCellInput(cellName, cellValue) {
	//create td-element
	  var myTd = document.createElement("td");
	  var myInput = document.createElement("input");
	  myInput.type = "text";

	//set value and name
	  myInput.name = cellName;
	  myInput.value = cellValue;

	  myTd.appendChild(myInput);
	  return myTd;
	}
	
	function createCellHiddenText(cellName, cellValue, cellText) {
		//create td-element
		  var myTd = document.createElement("td");
		//create text-element with value
		  var myTdText = document.createTextNode(cellText);
		  myTd.appendChild(myTdText);
		//create input-element "hidden"
		  myTd.appendChild(createHidden(cellName,cellValue));
		  return myTd;
	}

	function createHidden(cellName, cellValue) {
		return createInput("hidden",cellName,cellValue);
	}
	
	function createText(cellName, cellValue) {
		return createInput("text",cellName,cellValue);
	}
	
	function createInput(tipo, cellName, cellValue) {
		var myInput = document.createElement("input");
		myInput.type = tipo;
	
		myInput.name = cellName;
		myInput.value = cellValue;
	
		return myInput;					
	}	

	//create cells para la tabla, consists of: td-element, text-element with value, input-element "hidden" with value and name
	//InputName = string of the inputvalue
	function createCells(cellName, cellValue, cellText) {
	//create td-element
	  var TD1 = opener.document.createElement("td");
	//create text-element with value
	  var TD1text = opener.document.createTextNode(cellText);
	  TD1.appendChild(TD1text);
	//create input-element "hidden"
	  var Input1 = opener.document.createElement("input");
	  Input1.type = "hidden";
	//set value and name
	  Input1.name = cellName;
	  Input1.value = cellValue;
	//alert("Name: "+cellName);
	//alert("Value: "+cellValue);	  
	//finalize the td-element
	  TD1.appendChild(Input1);
	  return TD1;
	}
	
	//creates a row with td-elements of input-fields and a button to delete row
	//TableID = string of the tablename
	//sourceForm = string of the form with the input fields
	function createRow(targetTable,sourceForm) {
	//get table to append row
	  var Table = opener.document.getElementById(targetTable);
	//get number of rows in the table
	  var rows = Table.getElementsByTagName("tr");
	  if ((rows == 0) || (rows == null)) {	  
	  }
	  else {rows = rows.length - 1;}
	//append row at the last position
	  var TR = Table.insertRow(rows + 1);//agrego 1 ya q tengo el titulo
	  var cellValue;
	  var cellName;
	  var oldIdName;
	//get form with input fields
	  var FormID = document.getElementsByName(sourceForm);
	//loop over all elements in the form
		for (var i = 0; i < FormID[0].length; ++i) {
	//find text-fields in the form
			if (FormID[0].elements[i].type == 'text') {
	//get value of the textfield
	
				cellValue = FormID[0].elements[i].value;
				cellText = FormID[0].elements[i].value;
				cellName = FormID[0].elements[i].name;				
				
				if (cellName.indexOf('txt',0) < 0) { //Field Comun
					cellName = cellName;
				}
				else {
					cellName = cellName.replace('txt','id');
					cellValue = document.getElementsByName(cellName)[0].value;
					cellName = cellName;
				}
				
				TR.appendChild(createCells(cellName,cellValue,cellText));
//				alert(cellName);
//				alert(cellValue);
			}
		}
	//create new cellValue
	  var TD4 = opener.document.createElement("td");
	//get the created delete-button from the opener
	  var Button = window.opener.getDeleteButton(targetTable);
	//set attributes
	  Button.name = "Delete";
	  var Image = opener.document.createElement("img");
	  Image.src = "images/eliminar.gif";
	//append created button
		Button.appendChild(Image);
	  TD4.appendChild(Button);	
	  TR.appendChild(TD4);
		self.close();
	}
	

	//creates a row with td-elements of input-fields and a button to delete row
	//TableID = string of the tablename
	//sourceForm = string of the form with the input fields
	function createRowSelector(tableName,id,cuit,caption) {
	//get table to append row
	  var Table = document.getElementById(tableName);
//	  alert(Table);
//	  alert(Table.length);
	//get number of rows in the table
	  var rows = Table.getElementsByTagName("tr");
//	  alert(rows);
	  if ((rows == 0) || (rows == null)) {	  
	  	alert("zero");
	  }
	  else {rows = rows.length - 1;}
	//append row at the last position
	  var TR = Table.insertRow(rows + 1);//agrego 1 ya q tengo el titulo
	  var cellValue;
	  var cellName;
	  var oldIdName;
	//create input-element "hidden"
	  var Input1 = document.createElement("input");
	  Input1.type = "hidden";
	  
 	  var TD1 = document.createElement("td");
	//create text-element with value
	  var TD1text = document.createTextNode(id);
	  TD1.appendChild(TD1text);
	//set value and name
	  Input1.name = 'idEntidades';
	  Input1.value = id;
	  TD1.appendChild(Input1);
      TR.appendChild(TD1);
 	  
 	  var TD2 = document.createElement("td");
	//create text-element with value
	  var TD2text = document.createTextNode(cuit);
	  TD2.appendChild(TD2text);
 	  var Input2 = document.createElement("input");
	  Input2.type = "hidden";
     	Input2.name = 'cuits';
    	Input2.value = cuit;
	   TD2.appendChild(Input2);
 		TR.appendChild(TD2);
 		
  	  var TD3 = document.createElement("td");
	//create text-element with value
	  var TD3text = document.createTextNode(caption);
	  TD3.appendChild(TD3text);
 	  var Input3 = document.createElement("input");
	  Input3.type = "hidden";
    	Input3.name = 'denominaciones';
    	Input3.value = caption;
	   TD3.appendChild(Input3);
 		TR.appendChild(TD3);
 
 	//create new cellValue
	  var TD4 = document.createElement("td");
	//get the created delete-button from the opener
	  var Button = window.getDeleteButton(tableName);
	//set attributes
	  Button.name = "Delete";
	  var Image = document.createElement("img");
	  Image.src = "images/eliminar.gif";
	//append created button
		Button.appendChild(Image);
	  TD4.appendChild(Button);	
	  TR.appendChild(TD4);
	}

	//creates a row with td-elements of input-fields and a button to delete row
	//TableID = string of the tablename
	//sourceForm = string of the form with the input fields
	function createRowEntidad(targetTable,sourceForm) {
	//get table to append row
	  var Table = opener.document.getElementById(targetTable);
	//get number of rows in the table
	  var rows = Table.getElementsByTagName("tr");
	  if ((rows == 0) || (rows == null)) {	  
	  }
	  else {rows = rows.length - 1;}
	//append row at the last position
	  var TR = Table.insertRow(rows + 1);//agrego 1 ya q tengo el titulo
	  var cellValue;
	  var cellName;
	  var oldIdName;
	//get form with input fields
	  var FormID = document.getElementsByName(sourceForm);
	//loop over all elements in the form
		for (var i = 0; i < FormID[0].length; ++i) {
	//find text-fields in the form
			if ((FormID[0].elements[i].type == 'text') || (FormID[0].elements[i].type == 'select-one')) {
	//get value of the textfield
				cellValue = FormID[0].elements[i].value;
				cellText = FormID[0].elements[i].value;
				cellName = FormID[0].elements[i].name;				
				
				if (cellName.indexOf('txt',0) < 0) { //Field Comun
					cellName = cellName;
				}
				else {
					cellName = cellName.replace('txt','id');
					cellValue = document.getElementsByName(cellName)[0].value;
					cellName = cellName;
				}
				if (FormID[0].elements[i].type == 'select-one') {
					cellText = FormID[0].elements[i].options[FormID[0].elements[i].selectedIndex].text;
//					alert(cellValue);
//					cellValue = FormID[0].elements[i].options[FormID[0].elements[i].selectedIndex].text;
				}
				
				TR.appendChild(createCells(cellName,cellValue,cellText));
			}
		}
	//create new cellValue
	  var TD5 = opener.document.createElement("td");
	//get the created delete-button from the opener
	  var Button = window.opener.getDeleteButton(targetTable);
	//set attributes
	  Button.name = "Delete";
	  var Image = opener.document.createElement("img");
	  Image.src = "images/eliminar.gif";
	//append created button
		Button.appendChild(Image);
	  TD5.appendChild(Button);	
	  TR.appendChild(TD5);
		self.close();
	}

	//delete row from table
	//button = buttonobject
	//targetTable = string of the tablename
	function deleteRow(button,targetTable) {
	//format table to set ids for each row
		formatTable(targetTable);
	//get table
		var table = document.all ? document.all[targetTable] : document.getElementById(targetTable);
	//get row for the clicked button
		var obj = button.parentNode;
		var r = obj.parentNode;
	//delete selected row
		table.deleteRow(r.getAttribute('id'));
	//format table to set ids new
		formatTable(targetTable);
	}
	
	//opener function to create a delete button
	//targetTable = string of the tablename
	function getDeleteButton(targetTable) {
		var button;
		button = document.createElement("button");
	//set function for the onclick event
		button.onclick = new Function("deleteRow(this, '"+targetTable+"');");
		return button;
	}
	
	//format table to set ids for each row
	//oTable = string of the tablename
	function formatTable(oTable) {
	//get all rows
		var rows = document.all(oTable).rows;
	//set attribute id
		for (var i = 0; i < rows.length; i++) {
			rows[i].setAttribute('id', i);
		}
	}
	
	// JHD Esta funcion est? incluida en libreria.js, habr?a q quitarla de aca 
	// cuando se me vaya del cache del disco
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
	
	//************************************************//
	// Abre el popUp para cargar Distribucion de      //
	// Finaciamento para Regiones y Jurisdicciones    //
	//											      //
	//************************************************//	
	function popUpDistribucionJurisdiccion() {
		var montoTotal = window.document.forms[0].montoFinanciamientoTotal.value;
		
		if (!IsBlank(montoTotal) && !IsNull(montoTotal) && isDouble(montoTotal)) { 
			var type = getCheckedValue(window.document.forms[0].tipoDistribucionFinanciamiento);
			var asignacion = window.document.forms[0].codigoTipoFinanciamientoAsignacion.value;
			var url= "DistribucionFinanciamiento.do?tipo="+ type + "&asignacion=" + asignacion + "&montoTotal=" + montoTotal;
			var scroll = true;
			if (type=='REGIONAL') {
				AbrirPopup(url,'350','400',scroll);
//				AbrirPopupBloqueante(url,'350','400');
			}
			if (type=='JURISDICCIONAL') {
				AbrirPopup(url,'400','750',scroll);
//				AbrirPopupBloqueante(url,'400','750');
			}
		} else {
			alert('Debe ingresar un importe en Monto Total Instrumento');		
		}
	}
	
	function popUpInstrumentoVersion(instrumento) {
		var idInstrumento = window.document.all('id').value;
		var identificador = window.document.all('identificador').value;
		
		if (idInstrumento == null || idInstrumento == '') {			
			alert('No existen versiones cargadas del instrumento');
			return;
		}
		
		var url= "InstrumentoVersion.do?idInstrumento=" + idInstrumento 
		url += "&instrumento=" + instrumento 
		url += "&identificador=" + identificador
		
		AbrirPopup(url,'450','450', true);
	}

	function popUpMostrarVersionInstrumento(instrumento,idInstrumento,identificador) {
		
		if (idInstrumento == null || idInstrumento == '') {			
			alert('No existen versiones cargadas del instrumento');
			return;
		}
		
		var url= "InstrumentoVersion.do?idInstrumento=" + idInstrumento 
		url += "&instrumento=" + instrumento 
		url += "&identificador=" + identificador
		
		AbrirPopup(url,'450','450', true);
	}
	
	function popUpEntidadBeneficiaria() {
		var idEntidadBeneficiaria = window.document.all('id').value;
		var url= "EntidadBeneficiariaEditar.do?id="+idEntidadBeneficiaria;
//		AbrirPopupBloqueante(url,'700','580');
 	window.open(url,"EntidadBeneficiariaEditarDynaForm","width=700,height=580,left=100,top=70,resizable=yes,status=yes,help=yes,scrollbars=1");
	}
	
	function popUpPersonaEvaluador() {
		var id = window.document.all('id');
		if (id.value == "") {
			var url= "PersonaEvaluadorEditar.do?id=0";
		} else {
			var url= "PersonaEvaluadorEditar.do?id="+id.value;
		}
		AbrirPopup(url,'500','300',true);
//		AbrirPopupBloqueante(url,'500','280');
// 	window.open(url,"Zweitfenster","width=700,height=580,left=100,top=200,resizable=yes,status=yes,help=yes,scrollbars=yes");
	}
	
	function popUpFacturacion() {
		var id = window.document.all('id');
		if (id == null) {
			var url= "FacturacionEditar.do?id=0";
		}
		else {
			var url= "FacturacionEditar.do?id="+id.value;
		}
		AbrirPopup(url,'700','280',true);
//		AbrirPopupBloqueante(url,'700','580');
//		window.open(url,"Zweitfenster","width=700,height=580,left=100,top=200,resizable=yes,status=yes,help=yes,scrollbars=yes");
	}
	
	function popUpEvaluacionEvaluador(valor) {
		var url= "EvaluacionEvaluador.do?idTable="+valor;
		AbrirPopup(url,'500','250',true);
	}		
	
	function popUpEntidadIntervinientes(valor) {
		var url= "EntidadIntervinientes.do?idTable="+valor;
		AbrirPopup(url,'500','250',true);
//		window.open(url,"Zweitfenster","width=700,height=580,left=100,top=200,resizable=yes,status=yes,help=yes,scrollbars=yes");
	}		
	
	function popUpFacturacionData(valor) {
		var url= "FacturacionData.do?idTable="+valor;
//		AbrirPopup(url,'350','250');
//		AbrirPopupBloqueante(url,'700','580');
		window.open(url,"test","width=350,height=250,left=100,top=200,resizable=yes,status=yes,help=yes,scrollbars=1,edge=Raised");
	}		
	
	function popUpPrintAdmisibilidad(valor) {
		var url= "PrintAdmisibilidadProyecto.do?id="+valor;
//		AbrirPopup(url,'350','250');
//		AbrirPopupBloqueante(url,'700','580');
		window.open(url,"test","width=550,height=450,left=100,top=200,resizable=yes,status=yes,help=yes,scrollbars=1,edge=Raised");
	}		
	
	function popUpExpedienteCuerpo(valor) {
		var url= "ExpedienteProyectoAgregar.do?idTable="+valor;
//		AbrirPopup(url,'350','250');
//		AbrirPopupBloqueante(url,'700','580');
		window.open(url,"test","width=350,height=250,left=100,top=200,resizable=yes,status=yes,help=yes,scrollbars=1,edge=Raised");
	}		
	

	function popUpEntidadBeneficiariaSelector(idEntidad) {
		var url= "EntidadBeneficiariaSelSelectorPopUp.do?idEntidad="+idEntidad;
		
//		AbrirPopup(url,'350','250');
//		AbrirPopupBloqueante(url,'700','580');
//		window.open(url,"test","width=700,height=350,left=100,top=200,resizable=yes,status=yes,help=yes,scrollbars=yes,edge=Raised");
		var selectionHandler = function() {
			var selectionEvent =  $("selectionEvent");
			createRowSelector('tablaEntidad',selectionEvent.value,selectionEvent.cuit,selectionEvent.displayValue);
		};

		AbrirSelectorPopUp(url, 'EntidadBeneficiariaSel', selectionHandler  );
	}		
	
	function popUpDistribucionTipoProyecto() {
		var monto = document.all.montoFinanciamientoTotal.value;
		if (monto == "" || !isDouble(monto)) {
			alert('Debe ingresar un importe en Monto Total Instrumento para continuar');
			return;
		}
		var url= "DistribucionTipoProyectoAgregar.do?montoTotalInstrumento="+monto;
		//AbrirPopupBloqueante(url,'750','350');
		AbrirPopup(url,'750','450',true);
	}

	function popUpEvaluarProyecto(id) {
		var url= "EvaluarResultadoProyectoEditar.do?id="+id;
		AbrirPopup(url,'900','500',true);
//		AbrirPopupBloqueante(url,'900','600');
	}
	
	function popUpEditarMontosRendicionEvaluacionSeguimientoANR(valor) {
		var url= "EditarMontosRendicionEvaluacionSeguimientoANR.do?id="+valor;
		AbrirPopup(url,'600','280',true);
	}			
	function popUpEditarMontosRendicionEvaluacionSeguimientoCF(valor) {
		var url= "EditarMontosRendicionEvaluacionSeguimientoCF.do?id="+valor;
		AbrirPopup(url,'600','280',true);
	}		
	
	function popUpEditarMontosGestionadosRendicion(valor) {
		var url= "EditarMontosGestionadosRendicion.do?id="+valor;
		AbrirPopup(url,'600','280',true);
	}		

	function popUpControlFacturas() {
		var url= "SeguimientoControlarFacturas.do";
		AbrirPopup(url,'700','200',true);
	}		
	
	function popUpEditarResultadoFontar(valor) {
		var url= "EditarResultadoFontar.do?id="+valor;
		AbrirPopup(url,'600','280',true);
	}		

	function popUpEditarResultadoUffa(valor) {
		var url= "EditarResultadoUffa.do?id="+valor;
		AbrirPopup(url,'600','280',true);
	}		

	function popUpEditarResultadoBid(valor) {
		var url= "EditarResultadoBid.do?id="+valor;
		AbrirPopup(url,'600','280',true);
	}		
	function popUpModificarPresupuestoSegunAvance(idSeguimiento) {
		var url= "ModificarPresupuestoSegunAvance.do?id="+idSeguimiento;
		AbrirPopup(url,'600','280',false);
	}
	function popUpModificarPendienteDeRendicion(idSeguimiento, valorActual) {
		var url= "ModificarPendienteDeRendicion.do?id="+idSeguimiento+"&valor="+valorActual;
		AbrirPopup(url,'600','280',false);
	}
