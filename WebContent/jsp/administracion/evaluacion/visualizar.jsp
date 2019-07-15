<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.visualizarEvaluacion"/></h3> 

	<%-- Header Proyecto + Evaluacion --%>
	<jsp:include page="cabecera${evaluacion.proyecto.class.simpleName}.jsp" flush="true"/>
	
	<!--  Detalle Evaluacion -->
	<jsp:include page="visualizarDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<pragmatags:btnCerrar/>	
	
</div>