<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<h3>
	<bean:message bundle="titles" key="app.title.entidad"/>	
</h3>
<html:form action="/EntidadConfiguracionInventarioFiltrar">	
	<table class="filtros">
			<tr>
				<td>				
					<rapidFilters:textFilter 
						property="cuit" 
						title="app.label.cuit" 
						filterType="filter.type.string.contains" 
						classNameType="java.lang.String"/>
				</td>				
				<td>				
					<rapidFilters:textFilter 
						property="denominacion" 
						title="app.label.denominacion" 
						filterType="filter.type.string.contains" 
						classNameType="java.lang.String"/>
				</td>
				<td>
					<rapidFilters:comboFilter property="activo" 
											  title="app.label.activo" 
										      filterType="filter.type.expresion.compleja" 
											  classNameType="java.lang.Boolean" 
											  options="filtroEsActivo" 
											  labelProperty="label" 
											  valueProperty="value"/>
				</td>
				<td>
					<rapidFilters:applyFilters />
				</td>
				
				<td>
					<rapidFilters:clearFilters />
				</td>
	
			</tr>
	</table>
	
	<pragmatags:btnAgregar action="/EntidadAgregar" permissions="ENTIDADES-AGREGAR"/>			
	<toolbar:table model="list" requestURI="/EntidadConfiguracionInventarioFiltrar.do" />	
</html:form>