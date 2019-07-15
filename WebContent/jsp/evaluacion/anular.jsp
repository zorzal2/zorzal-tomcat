<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<div>
	<!--  Titulo  -->
	<h3><bean:message bundle="titles" key="app.title.anularEvaluacion"/></h3>

	<%-- Header Proyecto + Evaluacion --%>
	<jsp:include page="/jsp/administracion/evaluacion/cabecera${evaluacion.proyecto.class.simpleName}.jsp" flush="true"/>

	<!--  Detalle Evaluacion -->
	<jsp:include page="/jsp/administracion/evaluacion/visualizarDatos.jsp" flush="true"/>
	
	<!--  Resultado -->
	<html:form action="/AnularEvaluacionGuardar">
		<html:hidden property="idEvaluacion"/>	
		<html:hidden property="idProyecto"/>	

		<table class="formulario">
			<tr>
				<th colspan="2" class="titulo">
					<bean:message bundle="headers" key="app.header.observaciones" />
				</th>
			</tr>
			<tr>
				<td class="opcional" align="left">
			   		<html:textarea property="observaciones" rows="4" cols="100" />
					<pragmatags:error property="observaciones" />
				</td>
			</tr>		
		</table>
		
		<!-- Aceptar , Cancelar -->
		<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>	
		
	</html:form>
	
</div>