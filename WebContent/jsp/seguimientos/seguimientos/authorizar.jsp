<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.authorizarSeguimiento"/></h3> 

	<%-- Header Finalizar Control de Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraSeguimiento.jsp" flush="true"/>

	<!--  Cuerpo Finalizar Control de Proyecto -->
	<jsp:include page="authorizarDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
</div>
