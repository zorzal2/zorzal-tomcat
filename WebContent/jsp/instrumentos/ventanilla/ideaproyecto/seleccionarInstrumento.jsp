<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.cargarProyectoSelecciónInstrumentoVentanilla"/></h3> 

	<%-- Header Seleccionar Ventanilla de Idea Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraIdeaProyecto.jsp" flush="true"/>

	<!--  Cuerpo Seleccionar Ventanilla de Idea Proyecto -->
	<jsp:include page="seleccionarInstrumentoDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/IdeaProyectoInventario"/>	
</div>
