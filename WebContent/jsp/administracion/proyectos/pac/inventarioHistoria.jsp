<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<script type="text/javascript" src="js/libreria.js"></script>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.historia"/></h3> 

	<%-- Header Editar Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>
	<jsp:include page="visualizarItem.jsp" flush="true"/>
 	<jsp:include page="visualizarDesembolso.jsp" flush="true"/>

<html:form action="/HistoriaPACInventario">
<c:if test="${empty OPCION_CORTA or OPCION_CORTA==false}">
	<h3>
		<bean:message bundle="titles" key="app.title.procedimientos"/>
	</h3> 
	<div>	
		<toolbar:table model="list" requestURI="/HistoriaPACInventario.do" />	
	</div>
	<!-- Volver al inventario -->
	<pragmatags:btnCerrar action="/ProyectoPACInventario.do"/>
</c:if>	
<c:if test="${!(empty OPCION_CORTA or OPCION_CORTA==false)}">
     <pragmatags:btnCerrar action="/VisualizarItemsProcedimientoInventario.do"/>
</c:if>	
</html:form>
</div>

	

