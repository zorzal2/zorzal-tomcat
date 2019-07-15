<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div>
	<!-- Titulo -->
	<h3>
		<c:choose>
			<c:when test="${tipoPaquete == 'COMISION'}">
				<bean:message bundle="titles" key="app.title.paqueteConfirmarComision"/>
			</c:when>
			<c:when test="${tipoPaquete == 'SECRETARIA'}">
				<bean:message bundle="titles" key="app.title.paqueteConfirmarSecretaria"/>
			</c:when>
			<c:when test="${tipoPaquete == 'DIRECTORIO'}">
				<bean:message bundle="titles" key="app.title.paqueteConfirmarDirectorio"/>
			</c:when>
		</c:choose>
	</h3>

	<%-- Header Confirmar Paquete --%>
	<jsp:include page="/jsp/cabecera/cabeceraPaquete.jsp" flush="true"/>

	<!--  Detalle Confirmar Paquete -->
	<jsp:include page="confirmarDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/PaqueteInventario"/>	
	
</div>
