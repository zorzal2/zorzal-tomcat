<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.finalizarPosibilidadReconsideracion"/></h3> 

	<%-- Header Reconsideracion de Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

	<!--  Cuerpo Reconsideracion Proyecto -->
	<jsp:include page="reconsideracionDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
</div>
