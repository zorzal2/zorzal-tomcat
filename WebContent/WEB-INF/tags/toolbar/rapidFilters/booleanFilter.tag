<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %>

<%@tag description="DropDownList Rapid Filters" pageEncoding="UTF-8"%>

<%@attribute name="property" required="true" description="propiedad del objeto del modelo sobre el que se aplica el filtro"%>
<%@attribute name="title" required="true" description="titulo del filtros"%>

<!--  c:set var="filtersLabelsBundle" value="com.pragma.resource.ApplicationResource" / -->
<c:set var="filtersLabelsBundle" value="resources.FieldLabels" />

<c:set var="filterType" value="equals" />
<c:set var="classNameType" value="java.lang.Boolean" />

<fmt:bundle basename="${filtersLabelsBundle}">
    <fmt:message key="${title}"/>
</fmt:bundle>

<c:if test="${filterType != null}">
    <c:set var="propertyKey" value="CMB%20${property}%20${filterType}%20${classNameType}" />
</c:if>

<c:if test="${filterType == null}">
    <c:set var="propertyKey" value="CMB%20${property}%20filter.type.string.equal" />
</c:if>

<html-el:select property="filter(${propertyKey})">        
		<html-el:option value=""></html-el:option>
		<html-el:option value="true">Si</html-el:option>
		<html-el:option value="false">No</html-el:option>
</html-el:select>






