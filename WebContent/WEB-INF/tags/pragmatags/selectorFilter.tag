<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 

<%@ attribute name="entidad" required="true"%>
<%@ attribute name="infoButton" required="false"%>
<%@ attribute name="sinTextBox" required="false"%>
<%@ attribute name="editTextBox" required="false"%>
<%@ attribute name="width" required="false"%>
<%@ attribute name="parametersBuilder" required="false"%>
<%@ attribute name="push" required="false"%>
<%@ attribute name="handlerName" required="false"%>

<c:set var="widthColumn" value="40"/>	
<c:if test="${!empty width}">
	<c:set var="widthColumn" value="${width}"/>
</c:if>

<html-el:hidden property="filter(id${entidad})" />

<c:if test="${empty sinTextBox}">
	<html-el:text property="filter(txt${entidad})"  maxlength="50" size="${widthColumn}" readonly="true" />
</c:if>

<c:if test="${!empty editTextBox}">
	<html-el:text property="filter(txt${entidad})"  maxlength="50" size="${widthColumn}"/>
</c:if>

<c:if test="${empty parametersBuilder}">
	<c:choose>
		<c:when test="${empty handlerName}">
			<button id="button${entidad}" onmouseover="javascript:this.style.cursor='hand'" onclick="javascript:AbrirFilterSelectorPopUp('<c:out value='${entidad}'/>SelectorPopUp.do?push=<c:out value='${push}'/>','<c:out value='${entidad}'/>');"/>
		</c:when>
		<c:otherwise>
			<button id="button${entidad}" onmouseover="javascript:this.style.cursor='hand'" onclick="javascript:AbrirFilterSelectorPopUp('<c:out value='${entidad}'/>SelectorPopUp.do?push=<c:out value='${push}'/>','<c:out value='${entidad}'/>' , ${handlerName});"/>
		</c:otherwise>
	</c:choose>
</c:if>

<c:if test="${!empty parametersBuilder}">
	<button id="button${entidad}" onmouseover="javascript:this.style.cursor='hand'" onclick="javascript:AbrirFilterSelectorPopUp('<c:out value='${entidad}'/>SelectorPopUp.do' + <c:out value="${parametersBuilder}()"/> + '&push=<c:out value='${push}'/>','<c:out value='${entidad}'/>');">
</c:if>

	<c:if test="${!empty infoButton}">
		<c:out value="${infoButton}" />
	</c:if>
	<c:if test="${empty infoButton}">
		...
	</c:if>
</button>

<html-el:messages id="error" property="txt${entidad}">
	<font color="red"><bean:write name="error"/></font>
</html-el:messages>