<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 

<h3>
	<bean:message bundle="titles" key="app.title.ideaProyectoPitec"/>
</h3>

<html:form action="/IdeaProyectoPitecInventarioFiltrar">
	<table class="filtros">
		<tr>
			<td>
				<rapidFilters:textFilter property="codigo" 
										 title="app.label.codigo" 
										 filterType="filter.type.string.contains" 
										 classNameType="java.lang.String"/>
			</td>
			
			<td>
				<rapidFilters:comboFilter property="idInstrumento" 
										  title="app.label.instrumento" 
										  filterType="filter.type.number.equal" 
										  classNameType="java.lang.Long" 
										  options="instrumentos" 
										  labelProperty="label" 
										  valueProperty="value"/>
			</td>

			<td>                                  
				<rapidFilters:textFilter property="proyectoDatos.entidadBeneficiaria.entidad.denominacion" 
										 title="app.label.nombreEntidad" 
										 filterType="filter.type.string.contains" 
										 classNameType="java.lang.String"/>
			</td>

			<td>
				<rapidFilters:comboFilter property="codigoEstado" 
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
	<br>

	<toolbar:table model="list" requestURI="/IdeaProyectoPitecInventarioFiltrar.do" />	
</html:form>