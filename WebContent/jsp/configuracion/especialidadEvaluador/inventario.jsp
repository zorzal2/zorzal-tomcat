<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<h3>
	<bean:message bundle="titles" key="app.title.especialidadesEvaluador"/>
</h3>
<html:form action="/EspecialidadEvaluadorInventarioFiltrar">	
	<table class="filtros">
			<tr>
				<td width="45%">				
					<rapidFilters:textFilter 
						property="codigo" 
						title="app.label.codigo" 
						filterType="filter.type.string.contains" 
						classNameType="java.lang.String"/>
				</td>
				<td width="45%">				
					<rapidFilters:textFilter 
						property="nombre" 
						title="app.label.nombre" 
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
	
	<pragmatags:btnAgregar action="/EspecialidadEvaluadorAgregar" permissions="ESPECIALIDADESEVALUADOR-AGREGAR"/>
	<toolbar:table model="list" requestURI="/EspecialidadEvaluadorInventarioFiltrar.do" />	
</html:form>