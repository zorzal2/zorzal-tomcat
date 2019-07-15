<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<br>
<h3>Detalle Instancia Proceso</h3>
<html:form action="WFDetalleInstanciaProceso">
<pragma:jbpmprocessimageToken token="${processInstance.rootToken.id}"/>
</html:form>

