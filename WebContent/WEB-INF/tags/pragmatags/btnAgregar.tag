<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<%@ tag body-content="empty" %>

<%@ attribute name="action" required="false"%>
<%@ attribute name="javascript" required="false"%>
<%@ attribute name="permissions" required="false"%>

<SPAN style="height:20px;">
<c:if test="${!empty action}">
	<c:choose> 
		<c:when test="${!empty permissions}">
			<pragma:authorize permissions="${permissions}">
			<!--  aceptar action   -->
				<html-el:link action="${action}">
					<bean:message bundle="labels" key="app.label.agregar"/>
				</html-el:link>				
			</pragma:authorize>		
		</c:when>
		<c:otherwise>
				<html-el:link action="${action}">
					<bean:message bundle="labels" key="app.label.agregar"/>
				</html-el:link>				
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${empty action && !empty javascript}">
	<c:choose> 
		<c:when test="${!empty permissions}">
			<pragma:authorize permissions="${permissions}">
				<!--  aceptar javascript   -->
				<html-el:link href="#" onclick="${javascript}">
					<bean:message bundle="labels" key="app.label.agregar"/>		
				</html-el:link>				
			</pragma:authorize>
		</c:when>
		<c:otherwise>
			<html-el:link href="#" onclick="${javascript}">
				<bean:message bundle="labels" key="app.label.agregar"/>		
			</html-el:link>				
		</c:otherwise>
	</c:choose>
</c:if>
</SPAN>
<br>

<!-- 
<jsp:useBean id="userDetailLink" class="java.util.HashMap"/>
<c:set target="${userDetailLink}" property="action" value="editUser"/>
<c:set target="${userDetailLink}" property="id" value="${user.id}"/>

<html:link action="/manageUsers" name="userDetailLink">
        Edit this user
</html:link>

-->