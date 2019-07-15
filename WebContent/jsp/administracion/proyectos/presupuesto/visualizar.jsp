<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<!-- Titulo -->
<%-- h3><bean:message bundle="titles" key="app.title.presupuesto"/></h3 --%> 

<%-- jsp:include page="/jsp/cabecera/cabeceraProyectoRaiz.jsp" flush="true"/--%>
<c:if test="${!empty presupuesto}">
	<c:choose>
		<c:when test="${!empty presupuesto.id}">
			<tiles:insert beanName="paginaDetalle"/>
			<br/>
			<html-el:link action="/EditarPresupuestoDownload.do?idPresupuesto=${presupuesto.id}">
				<img 
					src="images/download.gif"
					title="<bean:message bundle="labels" key="app.label.download"/>"
					border="0"/>
			</html-el:link>
			<html-el:link action="/EditarPresupuestoDownload.do?idPresupuesto=${presupuesto.id}">
				<bean:message bundle="labels" key="app.label.download"/>
			</html-el:link>
			<br/>
		</c:when>
		<c:otherwise>
			<h2>
				<bean:message key="app.msj.noHayPresupuesto" bundle="informationals"/>
			</h2>
		</c:otherwise>					
	</c:choose>

</c:if>
<c:if test="${!empty message}">
	<h2>
		<bean:message key="${message}" bundle="${bundle}"/>
	</h2>
</c:if>

