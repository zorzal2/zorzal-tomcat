<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div>
	<!-- Titulo -->
	<h3>
		<c:choose>
			<c:when test="${tipoPaquete == 'COMISION'}">
				<bean:message bundle="titles" key="app.title.paqueteEvaluarComision"/>
			</c:when>
			<c:when test="${tipoPaquete == 'SECRETARIA'}">
				<bean:message bundle="titles" key="app.title.paqueteEvaluarSecretaria"/>
			</c:when>
			<c:when test="${tipoPaquete == 'DIRECTORIO'}">
				<bean:message bundle="titles" key="app.title.paqueteEvaluarDirectorio"/>
			</c:when>
		</c:choose>
	</h3>

	<%-- Header Evaluar Paquete --%>
	<jsp:include page="/jsp/cabecera/cabeceraPaquete.jsp" flush="true"/>

	<!--  Detalle Evaluar Paquete -->
	<jsp:include page="evaluacionDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/PaqueteInventario"/>	
	
</div>
