<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<h3>
	<bean:message bundle="titles" key="app.title.notificaciones"/>
</h3>
<html:form action="/NotificacionInventarioFiltrar">
	<table class="filtros">
		<tr>
			<td>
				<rapidFilters:textFilter property="proyecto.codigo" 
										 title="app.label.codigoProyecto" 
										 filterType="filter.type.string.contains" 
										 classNameType="java.lang.String"/>
			</td>
			
			<td>
				<rapidFilters:comboFilter property="estado" 
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
	<toolbar:table model="list" requestURI="/NotificacionInventarioFiltrar.do" />	
</html:form>
