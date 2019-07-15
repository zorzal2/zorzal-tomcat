<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3>
		<fmt:bundle basename="resources.Titles">
			<fmt:message key="app.title.finalizarProyecto" />
		</fmt:bundle>
	</h3> 
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>
	
	<!--  Detalle Anular Proyecto -->
	<jsp:include page="finalizarDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
</div>