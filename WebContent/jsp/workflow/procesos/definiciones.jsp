<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<h3>Procesos Definidos</h3>

<p><pragmatags:btnAgregar action="/WFProcessDefinitionDeployment" permissions="PROCESOSDEFINIDOS-INVENTARIO"/></p>
<html:form action="WFProcesosDefinidos">

<display-el:table name="processDefinitions" class="inventario" requestURI="WFProcesosDefinidos.do" pagesize="20" decorator="com.fontar.web.decorator.workflow.ProcessDefinitionWrapper">
	<display-el:column  property="id"/>
	<display-el:column  property="name"/>
	<display-el:column  property="version"/>	
<%--	<display-el:column  property="linkNuevo"/>--%>
	<display-el:column  property="linkInstancias"/>	
</display-el:table>

</html:form>


