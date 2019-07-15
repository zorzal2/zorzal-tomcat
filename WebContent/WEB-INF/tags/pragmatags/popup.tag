<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 

<%@ attribute name="entidad" required="true"%>
<%@ attribute name="infoButton" required="false"%>


<html-el:hidden property="id${entidad}" />

<button onmouseover="javascript:this.style.cursor='hand'" 
		onclick="javascript:AbrirPopup('<c:out value='${entidad}'/>PopUp.do','400','200');">

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

