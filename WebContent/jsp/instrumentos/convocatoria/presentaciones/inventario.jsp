<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<h3>
	<bean:message bundle="titles" key="app.title.presentacionesConvocatoria"/>
</h3>	
<html:form action="/PresentacionConvocatoriaInventarioFiltrar">	
	
	<table class="filtros">
		<tr>
			<td>
				<!-- convocatoria -->
				<rapidFilters:comboFilter property="idInstrumento" 
										  title="app.label.convocatoria" 
										  filterType="filter.type.number.equal" 
										  classNameType="java.lang.Long" 
										  options="convocatorias" 
										  labelProperty="label" 
										  valueProperty="value"/>
			</td>
			<td>
				<!-- nroPresentacion -->
				<rapidFilters:textFilter property="codigo" title="app.label.numeroPresentacion" filterType="filter.type.string.contains" classNameType="java.lang.String"/>
			</td>
			<td>
				<rapidFilters:textFilter property="nombreEntidad" title="app.label.solicitante" filterType="filter.type.string.contains" classNameType="java.lang.String"/>
			</td>			
			<td>
				<rapidFilters:comboFilter property="estado" 
										  title="app.label.estado" 
										  filterType="filter.type.string.equal" 
										  classNameType="java.lang.String" 
										  options="estados" 
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
	<%-- TIENEN QUE IR DENTRO DEL FORM! --%>
	<pragmatags:btnAgregar action="/PresentacionConvocatoriaAgregar" permissions="PRESENTACIONCONVOCATORIA-AGREGAR"/>
	<toolbar:table model="list" requestURI="/PresentacionConvocatoriaInventarioFiltrar.do" />	
</html:form>