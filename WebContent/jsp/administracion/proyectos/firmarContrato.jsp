<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.firmarContrato"/></h3> 

	<%-- Header Firmar Contrato Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>
 
	<!--  Cuerpo Firmar Contrato Proyecto -->
	<jsp:include page="firmarContratoDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
	
</div>
