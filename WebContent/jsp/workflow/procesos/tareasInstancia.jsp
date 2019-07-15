<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
[
<c:forEach items="${tasks}" var="task">{
    "value":"WFCargarTarea.do?idTaskInstance=<c:out value="${task.id}"/>",
	"desc":"<c:out value="${task.description}" />"
},</c:forEach>
]


