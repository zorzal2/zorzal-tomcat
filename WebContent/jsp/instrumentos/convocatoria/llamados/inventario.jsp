<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<h3>
	<bean:message bundle="titles" key="app.title.llamadosConvocatoria"/>
</h3>	
<html:form action="/LlamadoConvocatoriaInventarioFiltrar">	
	
	<table class="filtros">
		<tr>
			<td>
				<rapidFilters:textFilter property="identificador" 
										 title="app.label.identificador" 
										 filterType="filter.type.string.contains" 
										 classNameType="java.lang.String"/>
			</td>
			
			<td>
										 
				<rapidFilters:comboFilter property="codigoEstado" 
										  title="app.label.estado" 
										  options="estados" 
										  filterType="filter.type.string.equal" 
										  classNameType="java.lang.String" 
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
	
	<pragmatags:btnAgregar action="/LlamadoConvocatoriaAgregar" permissions="LLAMADOSCONVOCATORIA-AGREGAR"/>

	<toolbar:table model="list" requestURI="/LlamadoConvocatoriaInventarioFiltrar.do" />	
	
</html:form>
