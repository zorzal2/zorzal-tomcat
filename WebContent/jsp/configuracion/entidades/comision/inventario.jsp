<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<h3>
	<bean:message bundle="titles" key="app.title.comision"/>	
</h3>

<html:form action="/ComisionInventarioFiltrar">	
	<table class="filtros">
			<tr>
				<td width="90%">				
					<rapidFilters:textFilter 
						property="denominacion" 
						title="app.label.denominacion" 
						filterType="filter.type.string.contains" 
						classNameType="java.lang.String"/>
				</td>				
				<td>
					<rapidFilters:applyFilters />
				</td>
				
				<td>
					<rapidFilters:clearFilters />
				</td>
	
			</tr>
	</table>
	<pragmatags:btnAgregar action="/ComisionAgregar" permissions="COMISIONES-AGREGAR"/>					
	<toolbar:table model="list" requestURI="/ComisionInventarioFiltrar.do" />	
</html:form>