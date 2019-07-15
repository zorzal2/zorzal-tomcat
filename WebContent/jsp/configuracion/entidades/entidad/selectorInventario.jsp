<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html:form action="/EntidadSelectorPopUp">	
	<table class="filtros">
		<tr>
			<td width="45%">			
				<rapidFilters:textFilter  property="denominacion"  title="app.label.denominacion"  filterType="filter.type.string.contains" classNameType="java.lang.String"/>
			</td>
			<td width="45%">				
				<rapidFilters:textFilter  property="cuit"  title="app.label.cuit"  filterType="filter.type.string.contains"  classNameType="java.lang.String"/>
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
	<pragmatags:btnAgregar action="/EntidadSelectorAgregar"/>
	<toolbar:table model="list" requestURI="/EntidadSelectorFiltrar.do" />	
</html:form>