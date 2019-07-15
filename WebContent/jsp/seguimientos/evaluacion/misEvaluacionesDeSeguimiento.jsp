<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters"
	prefix="rapidFilters"%>

<h3><bean:message bundle="titles" key="app.title.misEvaluacionesDeSeguimiento" /></h3>

<html:form action="/MisEvaluacionesDeSeguimiento">
	<table class="filtros">
		<tr>
			<td><rapidFilters:textFilter property="proyecto.codigo"
				title="app.label.nroProyecto" filterType="filter.type.string.contains"
				classNameType="java.lang.String" /></td>
			<td><rapidFilters:comboFilter property="proyecto.instrumento.id"
				title="app.label.instrumento" filterType="filter.type.number.equal"
				classNameType="java.lang.Long" options="instrumentos"
				labelProperty="label" valueProperty="value" /></td>
			<td><rapidFilters:textFilter
				property="proyecto.proyectoDatos.entidadBeneficiaria.entidad.denominacion"
				title="app.label.entidadBeneficiaria" filterType="filter.type.string.contains"
				classNameType="java.lang.String" /></td>
			<td><rapidFilters:comboFilter property="estado"
				title="app.label.estado" filterType="fontar.filter.type.object.sin_anulados"
				classNameType="java.lang.String" options="estados"
				labelProperty="label" valueProperty="value" /></td>
			<td><rapidFilters:applyFilters /></td>
			<td><rapidFilters:clearFilters /></td>
		</tr>
	</table>

	<br>
	<toolbar:table model="list"
		requestURI="/MisEvaluacionesDeSeguimiento.do" />
</html:form>
