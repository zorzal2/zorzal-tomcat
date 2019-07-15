<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.anularPaquete"/></h3>

	<%-- Header Anular Paquete --%>
	<jsp:include page="/jsp/cabecera/cabeceraPaquete.jsp" flush="true"/>

	<!--  Detalle Anular Paquete -->
	<jsp:include page="anularDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>		
</div>
