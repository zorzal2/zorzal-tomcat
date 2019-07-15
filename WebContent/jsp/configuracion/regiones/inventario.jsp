<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>

<h3>
	<bean:message bundle="titles" key="app.title.region"/>
</h3>

<html:form action="/RegionInventarioFiltrar">
	<pragmatags:btnAgregar action="/RegionAgregar" permissions="REGIONES-AGREGAR"/>			
	<toolbar:table model="list" requestURI="/RegionInventarioFiltrar.do" />	
</html:form>