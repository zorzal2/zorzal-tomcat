<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<html> 
<head>
	<script type="text/javascript">

    function optionCriterios(mySelect) {

		var critAuxList = document.all.criterioList.value;
        var critAux = critAuxList.split("|||");

        for(i=0; i < critAux.length; i++) {
            mySelect.options[i] = new Option(critAux[i].split("---")[0],critAux[i].split("---")[1]);
        }
    }
    	
   function existRowTipoProyecto() {
   		var existe = false;
   		
	  	var fieldName = document.getElementsByName('idTipoProyectoSelect')[0];
	  	var cellValue = fieldName.options[fieldName.selectedIndex].value;
			
	    var myForm = document.forms[0];
		for (var i = 0; i < myForm.length; ++i) {
			if (myForm[i].type == 'hidden' && myForm[i].value == cellValue) { 
				existe = true;
		    }
	    }	    
   		
   		return existe;
   } 	
    	
   function createRowTipoProyecto() {

	  	var fieldName = document.getElementsByName('idTipoProyectoSelect')[0];
  	
	  	var cellValue = fieldName.options[fieldName.selectedIndex].value;
		var cellText = fieldName.options[fieldName.selectedIndex].text;
		
		if (cellValue=="") { //si es vacio no debo agregarlo
		    return;
		}
		
		if (existRowTipoProyecto()) {
			alert('Ya existe un item con este Tipo de Proyecto');
			return;
		}
	
		var targetTable = 'tipoProyectoTable';
		var sourceForm = 'DistribucionTipoProyectoEditarDynaForm';
		
		var myTable = document.getElementById(targetTable);
		var rows = myTable.getElementsByTagName("tr").length - 1;
		//append row at the last position
	  	//var myTr = myTable.insertRow(rows + 1);//agrego 1 ya q tengo el titulo
	  	var myTr = myTable.insertRow(rows);//por que tengo el monto acumulado
	  	
		var myTdButtonDelete = createTdButton(targetTable);
				
		//Creo toas las filas	
		var myCell = createCellHiddenText('idTipoProyecto',cellValue,cellText);
		myCell.appendChild(createHidden('tipoProyecto',cellText));
		myCell.width="150";
		
		myTr.appendChild(myCell); //cellName, cellValue, cellText
		
		var myTdMonto = createCell();
		var inputMonto = createText('montoTotalAsignado','');
		inputMonto.onblur = new Function("actualizarTotal(this);");
		inputMonto.style.textAlign = 'right';
		inputMonto.maxLength= 15;
		inputMonto.value = ''; //valor default
		myTdMonto.appendChild(inputMonto);
		myTr.appendChild(myTdMonto);
		
		var myTdLimite = createCell();
		var inputLimite = createText('limiteMaximoProyecto','');
		inputLimite.style.textAlign = 'right';
		inputLimite.maxLength= 10;
		inputLimite.value = ''; //valor default
		myTdLimite.appendChild(inputLimite);
		myTr.appendChild(myTdLimite);

		var myTdPlazo = createCell();
		var inputPlazo = createText('plazoEjecucion','');
		inputPlazo.style.textAlign = 'right';
		inputPlazo.maxLength= 3;
		inputPlazo.value = ''; //valor default
		myTdPlazo.appendChild(inputPlazo);
		myTr.appendChild(myTdPlazo);

		var mySelect = createSelect('idMatrizCriterio');
		optionCriterios(mySelect);
		
		var myTd = createCell();
		myTd.appendChild(mySelect);
		myTr.appendChild(myTd);
		
		myTr.appendChild(myTdButtonDelete);
	}
	
	function createTdButton(targetTable) {
	
		//create new cellValue
		var myTd = document.createElement("td");
		//get the created delete-button from the opener
		var myButton = document.createElement("button");
		myButton.onclick = new Function("deleteRowTipoProyecto(this, '"+targetTable+"');");
		
		myButton.name = "Delete";
		var myImage = document.createElement("img");
		myImage.src = "images/eliminar.gif";
		myButton.appendChild(myImage);
		myTd.appendChild(myButton);	
		return myTd;
	}
	
	function submitValidate() {
		var myForm = document.forms[0];
		var fieldType;

		for (var i = 0; i < myForm.length; ++i) {
			fieldType = myForm[i].type;
			var error = "";
			var label = "";
			
			if (fieldType == 'text' ) {
                
                var fieldName = myForm[i].name;
                var fieldValue = myForm[i].value;

                if(fieldName == 'montoTotalAsignado') {
                    if (!isDouble(fieldValue) && trim(fieldValue) != '') {
                        error = "tipoDato"; 
                        label = "Monto Total Asignado";
                    } else
		            if (trim(fieldValue) != '' && fieldValue < '0') {
        	            error = "positivo"; 
            	        label = "Monto Total Asignado";
			        }
                } else if(fieldName == 'limiteMaximoProyecto') {
                    if (!isDouble(fieldValue) && trim(fieldValue) != '') {
                        error = "tipoDato"; 
                        label = "Límite máximo del proyecto";
                    } else
                    if (trim(fieldValue) != '' && fieldValue < '0') {
                       	error = "positivo"; 
	                    label = "Límite máximo del Proyecto";
				     }
                } else if(fieldName == 'plazoEjecucion') {
                    if (!isDouble(fieldValue)) {
                        error = "tipoDato"; 
                        label = "Plazo de Ejecución"; 
                    } else
                    if (fieldValue <= '0') {
                       error = "positivo"; 
   	                   label = "Plazo de Ejecución"; 
					}
                }
                if (error == "tipoDato") {
                    alert("'"+ label +"' debe ser un valor numérico");
                    return;
                } else if (error == "datoRequerido") {
                    alert("'" + label + "' no tiene especificado la cantidad de meses");
                    return;
                } else if (error == "positivo") {
					alert("'" + label + "' debe ser positivo");
		        	return;
		        }
                
			} else if (fieldType == 'select-one') {
				
				var fieldName = myForm[i];
				//alert("nombre select: "+ fieldName.name);
				//alert("valor select: " + fieldName.options[fieldName.selectedIndex].value);
				//alert("text select: " + fieldName.options[fieldName.selectedIndex].text);
				//dejo esto por si en criterios se agrega un item mas vacío
				
			}
		}
		if (parseFloat(document.all.montoTotalInstrumento.value) < parseFloat(document.all.divTotal.innerHTML)) {
		    alert("El Monto total acumulado debe ser menor o igual al Monto total instrumento");
            return;
		}
		requerimiento(myForm);
//		myForm.submit();
	}
	
	function actualizarTotal(campo) {
	
	    var myForm = document.forms[0];
		var fieldType;
		var total = parseFloat(0);

		for (var i = 0; i < myForm.length; ++i) {
		
		    if (myForm[i].name == campo.name) {
		        if (isDouble(myForm[i].value)) {
		            total = total + parseFloat(myForm[i].value);
		        }
		    }
	    }	    
	    document.all.divTotal.innerHTML = total;	    
	}
	
	function actualizarTotalMontos() {
	
	    var myForm = document.forms[0];
		var fieldType;
		var total = parseFloat(0);

		for (var i = 0; i < myForm.length; ++i) {
		
		    if (myForm[i].name == 'montoTotalAsignado') {
		        if (isDouble(myForm[i].value)) {
		            total = total + parseFloat(myForm[i].value);
		        }
		    }
	    }	    
	    document.all.divTotal.innerHTML = total;	    
	}	
	
	
	function deleteRowTipoProyecto(field, targetTable) {
		
		deleteRow(field, targetTable);
		actualizarTotalMontos();
	}
	
	</SCRIPT>
	
	
