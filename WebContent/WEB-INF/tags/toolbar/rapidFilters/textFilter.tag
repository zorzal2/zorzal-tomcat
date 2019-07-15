<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %>

<%@attribute name="property" required="true" description="propiedad del objeto del modelo sobre el que se aplica el filtro"%>
<%@attribute name="title" required="true" description="titulo del filtros"%>
<%@attribute name="filterType" required="false" description="opciones de busqueda, ToolbarFilter."%>
<%@attribute name="classNameType" required="true" description="Class name del tipo de datos del filtro"%>

<c:set var="filtersLabelsBundle" value="resources.FieldLabels" />

<fmt:bundle basename="${filtersLabelsBundle}">
    <fmt:message key="${title}"/>
</fmt:bundle>

<c:if test="${filterType != null}">
    <c:set var="propertyKey" value="TXT%20${property}%20${filterType}%20${classNameType}" />
</c:if>

<c:if test="${filterType == null}">
    <c:set var="propertyKey" value="TXT%20${property}%20filter.type.string.equal%20${classNameType}" />
</c:if>
<html-el:text property="filter(${propertyKey})" />
<html-el:messages id="error" property="${propertyKey}" >
        &nbsp;<font color="red"><c:out value="${error}"/></font>
</html-el:messages>


