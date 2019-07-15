<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<h3>
	<bean:message bundle="titles" key="app.title.ideasProyecto"/>
</h3>

<html:form action="/IdeaProyectoInventarioFiltrar">	
	<table class="filtros">
			<tr>
				<td>				
					<!-- Identificador -->
					<rapidFilters:textFilter 
						property="codigo"
						title="app.label.codigo" 
						filterType="filter.type.string.contains" 
						classNameType="java.lang.String"/>
				</td>
				<td>
					<rapidFilters:textFilter 
						property="proyectoDatos.entidadBeneficiaria.entidad.denominacion" 
						title="app.label.entidadBeneficiaria" 
						filterType="filter.type.string.contains" 
						classNameType="java.lang.String"/>
				</td>
				<td>
					<rapidFilters:comboFilter 
						property="codigoEstado" 
						title="app.label.estado" 
						filterType="fontar.filter.type.object.sin_anulados" 
						classNameType="java.lang.String" 
						options="estados" 
						labelProperty="label" 
						valueProperty="value"/> 
				</td>
				<!-- ¿tipos proyecto se refiere a si son "Normales ó Pitec" ? -->
				<%-- td>
					<rapidFilters:comboFilter property="codigo" title="app.label.convocatoria" filterType="filter.type.string.contains" classNameType="java.lang.String"/>
				</td --%>
				<td>
					<rapidFilters:applyFilters />
				</td>
				
				<td>
					<rapidFilters:clearFilters />
				</td>
	
			</tr>
	</table>
	<br>
	
	<pragmatags:btnAgregar action="/IdeaProyectoAgregar" permissions="IDEASPROYECTO-AGREGAR"/>		
	<toolbar:table model="list" requestURI="/IdeaProyectoInventarioFiltrar.do" />	
</html:form>