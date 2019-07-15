<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>


<%@ attribute name="clase" required="true"%>

<c:choose>
	<c:when test="${! empty param.id}" >
		<c:set var="operacion" value="edicion" />
	</c:when>
	<c:otherwise>
		<c:set var="operacion" value="alta" />
	</c:otherwise>
</c:choose>

<fmt:bundle basename="resources.Titles">
	<fmt:message key="app.title.${operacion}${clase}" />
</fmt:bundle>