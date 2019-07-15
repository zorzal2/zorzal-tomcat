<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<script language="javascript">
	// cambio de accion, hay diferencia para la primer evaluación y las siguientes
	function swapAction() {
	  
	  formAction = document.getElementById("EvaluarProyectoDynaForm").action;
	  if (formAction == "/EvaluarProyectoNuevamente")
	  {
	  	newAction = '<html:rewrite page="/EvaluarProyectoNuevamenteGuardar"/>';
	  	document.getElementById("EvaluarProyectoDynaForm").action = newAction;
	  }
	}
			
	function isSelected( id ){
		return window.document.getElementById(id).checked==true;
	}
	
	function updateTipoEvaluacion(){
		var tipoEvaluacion = window.document.getElementById('tipoEvaluacion');
		if(isSelected('esTecnica') || isSelected('esEconomica') || isSelected('esFinanciera') )
			tipoEvaluacion.value="selected";
		else
			tipoEvaluacion.value = "";
	}
	
	function populateTipoEvalucion(){
		toggleVisibility('divComision', isSelected('esFinanciera'));
	}
	
	
	function deleteEvaluador(button){
		alert( button);
		var table = window.document.getElementById("tablaEvaluador");
		alert( table);
		deleteRow(button, table);
	}
	
	function setFechaComp() {
		var fechaInicial = window.document.getElementById('fechaInicial');
		var fecha = fechaInicial.value.split("/");
		var one_day=1000*60*60*24;

    	aquel_dia = new Date (fecha[2], fecha[1]-1, fecha[0]);
	    aquel_dia= new Date(aquel_dia.getTime() + 20 * one_day);
//	    alert(aquel_dia);
	    var month = aquel_dia.getMonth()+1;
		fecha = aquel_dia.getDate() + '/' + month + '/' + aquel_dia.getYear(); 
		var fechaEntrega = window.document.getElementById('fechaEntrega');
		if (fechaEntrega.value == '') {
//			alert(fechaEntrega);
			fechaEntrega.value = fecha;
		}
//		alert(fecha);
//		alert(aquel_dia.toLocaleString());
	}
	</script>

<body onLoad="populateTipoEvalucion()">
<html:form action="/EvaluarProyectoGuardar">

<html:hidden property="idProyecto"/>
<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio" width="20%">
			<bean:message bundle="labels" key="app.label.tipo"/>
		</td>

		<td align="left">
			<html:checkbox property="esTecnica" styleId="esTecnica" onclick="updateTipoEvaluacion()"/>&nbsp;Técnica
			<html:checkbox property="esEconomica"  styleId="esEconomica" onclick="updateTipoEvaluacion()"/>&nbsp;Económica
			<html:checkbox property="esFinanciera" styleId="esFinanciera" onclick="toggleVisibility('divComision',this.checked==true);updateTipoEvaluacion();"/>&nbsp;Financiera&nbsp;
			<div id="divComision" style="position:absolute;visibility=hidden;">
				<html:select property="tipoEvaluacionFinanciera">
					<html:options collection="tipoEvaluacionFinanciera" property="value" labelProperty="label"/>
				</html:select>
			</div>
		</td>
	</tr>		
	<!-- requerido para validar que se haya seleccionado al menos un tipo  -->
	<tr><td><html:hidden property="tipoEvaluacion"/></td><td><pragmatags:error property="tipoEvaluacion"/></td></tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaInicial"/>
		</td>
		<td align="left">
			<pragmatags:calendar propertyName="fechaInicial" top="0" left="0" onChange="setFechaComp()"/>
			<pragmatags:error property="fechaInicial" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaEntrega"/>
		</td>
		<td align="left">
			<pragmatags:calendar propertyName="fechaEntrega" top="0" left="0"/>
			<pragmatags:error property="fechaEntrega" />
		</td>
	</tr>
	
	<br>
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.evaluadores" />
		</th>
	</tr>
	<tr>
		<td colspan="2">
			<pragmatags:btnAgregar javascript="popUpEvaluacionEvaluador('tablaEvaluador');"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<table id="tablaEvaluador" border="0" width="100%" class="inventario">
			<tr>
				<th>Entidad Evaluadora</th>
				<th>Evaluador</th>
				<th>Lugar de evaluación</th>
				<th>Acciones</th>
 			 </tr>
 			 <c:if test="${!empty evaluadores}">
			<c:forEach items="${evaluadores}" var="evaluador">
			<tr>
				<input id="idEntidadEvaluadora" name="idEntidadEvaluadora" value="${evaluador.idEntidadEvaluadora}" type="hidden"/>
				<input id="idEvaluador" name="idEvaluador" value="${evaluador.idEvaluador}" type="hidden"/>
				<input id="lugar" name="lugar" value="${evaluador.lugar}" type="hidden"/>										
				<td><c:out value="${evaluador.entidadEvaluadora}"/></td>
				<td><c:out value="${evaluador.evaluador}"/></td>
				<td><c:out value="${evaluador.lugar}"/></td>
				<td><button name="Delete" onclick="deleteRow(this , 'tablaEvaluador')"><img src="images/eliminar.gif"/></button></td>					
			</tr>
			</c:forEach>
			</c:if>
		</table>			
		
		</td>
	</tr>
	<tr><td colspan="2"><pragmatags:error property="idEntidadEvaluadora"/></td></tr>
</table>

<br>
<table class="formulario">		
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</th>
	</tr>
	<tr>
		<td>
	   		<html:textarea property="observaciones" rows="4" cols="100" />
			<pragmatags:error property="observacion" />
   		</td>
	</tr>	
</table>
</html:form>
</body>