<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>

<h3>
	<bean:message bundle="titles" key="app.title.proyectos"/>
</h3>
<html:form action="/ProyectoInventarioFiltrar">
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
<%--				<rapidFilters:checkboxFilter property="proyectoDatos.entidadBeneficiaria.entidad.denominacion" 
										 title="app.label.nombreEntidad" 
										 filterType="filter.type.string.equals"
										 criteria="anulado" 
										 classNameType="java.lang.String"/>
			</td>
--%>
			<td>
				<rapidFilters:comboFilter property="codigoEstado" 
										  title="app.label.estado" 
										  filterType="fontar.filter.type.object.sin_anulados" 
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
	<pragma:authorize permissions="PROYECTOS-CARGAR-DESDE-EXCEL">
		<html:link action="ProyectosExcelIngresar.do">
			<bean:message bundle="titles" key="app.title.cargaDeProyectosExcel"/>	
		</html:link>
		<br/>
	</pragma:authorize>
	<toolbar:table model="list" requestURI="/ProyectoInventarioFiltrar.do" />	
</html:form>
