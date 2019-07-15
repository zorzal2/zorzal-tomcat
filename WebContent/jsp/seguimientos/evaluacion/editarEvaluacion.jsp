<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<body>

<script language="javascript">

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
	    var month = aquel_dia.getMonth()+1;
		fecha = aquel_dia.getDate() + '/' + month + '/' + aquel_dia.getYear(); 
		var fechaEntregaComprometida = window.document.getElementById('fechaEntregaComprometida');
		if (fechaEntregaComprometida.value == '') {
			fechaEntregaComprometida.value = fecha;
		}
	}
	
</script>
	
<h3>
	<bean:message bundle="titles" key="app.title.evaluacionSeguimiento.editarEvaluacion"/>
</h3>	

<jsp:include page="/jsp/cabecera/cabeceraEvaluacionSeguimiento.jsp" flush="true"/>

<html:form action="/GuardarEdicionEvaluacion">

<html:hidden property="id" />

<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio" style="width:15%">
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
			<pragmatags:calendar propertyName="fechaEntregaComprometida" top="0" left="0"/>
			<pragmatags:error property="fechaEntregaComprometida" />
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
 			<c:if test="${!empty evaluacion.evaluadores}">
			<c:forEach items="${evaluacion.evaluadores}" var="evaluador">
			<tr>
				<input id="idEntidadEvaluadora" name="idEntidadEvaluadora" value="${evaluador.idEntidadEvaluadora}" type="hidden"/>
				<input id="idEvaluador" name="idEvaluador" value="${evaluador.idEvaluador}" type="hidden"/>
				<input id="lugar" name="lugar" value="${evaluador.lugar}" type="hidden"/>										
				<td><c:out value="${evaluador.entidadEvaluadora}"/></td>
				<td><c:out value="${evaluador.evaluador}"/></td>
				<td><c:out value="${evaluador.lugar}"/></td>
				<td><button name="Delete" onclick="deleteRow(this,'tablaEvaluador')"><img src="images/eliminar.gif"/></button></td>					
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
	   		<html:textarea property="observacion" rows="4" cols="100" />
			<pragmatags:error property="observacion" />
   		</td>
	</tr>	
</table>	

<pragmatags:btnOkCancel okJavascript="submitForm()"  cancelAction="/VisualizarEvaluacionSeguimiento.do"/>	
	
</html:form>
</body>

