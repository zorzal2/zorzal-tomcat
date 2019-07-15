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

<c:set var="widthColumn" value="20"/>	
<c:if test="${!empty width}">
	<c:set var="widthColumn" value="${width}"/>
</c:if>

<html-el:hidden property="id${entidad}" styleId="id_id${entidad}" />
<table cellpadding="0" cellspacing="0" border=0 style="border-collapse: collapse">
	<tr valign="middle">
		<c:if test="${empty sinTextBox}">
			<td style="border: 0px; padding: 0px; margin: 0px">
				<html-el:text property="txt${entidad}" maxlength="50"
					size="${widthColumn}" readonly="true" styleId="id_txt${entidad}" />
			</td>
		</c:if>
		<td style="border: 0px; padding: 0px; margin: 0px">
			<c:if test="${empty parametersBuilder}">
				<button id="button${entidad}" onmouseover="javascript:this.style.cursor='hand'" onclick="javascript:AbrirSelectorPopUp('<c:out value='${entidad}'/>SelectorPopUp.do?push=<c:out value='${push}'/>','<c:out value='${entidad}'/>');"/>
			</c:if>

			<c:if test="${!empty parametersBuilder}">
			    <button id="button${entidad}" onmouseover="javascript:this.style.cursor='hand'" onclick="javascript:AbrirSelectorPopUp('<c:out value='${entidad}'/>SelectorPopUp.do' + <c:out value="${parametersBuilder}()"/> + '&push=<c:out value='${push}'/>','<c:out value='${entidad}'/>');">
			</c:if>
				<c:if test="${!empty infoButton}"><c:out value="${infoButton}" /></c:if>
				<c:if test="${empty infoButton}">...</c:if>
			</button>
		</td>
		<c:if test="${!empty clearButton}">
			<td style="border: 0px; padding: 0px; margin: 0px">
				<html-el:img page="/images/limpiar.gif"
					style="margin-top: 2px; cursor: pointer"
					onclick="javascript: document.getElementById('id_id${entidad}').value='';document.getElementById('id_txt${entidad}').value=''" />
			</td>
		</c:if>
		<td style="border: 0px; padding: 0px; margin: 0px">
			<html-el:messages id="error" property="txt${entidad}">
				<font color="red"><bean:write name="error"/></font>
			</html-el:messages>
		</td>
	</tr>
</table>

