<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>

<html:form action="/ItemsProcedimientoInventarioFiltrar">
	<toolbar:table model="list" requestURI="/ItemsProcedimientoInventarioFiltrar.do" />	
</html:form>




