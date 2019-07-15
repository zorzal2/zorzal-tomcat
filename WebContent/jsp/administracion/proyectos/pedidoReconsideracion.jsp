<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.pedidoReconsideracion${clase}"/></h3> 

	<%-- Header Reconsideracion de Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabecera${clase}.jsp" flush="true"/>

	<!--  Cuerpo Reconsideracion Proyecto -->
	<jsp:include page="pedidoReconsideracionDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
</div>
