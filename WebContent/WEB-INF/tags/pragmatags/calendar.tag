<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%@ attribute name="propertyName" required="true"%>
<%@ attribute name="left" required="true"%>
<%@ attribute name="top" required="true"%>
<%@ attribute name="onChange" required="false"%>

<c:if test="${!empty onChange}">
	<c:set var="onChange" value="${onChange}"/>		
</c:if>

<html-el:text property="${propertyName}" styleId="${propertyName}" maxlength="10" size="10" onchange="${onChange}"/>
<img src="images/calendario/calendar.gif" onClick="javascript:showCalendar('${propertyName}','${onChange}')" id="${propertyName}Button"/>
<div id="${propertyName}CalendarContainer"  style="display:none;position:absolute;left:${left}px;top:${top}px;">  </div>
