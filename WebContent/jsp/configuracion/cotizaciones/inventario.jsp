<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters"	prefix="rapidFilters"%>

<h3><bean:message bundle="titles" key="app.title.cotizaciones.inventario" /></h3>

<html:form action="/CotizacionInventario">
	<table class="filtros">
		<tr>
			<td width="80%"><rapidFilters:comboFilter property="idMoneda"
				title="app.label.moneda" filterType="filter.type.long.equal"
				classNameType="java.lang.Long" options="monedas"
				labelProperty="label" valueProperty="value" /></td>

			<td><rapidFilters:applyFilters /></td>
			<td><rapidFilters:clearFilters /></td>
		</tr>
	</table>
<br/>
<pragmatags:btnAgregar action="/CotizacionAgregar" permissions="COTIZACIONES-AGREGAR"/>
	<toolbar:table model="list" requestURI="/CotizacionInventario.do" />
</html:form>
