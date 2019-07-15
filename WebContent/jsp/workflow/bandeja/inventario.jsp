<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<h3>
	<bean:message bundle="titles" key="app.title.bandejaDeEntrada"/>
</h3>

<html:form action="/WFBandejaDeEntrada.do">
	<table class="filtros">
		<tr>
			
			<td>
				<rapidFilters:comboFilter property="token.processInstance.processDefinition.name" 
										  title="app.label.proceso" 
										  filterType="filter.type.string.equal" 
										  classNameType="java.lang.String" 
										  options="procesos" 
										  labelProperty="label" 
										  valueProperty="value"/>			
			</td>
			
			<td>
				<rapidFilters:comboFilter property="task.name||o.token.processInstance.processDefinition.name" 
										  title="app.label.nombreTarea" 
										  filterType="filter.type.string.equal" 
										  classNameType="java.lang.String" 
										  options="tareas" 
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
	<toolbar:table model="list" requestURI="/WFBandejaDeEntrada.do" />	
</html:form>

