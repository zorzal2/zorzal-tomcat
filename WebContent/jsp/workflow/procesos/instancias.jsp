<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<h3>Procesos Instanciados</h3>
<html:form action="WFInstanciasProceso">
<display-el:table name="instancias" class="inventario" requestURI="WFInstanciasProceso.do" pagesize="20" decorator="com.fontar.web.decorator.workflow.ProcessInstanceWrapper">
	<display-el:column  property="id"/>
	<display-el:column  property="start"/>
	<display-el:column  property="end"/>	
	<display-el:column  property="linkDetalle"/>
</display-el:table>
</html:form>