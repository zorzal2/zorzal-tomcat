<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<h3>
	Tareas Pendientes
</h3>
<html:form action="WFTareas">
	<pragmatags:tabla 
		toolbar="yes"
		toolbaroptions="list,selcolumns,filter,sort,page"
		columns="id,name,description,actorId,create,start,end,dueDate"
		actions="linkCargar,linkTerminar,linkSignal,linkLiberar"
		decorator="com.fontar.web.decorator.workflow.TaskInstanceWrapper"
		requestURI="WFTareas.do" 
		collection="taskInstances"/>
<pragma:showErrors></pragma:showErrors>
</html:form>