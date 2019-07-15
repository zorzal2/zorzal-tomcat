<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html:form action="/EntidadBeneficiariaSelSelectorPopUp">	
	<table class="filtros">
		<tr>
			<td width="45%">			
				<rapidFilters:textFilter  property="entidad.denominacion"  title="app.label.denominacion"  filterType="filter.type.string.contains" classNameType="java.lang.String"/>
			</td>
			<td width="45%">				
				<rapidFilters:textFilter  property="entidad.cuit"  title="app.label.cuit"  filterType="filter.type.string.contains"  classNameType="java.lang.String"/>
			</td>				
			<td>
				<rapidFilters:applyFilters />
			</td>
			<td>
				<rapidFilters:clearFilters />
			</td>
		</tr>
	</table>
	<br>
	<toolbar:table model="list" requestURI="/EntidadBeneficiariaSelInventarioFiltrar.do" />	
</html:form>