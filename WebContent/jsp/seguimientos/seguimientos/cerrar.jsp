<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>


<div>
	<h3>
		<fmt:bundle basename="resources.Titles">
			<fmt:message key="app.title.cerrarSeguimiento" />
		</fmt:bundle>
	</h3> 

	<jsp:include page="/jsp/cabecera/cabeceraSeguimiento.jsp" flush="true" />		
	
	<jsp:include page="cerrarDatos.jsp" flush="true"/>

	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
</div>