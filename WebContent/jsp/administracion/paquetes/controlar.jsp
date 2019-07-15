<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.controlarDirector"/></h3>

	<%-- Header Controlar Paquete --%>
	<jsp:include page="/jsp/cabecera/cabeceraPaquete.jsp" flush="true"/>

	<!--  Detalle Controlar Paquete -->
	<jsp:include page="controlarDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="PaqueteInventario"/>
</div>
