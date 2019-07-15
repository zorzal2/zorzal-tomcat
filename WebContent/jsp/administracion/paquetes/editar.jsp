<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div>
	<!-- Titulo -->
	<h3>
		<c:choose>
			<c:when test="${tipoPaquete == 'COMISION'}">
				<bean:message bundle="titles" key="app.title.proyectosParaComision"/>
			</c:when>
			<c:when test="${tipoPaquete == 'SECRETARIA'}">
				<bean:message bundle="titles" key="app.title.proyectosParaSecretaria"/>
			</c:when>
			<c:when test="${tipoPaquete == 'DIRECTORIO'}">
				<bean:message bundle="titles" key="app.title.proyectosParaDirectorio"/>
			</c:when>
		</c:choose>
	</h3>	

	<%-- Header Editar Paquete --%>
	<jsp:include page="/jsp/cabecera/cabeceraPaquete.jsp" flush="true"/>

	<!--  Detalle Editar Paquete -->
	<jsp:include page="editarDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="PaqueteInventario"/>
	
</div>
