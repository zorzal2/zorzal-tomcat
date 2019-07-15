<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<script type="text/javascript">
function testingCrit() {
	var nombre = document.getElementsByName('idTipoProyecto')[0].value;
//	var nombre = document.getElementsByName('idTipoProyecto')[0].options[document.getElementsByName('idTipoProyecto')[0].selectedIndex].text;
	var id = document.getElementsByName('idEvaluacion')[0].value;
	var url= "CargarCriteriosResultado.do?nombre="+nombre+"&idEvaluacion="+id;
//		AbrirPopupBloqueante(url,'700','580');
 	window.open(url,"CriteriosResultadoEditarDynaForm","width=700,height=580,left=100,top=70,resizable=yes,status=yes,help=yes,scrollbars=1");
}

function editarPresupuesto() {
	var idEvaluacion = document.getElementsByName('idEvaluacion')[0].value;
	var url= "EditarPresupuestoEvaluacionIngresar.do?idEvaluacion="+idEvaluacion;
 	window.open(url,"EditarPresupuestoEvaluacion","width=700,height=580,left=100,top=70,resizable=yes,status=yes,help=yes,scrollbars=1");
}
</script>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.cargarEvaluacion"/></h3>

	<jsp:include page="/jsp/administracion/evaluacion/cabecera${evaluacion.proyecto.class.simpleName}.jsp" flush="true"/>
	
	<!--  Detalle Evaluacion -->
	<jsp:include page="/jsp/administracion/evaluacion/visualizarDatos.jsp" flush="true"/>

	<html:form action="/CargarEvaluacionGuardar">
		<html:hidden property="idEvaluacion"/>	
		<html:hidden property="idProyecto"/>	
	
		<table class="formulario">
		<tr>
				<th colspan="3" class="titulo">
					<bean:message bundle="headers" key="app.header.resultado" />
				</th>
			</tr>
			<tr>
				<td class="obligatorio" >
					<bean:message bundle="labels" key="app.label.fechaEvaluacion"/>
				</td>		
				<td align="left">
					<pragmatags:calendar propertyName="fechaEvaluacion" top="0" left="0"/>	
					<pragmatags:error property="fechaEvaluacion" />
				</td>
				<td>
					<c:if test="${evaluacion.aceptaEdicionPresupuesto}">
						<pragmatags:btnDynaLabel javascript="editarPresupuesto()" label="app.label.editarPresupuesto"/>	
					</c:if>
				</td>		
			</tr>
			
			<c:if test="${!empty tiposProyectos}">
				<tr>
					<td class="opcional">
						<bean:message bundle="labels" key="app.label.tipoProyecto"/>
					</td>
		
					<td>
						<html:select property="idTipoProyecto">
							<html:options collection="tiposProyectos" property="value" labelProperty="label"/>
						</html:select>
					</td>
					<td align="left">
						<pragmatags:btnDynaLabel javascript="testingCrit()" label="app.label.cargarCriterios" />
					</td>
				</tr>
			</c:if>
		
			
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.aceptaEvaluacion"/>
				</td>		
				<td align="left">
					<html:radio property="aceptaEvaluacion" value="true" > 
						<bean:message bundle="labels" key="app.label.aceptaEvalSI"/>
					</html:radio>
					&nbsp;
					<html:radio property="aceptaEvaluacion" value="false"> 
						<bean:message bundle="labels" key="app.label.aceptaEvalNO"/>
					</html:radio>
					<pragmatags:error property="aceptaEvaluacion" />
				</td>
			</tr>
			<tr>
				<td class="obligatorio" >
					<bean:message bundle="labels" key="app.label.fundamentacion"/>
				</td>		
				<td align="left">
			   		<html:textarea property="fundamentacion" rows="4" cols="100" />
					<pragmatags:error property="fundamentacion" />
				</td>
			</tr>		
		</table>
		
		<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>	
	</html:form>
</div>