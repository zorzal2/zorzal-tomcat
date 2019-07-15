<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<div>
	<!-- Titulo -->
	<c:set var="headerKey" value="app.title.agregarIdeaProyecto" />
	<c:if test="${!empty ideaProyecto.codigoIdeaProyecto}" >
		<c:set var="headerKey" value="app.title.edicionIdeaProyecto" />
	</c:if>	
	<h3>
		<fmt:bundle basename="resources.Titles">
			<fmt:message key="${headerKey}"/>
		</fmt:bundle>
	</h3>	
	
	<%-- Header Editar Idea Proyecto --%>
	<c:if test="${!empty ideaProyecto.codigoIdeaProyecto}" >
		<jsp:include page="/jsp/cabecera/cabeceraIdeaProyecto.jsp" flush="true"/>
	</c:if>

	<!--  Cuerpo Editar Idea Proyecto -->
	<jsp:include page="editarDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="IdeaProyectoInventario"/>
</div>
