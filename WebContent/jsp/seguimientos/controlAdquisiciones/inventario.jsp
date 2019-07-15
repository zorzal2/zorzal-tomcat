<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>

<h3>
	<bean:message bundle="titles" key="app.title.procedimientos.procedimientos"/>
</h3>

<html:form action="/ProcedimientoInventarioFiltrar">
	<table class="filtros">
		<tr>
			<td><rapidFilters:textFilter property="proyecto.codigo"
					title="app.label.nroProyecto" filterType="filter.type.string.contains"
					classNameType="java.lang.String"/></td>
			<td><rapidFilters:textFilter property="codigo"
					title="app.label.nroProcedimiento" filterType="filter.type.string.contains"
					classNameType="java.lang.String"/></td>
			<td><rapidFilters:comboFilter property="tipoAdquisicion.id"
					title="app.label.tipoAdquisicion" filterType="filter.type.string.equal"
					classNameType="java.lang.Long" options="tiposAdquisicion"
					labelProperty="label" valueProperty="value"/></td>
			<td><rapidFilters:comboFilter property="estado"
					title="app.label.estado" filterType="filter.type.string.equal"
					classNameType="java.lang.String" options="estados"
					labelProperty="label" valueProperty="value"/></td>

			<td>
				<rapidFilters:applyFilters />
			</td>
			<td>
				<rapidFilters:clearFilters />
			</td>
		</tr>
	</table>
	
	<br>
	
	<toolbar:table model="list" requestURI="/ProcedimientoInventarioFiltrar.do" />	
</html:form>





