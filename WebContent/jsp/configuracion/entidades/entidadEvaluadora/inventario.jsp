<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<h3>
	<bean:message bundle="titles" key="app.title.entidadesEvaluadoras"/>	
</h3>
<html:form action="/EntidadEvaluadoraConfiguracionInventarioFiltrar">	
	<table class="filtros">
			<tr>
				<td>
					<rapidFilters:textFilter 
						property="entidad.cuit" 
						title="app.label.cuit" 
						filterType="filter.type.string.contains" 
						classNameType="java.lang.String"/>
				</td>
				<td>
					<rapidFilters:textFilter 
						property="entidad.denominacion" 
						title="app.label.denominacion" 
						filterType="filter.type.string.contains" 
						classNameType="java.lang.String"/>
				</td>
				<td>
					<rapidFilters:comboFilter property="entidad.activo" 
											  title="app.label.activo" 
											  filterType="filter.type.boolean.equal" 
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
	<toolbar:table model="list" requestURI="/EntidadEvaluadoraConfiguracionInventarioFiltrar.do" />	
</html:form>