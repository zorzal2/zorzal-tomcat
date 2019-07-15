<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>


<div>

	<!-- Titulo -->
	<h3>
		<fmt:bundle basename="resources.Titles">
			<fmt:message key="app.title.anularSeguimiento" />
		</fmt:bundle>
	</h3> 

	<!-- Cabecera -->
	<jsp:include page="/jsp/cabecera/cabeceraSeguimiento.jsp" flush="true" />		
	
	<!-- Detalle Anular Seguimiento -->	
	<jsp:include page="anularDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
	
</div>