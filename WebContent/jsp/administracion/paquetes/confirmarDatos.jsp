<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<body>
<html:form action="/ConfirmarPaqueteGuardar">
<html:hidden property="id"/>

<!-- tabla con los datos de ProyectoFilaDTO's  -->
<jsp:include page="visualizacionProyectoFilas.inc.jsp" flush="true">
	<jsp:param name="uri" value="PaqueteInventario.do" />
	<jsp:param name="actions" value="hiddenArrays" />
</jsp:include>
<!-- tabla con los datos de ProyectoFilaDTO's  -->

<br>
<table class="formulario">
	<tr>
		<td class="obligatorio">						
			<c:set var="nroField" value="nroActa" />
			<c:if test="${tipoPaquete == 'DIRECTORIO'}">
				<c:set var="nroField" value="nroResolucion" />
			</c:if>
			
			<fmt:bundle basename="resources.FieldLabels">
				<fmt:message key="app.label.${nroField}"/>
			</fmt:bundle>
		</td>
		<td>
			<html:text property="codigoActa" maxlength="20"/>
			<pragmatags:error property="codigoActa" />
		</td>
	</tr>
	<tr>
		<td class="opcional" >
			<bean:message bundle="headers" key="app.header.observaciones" />
		</td>
		<td>
			<html:textarea property="observacion" rows="3" cols="100" />
			<pragmatags:error property="observacion" />
		</td>
	</tr>
</table>

</html:form>
</body>
