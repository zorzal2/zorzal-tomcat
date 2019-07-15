<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.proyectoVisualizarAdmisibilidad"/></h3> 

	<%-- Header Evaluar Admisibilidad Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

	<!--  Detalle Evaluar Admisibilidad Proyecto -->
	<jsp:include page="printAdmisibilidadDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnCerrar action="/VisualizarAdmisibilidadProyecto.do?id=${param.id}"/>	
	
</div>
