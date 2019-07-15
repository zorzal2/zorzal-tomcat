<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>

<div>
	<h3>
		<bean-el:message bundle="titles" key="app.title.procedimientos.imprimir${sessionScope['FORWARD_TILES']}"/>
	</h3>	


	<jsp:include page="imprimir${sessionScope['FORWARD_TILES']}.jsp" flush="true"/>
	
	<html-el:link action="/${sessionScope['FORWARD_TILES']}">
		<bean:message bundle="labels" key="app.label.cerrar"/>
	</html-el:link>				

</div>

