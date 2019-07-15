<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.visualizarPaquete"/></h3> 

	<%-- Header Visualizar Paquete --%>
	<jsp:include page="/jsp/cabecera/cabeceraPaquete.jsp" flush="true"/>

	<!--  Detalle Visualizar Paquete -->
	<jsp:include page="visualizarDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnCerrar/>	
	
</div>
