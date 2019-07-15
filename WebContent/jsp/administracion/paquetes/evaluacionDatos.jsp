<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body>
<html:form action="/EvaluarPaqueteGuardar">

<html:hidden property="id"/>

<!-- tabla con los datos de ProyectoFilaDTO's  -->
<jsp:include page="visualizacionProyectoFilas.inc.jsp" flush="true">
	<jsp:param name="uri" value="PaqueteInventario.do" />
	<jsp:param name="actions" value="comboResultado,linkVerPresupuesto,hiddenArrays" />
</jsp:include>
<br>
<table class="formulario">
	<tr>
		<c:choose>
		  <c:when test="${tipoPaquete == 'COMISION'}">
			<td class="opcional">
				<bean:message bundle="headers" key="app.header.nroActa" />
			</td>
		  </c:when>
		  <c:when test="${tipoPaquete == 'SECRETARIA'}">
			<td class="opcional">
				<bean:message bundle="headers" key="app.header.nroActa" />
			</td>
		  </c:when>
		  <c:when test="${tipoPaquete == 'DIRECTORIO'}">
			<td class="opcional">
				<bean:message bundle="headers" key="app.header.nroResolucion" />
			</td>
		  </c:when>
		</c:choose>
		<td>
			<html:text property="codigoActa" maxlength="20"/>
			<pragmatags:error property="codigoActa" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</td>
		<td align="left">
			<html:textarea property="observacion" rows="3" cols="100" />
			<pragmatags:error property="observacion" />
		</td>
	</tr>
</table>
</html:form>
</body>