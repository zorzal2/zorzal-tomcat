<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<br>
<html:form action="/RendicionEvaluacionSeguimientoCFInventario">
	<toolbar:table model="list" requestURI="/RendicionEvaluacionSeguimientoCFInventario.do" />	
</html:form>

<c:if test="${list.fullListSize > 0}">
	<html:link href="#" onclick="popUpControlFacturas();">
		<bean:message bundle="labels" key="app.label.controlarFacturas"/>		
	</html:link>
</c:if>

<br><br>