<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<c:if test="${!empty proyecto.codigo}" >
		<h3>
			<fmt:bundle basename="resources.Titles">
				<fmt:message key="app.title.cerrarProyecto" />
			</fmt:bundle>
		</h3> 
		<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>
	</c:if>
	<c:if test="${!empty ideaProyecto.codigoIdeaProyecto}" >
		<h3>
			<fmt:bundle basename="resources.Titles">
				<fmt:message key="app.title.cerrarIdeaProyecto" />
			</fmt:bundle>
		</h3> 
		<jsp:include page="/jsp/cabecera/cabeceraIdeaProyecto.jsp" flush="true"/>
	</c:if>

	<!--  Detalle Cerrar Proyecto -->
	<jsp:include page="cerrarDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
	
</div>
