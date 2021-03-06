<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>

<h3>
	<bean:message bundle="titles" key="app.title.criterios"/>
</h3>

<html:form action="/MatrizCriteriosInventarioFiltrar">
	<table class="filtros">
		<tr>
			<td>
				<rapidFilters:textFilter property="nombre" 
										 title="app.label.criterio" 
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
	<pragmatags:btnAgregar action="/MatrizCriteriosAgregar" permissions="CRITERIOS-AGREGAR" />
	<toolbar:table model="list" requestURI="/MatrizCriteriosInventarioFiltrar.do" />	
</html:form>