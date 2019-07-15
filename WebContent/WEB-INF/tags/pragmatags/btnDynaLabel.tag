<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ tag body-content="empty" %>

<%@ attribute name="action" required="false"%>
<%@ attribute name="label" required="true"%>
<%@ attribute name="javascript" required="false"%>

<c:choose> 
	<c:when test="${!empty action}"> 
		<html-el:link styleClass="textButton" action="${action}">
			<bean-el:message bundle="labels" key="${label}"/>
		</html-el:link>	
	</c:when> 
	<c:when test="${empty action && !empty javascript}"> 
		<html-el:link styleClass="textButton" href="#" onclick="${javascript}">
			<bean-el:message bundle="labels" key="${label}"/>	
		</html-el:link>		
	</c:when> 
    <c:otherwise> 
		<html-el:link styleClass="textButton" href="#">
			<bean-el:message bundle="labels" key="${label}"/>	
		</html-el:link>	
	</c:otherwise> 
</c:choose>