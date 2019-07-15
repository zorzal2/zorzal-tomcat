<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters"	prefix="rapidFilters"%>
<h3><bean:message bundle="titles" key="app.title.paquetes" /></h3>

<html:form action="/PaqueteInventarioFiltrar">
	<table class="filtros">
		<tr>
			<td><rapidFilters:comboFilter property="codigoTipo"
				title="app.label.tipo" filterType="filter.type.string.equal"
				classNameType="java.lang.String" options="tipos"
				labelProperty="label" valueProperty="value" /></td>
			<td><rapidFilters:comboFilter property="idInstrumento"
				title="app.label.instrumento" filterType="filter.type.number.equal"
				classNameType="java.lang.Long" options="instrumentos"
				labelProperty="label" valueProperty="value" /></td>
			<td><rapidFilters:comboFilter property="codigoEstado"
				title="app.label.estado" filterType="fontar.filter.type.object.sin_anulados"
				classNameType="java.lang.String" options="estados"
				labelProperty="label" valueProperty="value" /></td>
			<td><rapidFilters:applyFilters /></td>
			<td><rapidFilters:clearFilters /></td>
		</tr>
	</table>

	<br>
	<pragmatags:btnDynaLabel action="/PaqueteArmar.do?tipoPaquete=COMISION"
		label="app.label.agregarComision" />
	<pragmatags:btnDynaLabel
		action="/PaqueteArmar.do?tipoPaquete=SECRETARIA"
		label="app.label.agregarSecretaria" />
	<pragmatags:btnDynaLabel
		action="/PaqueteArmar.do?tipoPaquete=DIRECTORIO"
		label="app.label.agregarDirectorio" />
	<br>

	<toolbar:table model="list" requestURI="/PaqueteInventarioFiltrar.do" />
</html:form>
