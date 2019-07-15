<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>


<html:form action="/PersonaSelectorPopUp">	
	<!--  Filtros -->
	<jsp:include page="selectorInventario.jsp" flush="true"/>
	
	<pragmatags:btnAgregar action="/PersonaSelectorAgregar"/>			
	<toolbar:table model="list" requestURI="/PersonaSelectorFiltrar.do" />	
</html:form>
