<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>

<h3>
	<bean:message bundle="titles" key="app.title.fuenteFinanciamiento"/>
</h3>

<html:form action="/FuenteFinanciamientoInventarioFiltrar">
	<pragmatags:btnAgregar action="/FuenteFinanciamientoAgregar" permissions="FUENTESFINANCIAMIENTO-AGREGAR"/>			
	<toolbar:table model="list" requestURI="/FuenteFinanciamientoInventarioFiltrar.do" />	
</html:form>
