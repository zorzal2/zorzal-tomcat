<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 

<%@ attribute name="entidad" required="true"%>
<%@ attribute name="infoButton" required="false"%>
<%@ attribute name="sinTextBox" required="false"%>
<%@ attribute name="clearButton" required="false"%>
<%@ attribute name="width" required="false"%>
<%@ attribute name="parametersBuilder" required="false"%>
<%@ attribute name="push" required="false"%>
<%@ attribute name="eventName" required="false"%>

<c:set var="widthColumn" value="40"/>	
<c:if test="${!empty width}">
	<c:set var="widthColumn" value="${width}"/>
</c:if>

<c:set var="selectionEventName" value="selectionEvent"/>	
<c:if test="${!empty eventName}">
	<c:set var="selectionEventName" value="${eventName}"/>
</c:if>
<%  jspContext.setAttribute("__entidad__", com.pragma.util.StringUtil.firstLetterUpperCase(jspContext.getAttribute("entidad").toString())); %>
<html-el:hidden property="id${__entidad__}" styleId="id_id${__entidad__}" />
<table cellSpacing=0 cellPadding=0 border=0 style="border-collapse: collapse">
	<tr valign="middle">
		<c:if test="${empty sinTextBox}">
			<td style="border: 0px; padding: 0px; margin: 0px">
				<html-el:text property="txt${__entidad__}" maxlength="50"
					size="${widthColumn}" readonly="true" styleId="id_txt${__entidad__}" />
			</td>
		</c:if>
		<td style="border: 0px; padding: 0px; margin: 0px">
			<c:if test="${empty parametersBuilder}">
				<button id="button${__entidad__}" onmouseover="javascript:this.style.cursor='hand'" onclick="javascript:AbrirSelectorPopUp('<c:out value='${__entidad__}'/>SelectorPopUp.do?push=<c:out value='${push}'/>','<c:out value='${__entidad__}'/>', null, '<c:out value='${selectionEventName}'/>');"/>
			</c:if>

			<c:if test="${!empty parametersBuilder}">
				<button id="button${__entidad__}" onmouseover="javascript:this.style.cursor='hand'" onclick="javascript:AbrirSelectorPopUp('<c:out value='${entidad}'/>SelectorPopUp.do' + <c:out value="${parametersBuilder}()"/> + '&push=<c:out value='${push}'/>','<c:out value='${__entidad__}'/>');">
			</c:if>
				<c:if test="${!empty infoButton}"><c:out value="${infoButton}" /></c:if>
				<c:if test="${empty infoButton}">...</c:if>
			</button>
		</td>
		<c:if test="${!empty clearButton}">
			<td style="border: 0px; padding: 0px; margin: 0px">
				<html-el:img page="/images/limpiar.gif"
					style="margin-top: 2px; cursor: pointer"
					onclick="javascript: document.getElementById('id_id${__entidad__}').value='';document.getElementById('id_txt${__entidad__}').value=''" />
			</td>
		</c:if>
		<td style="border: 0px; padding: 0px; margin: 0px">
			<html-el:messages id="error" property="txt${__entidad__}">
				<span class="error"><bean:write name="error"/></span>
			</html-el:messages>
		</td>
	</tr>
</table>
