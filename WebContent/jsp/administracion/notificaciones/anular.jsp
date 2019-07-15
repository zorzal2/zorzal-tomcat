<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div>
	<!-- Titulo -->
	<h3>
		<bean:message bundle="titles" key="app.title.anularNotificacion"/>
	</h3>	

	<%-- Header Editar Paquete --%>
	<jsp:include page="/jsp/cabecera/cabecera${notificacion.proyectoRaiz.clase}.jsp" flush="true"/>

	<!--  Detalle Editar Paquete -->
	<jsp:include page="anularDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()"  cancelAction="/WFCancelarTarea.do"/>	
	
</div>
