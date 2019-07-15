<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<h3>
	<bean:message bundle="titles" key="app.title.personas"/>
</h3>

<html:form action="/PersonaInventarioFiltrar">	
	<table class="filtros">
			<tr>
				<td>
					<rapidFilters:textFilter 
						property="nombre" 
						title="app.label.nombre" 
						filterType="filter.type.string.contains" 
						classNameType="java.lang.String"/>
				</td>
				<td>				
					<rapidFilters:textFilter 
						property="cuit" 
						title="app.label.cuit" 
						filterType="filter.type.string.contains" 
						classNameType="java.lang.String"/>
				</td>
				<td>				
						<rapidFilters:comboFilter property="esEvaluador" 
										  title="app.label.esEvaluador" 
										  filterType="filter.type.boolean.equal" 
										  classNameType="java.lang.Boolean" 
										  options="filtroEsEvaluador" 
										  labelProperty="label" 
										  valueProperty="value"/>
				</td>
				<td>				
						<rapidFilters:comboFilter property="activo" 
										  title="app.label.activo" 
										  filterType="filter.type.boolean.equal" 
										  classNameType="java.lang.Boolean" 
										  options="filtroEsActivo" 
										  labelProperty="label" 
										  valueProperty="value"/>
				</td>
				
				</td>
								
				<td>
					<rapidFilters:applyFilters />
				</td>
				
				<td>
					<rapidFilters:clearFilters />
				</td>
	
			</tr>
	</table>
	
	<pragmatags:btnAgregar action="/PersonaAgregar" permissions="PERSONAS-AGREGAR"/>					
	<toolbar:table model="list" requestURI="/PersonaInventarioFiltrar.do" />	
</html:form>