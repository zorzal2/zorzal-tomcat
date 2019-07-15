<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ tag body-content="empty" %>

<html-el:link href="<%=com.pragma.web.NavigationManager.getPreviousURIHref(request,com.pragma.web.action.BaseMappingDispatchAction.NAVIGATION_OVERRIDE_FORWARD)%>">
	<bean:message bundle="labels" key="app.label.cerrar"/>
</html-el:link>				
