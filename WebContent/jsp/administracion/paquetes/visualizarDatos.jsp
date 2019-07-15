<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<html:form action="/VisualizarPaquete">

<html:hidden property="id"/>
<!-- tabla con los datos de ProyectoFilaDTO's  -->
<jsp:include page="visualizacionProyectoFilas.inc.jsp" flush="true">
	<jsp:param name="uri" value="VisualizarPaquete.do" />
	<jsp:param name="actions" value="" />
</jsp:include>
<!-- tabla con los datos de ProyectoFilaDTO's  -->

<table>
	<tr>
		<td class="obligatorio" width="10%">						
			<c:set var="nroField" value="nroActa" />
			<c:if test="${tipoPaquete == 'Directorio'}">
				<c:set var="nroField" value="nroResolucion" />
			</c:if>
			<fmt:bundle basename="resources.FieldLabels">
				<fmt:message key="app.label.${nroField}"/>&nbsp;:&nbsp;
			</fmt:bundle>
		</td>
		<td class="opcional" align="left" width="90%">
			<bean:write name="PaqueteVisualizarDynaForm" property="codigoActa" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio" width="10%">
			<bean:message bundle="headers" key="app.header.observaciones"/>&nbsp;:&nbsp;
		</td>
		<td class="opcional" align="left" width="90%">
			<bean:write name="PaqueteVisualizarDynaForm" property="observacion"/>
		</td>
	</tr>
</table>
</html:form>