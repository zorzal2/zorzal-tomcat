<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<%-- Titulo --%>
	<h3><bean:message bundle="titles" key="app.title.confirmarEvaluacion"/></h3>

	<%-- Header Finalizar Control de Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraEvaluacionSeguimiento.jsp" flush="true"/>

	<jsp:include page="/jsp/administracion/evaluacion/visualizarDatos.jsp" flush="true"/>
	<br>
	<jsp:include page="/jsp/seguimientos/resumenDeRendicion.jsp" flush="true"></jsp:include>

	<html:form action="/ConfirmarEvaluacionSeguimientoGuardar">

		<html:hidden property="idEvaluacion"/>	
		<html:hidden property="idSeguimiento"/>	
	
		<table class="formulario">
			<tr>
				<th class="titulo">
					<bean:message bundle="labels" key="app.label.observaciones" />
				</th>
			</tr>
			<tr>
				<td class="opcional" align="left">
			   		<html:textarea property="observaciones" rows="4" cols="100" />
					<pragmatags:error property="observaciones" />
				</td>
			</tr>		
		</table>
	
		<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>	
		
	</html:form>
	
</div>