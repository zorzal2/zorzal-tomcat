<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3>
		<fmt:bundle basename="resources.Titles">
			<fmt:message key="app.title.evaluar${clase}" />
		</fmt:bundle>	
	</h3>	
	
	<%-- Header Agregar Evaluacion de Proyecto o IdeaProyecto --%>
	<jsp:include page="/jsp/cabecera/cabecera${clase}.jsp" flush="true"/>

	<!-- Cuerpo Agregar Evaluacion de Proyecto o IdeaProyecto -->
	<jsp:include page="agregarEvaluacionDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
	
</div>
