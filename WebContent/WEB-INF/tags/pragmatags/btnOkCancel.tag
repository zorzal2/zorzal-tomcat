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

<%@ attribute name="okAction" required="false"%>
<%@ attribute name="cancelAction" required="false"%>

<%@ attribute name="cancelJavascript" required="false"%>
<%@ attribute name="okJavascript" required="false"%>


<table class="bottom-buttons">
	<tr align="left">
		<td>
			<!-- FF / si tengo action no me interesa el valor   -->
			<c:if test="${!empty okAction}">
				<!--  aceptar action   -->
				<html-el:link action="${okAction}">
					<%--
						<html:image page="/images/aceptar.gif" bundle="labels" altKey="app.label.guardar"/>
					 						<html:image page="/images/aceptar.gif" bundle="labels" altKey="app.label.guardar" on/>
					 --%>
					<bean:message bundle="labels" key="app.label.aceptar"/>
				</html-el:link>				
			</c:if>

			<c:if test="${empty okAction && !empty okJavascript}">
				<!--  aceptar javascript   -->
				<html-el:link href="#" onclick="${okJavascript}">
					<%--
						<html:image page="/images/aceptar.gif" bundle="labels" altKey="app.label.guardar"/>
					--%>
					<bean:message bundle="labels" key="app.label.aceptar"/>
				</html-el:link>				
			</c:if>
		</td>

		<td>
			<!-- FF / si tengo action no me interesa el valor   -->
			<c:if test="${!empty cancelAction}">
				<!--  cancelar action   -->			
				<html-el:link action="${cancelAction}">
					<%-- <html:image page="/images/cancelar.gif" bundle="labels" altKey="app.label.cancelar"/> --%>
					<bean:message bundle="labels" key="app.label.cancelar"/>					
				</html-el:link>				
			</c:if>

			<c:if test="${empty cancelAction && !empty cancelJavascript}">
				<!--  cancelar javascript -->						
				<html-el:link href="#" onclick="${cancelJavascript}">
					<%-- <html:image page="/images/cancelar.gif" bundle="labels" altKey="app.label.cancelar"/> --%>
					<bean:message bundle="labels" key="app.label.cancelar"/>										
				</html-el:link>				
			</c:if>
			
			
			<!--  Busco en el Navigation Manager   -->
			<c:if test="${empty cancelAction && empty cancelJavascript}">
			<html-el:link href="<%=com.pragma.web.NavigationManager.getPreviousURIHref(request)%>">
				<bean:message bundle="labels" key="app.label.cancelar"/>		
			</html-el:link>				
			</c:if>
		</td>
	</tr>
</table>