</head>

<body onload="checkVariableClose('windowClose');actualizarTotalMontos();">

<h3>
	<bean:message bundle="titles" key="app.title.distribucionTipoProyecto"/>
</h3>

<html:form  action="/DistribucionTipoProyectoGuardar">

<html:hidden property="windowClose" />
<html:hidden property="montoTotalInstrumento" />
<html:hidden property="criterioList" />

<table class="formulario">
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td width="90%" align="right">
						<html:select property="idTipoProyectoSelect">
							<logic:present name="tiposProyectos" scope="request">
								<html:options collection="tiposProyectos" property="value" labelProperty="label" />
							</logic:present>
						</html:select> 
					</td>
					<td width="10%">
						<button id="btnAgregarTipoProyecto" onClick="createRowTipoProyecto();">Agregar</button>
					</td>
				</tr>
			</table>

		</td>
	</tr>
	
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td class="obligatorio">
						<bean:message bundle="labels" key="app.label.montoTotalInstrumento"/>
						<bean:write name="DistribucionTipoProyectoEditarDynaForm" property="montoTotalInstrumento"/>
					</td>
				</tr>
			</table>		
		</td>
	</tr>
	
	<tr>
		<td>
			<table width="100%" id="tipoProyectoTable" class="inventario">
				<tr>
					<th><bean:message bundle="headers" key="app.header.tipoProyecto" /></th>
					<th class="opcional"><bean:message bundle="headers" key="app.header.montoTotalAsignado" /></th>
					<th class="opcional"><bean:message bundle="headers" key="app.header.limiteMaximoProyecto" /></th>
					<th class="obligatorio"><bean:message bundle="headers" key="app.header.plazoEjecucion" /></th>
					<th><bean:message bundle="headers" key="app.header.criterios" /></th>
					<th class="accionesInventario"><bean:message bundle="headers" key="app.header.acciones" /></th>					
				</tr>
				
			<logic:present name="tipoProyectoList" scope="request"> 	
				<logic:iterate name="tipoProyectoList" id="tipoProyecto" indexId="index">
			   	<tr>
			      	<td width="100">
			      		<html:hidden name="tipoProyecto" property="idTipoProyecto"/>
			      		<bean:write name="tipoProyecto" property="tipoProyecto"/>
			      		<html:hidden name="tipoProyecto" property="tipoProyecto"/>
			      	</td>
			      	<td>
				      	<html:text name="tipoProyecto" property="montoTotalAsignado" onblur="actualizarTotal(this);" maxlength="15" style="text-align:right"/>
			      	</td>
			      	<td>
				      	<html:text name="tipoProyecto" property="limiteMaximoProyecto" maxlength="10" style="text-align:right"/>
			      	</td>
			      	<td>
				      	<html:text name="tipoProyecto" property="plazoEjecucion" maxlength="3" style="text-align:right"/>
			      	</td>
			      	<td>
				      	<html:select name="tipoProyecto" property="idMatrizCriterio">
							<html:options collection="idMatrizCriterios" property="value" labelProperty="label" />
						</html:select> 
			      	</td>
			      	<td>
				      	<button onclick="deleteRowTipoProyecto(this,'tipoProyectoTable');"><img src='images/eliminar.gif' border='0' /></button>
			      	</td>			      				      				      	
				</tr>
			   </logic:iterate>
		   	</logic:present>

				<tr>
					<th width="150"><bean:message bundle="headers" key="app.header.montoAcumulado" /></th>
					<th align="left">
						<div id="divTotal" align="right">
							<c:out value="${montoTotalAcumulado}"/>
						</div>
						&nbsp;
					</th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
						   	
		   </table>
   		</td>
   </tr>
</table>

<pragmatags:btnOkCancel okJavascript="submitValidate()" cancelJavascript="cerrarPopUp()"/>

</html:form>

</body>
</html>