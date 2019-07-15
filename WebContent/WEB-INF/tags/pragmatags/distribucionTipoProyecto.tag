<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-logic-el" prefix="logic-el" %>

<%@ attribute name="id" required="true"%>

<c:set var="visible" value="display:none;"/> 
<html:messages id="error">
    <c:set var="visible" value="display:block;"/>
</html:messages>

	<script type="text/javascript" src="js/popUpWindow.js"></script>
	<script type="text/javascript">

	slDistribucionTipoProyecto('divDistribucionTipoProyecto','2');

    function optionCriterios(mySelect) {

		var critAuxList = document.all.criterioList.value;
        var critAux = critAuxList.split("|||");

        for(i=0; i < critAux.length; i++) {
            mySelect.options[i] = new Option(critAux[i].split("---")[0],critAux[i].split("---")[1]);
        }
    }
    	
    function createRowTipoProyecto() {
	
		var targetTable = '<c:out value='${id}'/>';
		
		var myTable = document.getElementById(targetTable);
		var rows = myTable.getElementsByTagName("tr").length - 1;
		//append row at the last position
	  	var myTr = myTable.insertRow(rows + 1);//agrego 1 ya q tengo el titulo
	  	
	  	var fieldName = document.getElementsByName('idTipoProyecto')[0];
  	
	  	var cellValue = fieldName.options[fieldName.selectedIndex].value;
		var cellText = fieldName.options[fieldName.selectedIndex].text;
		
		if (cellValue=="") { //si es vacio no debo agregarlo
		    return;
		}
		//TODO Verificar que ya no se haya agregado 
		

		var myTdButtonDelete = createTdButton(targetTable);
				
		//Creo toas las filas	
		var myCell = createCellHiddenText('idTipoProyecto',cellValue,cellText);
		myCell.appendChild(createHidden('tipoProyecto',''));
		
		myTr.appendChild(myCell); //cellName, cellValue, cellText
		myTr.appendChild(createCellInput('montoTotalAsignado',''));
		myTr.appendChild(createCellInput('limiteMaximoProyecto',''));
		myTr.appendChild(createCellInput('plazosEjecucion',''));
		
		var mySelect = createSelect('criterios');
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
		  var myButton = getDeleteButton(targetTable);
	
		  myButton.name = "Delete";
		  var myImage = document.createElement("img");
		  myImage.src = "images/eliminar.gif";
		  myButton.appendChild(myImage);
		  myTd.appendChild(myButton);	
		  return myTd;
	}
	
	function slDistribucionTipoProyecto(id,number) {
	
		if (number == '2') {
			switchLayer(id);
		}

	}
	
	</script>

	<html:hidden property="criterioList" />	


<button onclick="javascript:slDistribucionTipoProyecto('divDistribucionTipoProyecto','2');">...</button>

<div id="divDistribucionTipoProyecto">
<table class="formulario">
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td width="90%" align="right">
						<html:select property="idTipoProyecto">
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
			<table id="<c:out value='${id}'/>" border="0" width="100%" type="circle" style="<c:out value="${visible}" />">
				<tr>
					<th><bean:message bundle="headers" key="app.header.tipoProyecto" /></th>
					<th><bean:message bundle="headers" key="app.header.montoTotalAsignado" /></th>
					<th><bean:message bundle="headers" key="app.header.limiteMaximoProyecto" /></th>
					<th><bean:message bundle="headers" key="app.header.plazoEjecucion" /></th>
					<th><bean:message bundle="headers" key="app.header.criterios" /></th>
					<th><bean:message bundle="headers" key="app.header.acciones" /></th>					
				</tr>
				
			<logic:present name="tipoProyectoList" scope="request"> 	
				<logic:iterate name="tipoProyectoList" id="tipoProyecto" indexId="index">
			   	<tr>
			      	<td>
			      		<html:hidden name="tipoProyecto" property="idTipoProyecto" indexed="true"/>
			      		<bean:write name="tipoProyecto" property="tipoProyecto"/>
			      		<html:hidden name="tipoProyecto" property="tipoProyecto" indexed="true"/>
			      	</td>
			      	<td>
				      	<html:text name="tipoProyecto" property="montoTotalAsignado" indexed="true"/>
			      	</td>
			      	<td>
				      	<html:text name="tipoProyecto" property="limiteMaximoProyecto" indexed="true"/>
			      	</td>
			      	<td>
				      	<html:text name="tipoProyecto" property="plazosEjecucion" indexed="true"/>
			      	</td>
			      	<td>
				      	<html:select name="tipoProyecto" property="criterios">
							<html:options collection="criterioList" property="value" labelProperty="label" />
						</html:select> 
			      	</td>
			      	<td>
				      	<html:button property="acciones" onclick="void;"><img src='images/visualizar.gif' border='0'></html:button>
			      	</td>			      				      				      	
				</tr>
			   </logic:iterate>
		   	</logic:present>
		   </table>
		   <table width="100%" id="resultadoTable">
		   		<tr>
					<th><bean:message bundle="headers" key="app.header.montoAcumulado" /></th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>					
				</tr>
		   </table>
   		</td>
   </tr>
</table>