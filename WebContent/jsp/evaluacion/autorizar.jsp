<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<div>
	<%-- Titulo --%>
	<h3><bean:message bundle="titles" key="app.title.autorizarEvaluacion"/></h3>

	<%-- Header Proyecto + Evaluacion --%>
	<jsp:include page="/jsp/administracion/evaluacion/cabecera${evaluacion.proyecto.class.simpleName}.jsp" flush="true"/>

	<!--  Detalle Evaluacion -->
	<jsp:include page="/jsp/administracion/evaluacion/visualizarDatos.jsp" flush="true"/>


	<html:form action="/AutorizarEvaluacionGuardar">
		<html:hidden property="idEvaluacion"/>	
		<html:hidden property="idProyecto"/>	
	
		<table class="formulario">

			<!--  Resultado -->
			<tr>
				<th colspan="2" class="titulo">
					<bean:message bundle="headers" key="app.header.resultado" />
				</th>
			</tr>
			<tr>
				<td class="obligatorio" >
					<bean:message bundle="labels" key="app.label.autorizaEvaluacion"/>
				</td>		
				<td align="left">
					<html:radio property="autorizaEvaluacion" value="1"> 
						<bean:message bundle="labels" key="app.label.si"/>
					</html:radio>
					&nbsp;
					<html:radio property="autorizaEvaluacion" value="2"> 
						<bean:message bundle="labels" key="app.label.no"/>
					</html:radio>
					<pragmatags:error property="autorizaEvaluacion" />
				</td>
			</tr>
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.fundamentacion"/>
				</td>		
				<td align="left">
			   		<html:textarea property="fundamentacion" rows="4" cols="100" />
					<pragmatags:error property="fundamentacion" />
				</td>
			</tr>		
		</table>
		
		<!--  Guardar , Cancelar -->
		<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>	
		
	</html:form>

</div>




