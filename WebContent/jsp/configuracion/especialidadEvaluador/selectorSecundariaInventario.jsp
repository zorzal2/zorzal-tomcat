<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html:form action="/SecundariaSelectorPopUp">	
	<table class="filtros">
		<tr>
			<td width="45%">			
				<rapidFilters:textFilter  property="nombre"  title="app.label.nombre"  filterType="filter.type.string.contains" classNameType="java.lang.String"/>
			</td>
			<td width="45%">				
				<rapidFilters:textFilter  property="codigo"  title="app.label.codigo"  filterType="filter.type.string.contains"  classNameType="java.lang.String"/>
			</td>				
			<td>
				<rapidFilters:applyFilters />
			</td>
			<td>
				<rapidFilters:clearFilters />
			</td>
		</tr>
	</table>
	<br/>
	<toolbar:table model="list" requestURI="/SecundariaSelectorFiltrar.do" />	
	
</html:form>