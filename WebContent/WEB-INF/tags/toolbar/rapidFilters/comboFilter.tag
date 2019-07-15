<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %>

<%@tag description="DropDownList Rapid Filters" pageEncoding="UTF-8"%>

<%@attribute name="property" required="true" description="propiedad del objeto del modelo sobre el que se aplica el filtro"%>
<%@attribute name="title" required="true" description="titulo del filtros"%>
<%@attribute name="options" required="true" description="opciones del select, tiene que ser una collection de LabelValueBean"%>
<%@attribute name="filterType" required="true" description="opciones de busqueda, ToolbarFilter."%>
<%@attribute name="classNameType" required="true" description="Class name del tipo de datos del filtro"%>

<%@attribute name="valueProperty" required="true" description="value de opcion ToolbarFilter."%>
<%@attribute name="labelProperty" required="true" description="label de opcion, ToolbarFilter."%>

<%@attribute name="expresion" required="false" description="expresion, en caso de que el type sea Expresion.Compleja"%>

<!--  c:set var="filtersLabelsBundle" value="com.pragma.resource.ApplicationResource" / -->
<c:set var="filtersLabelsBundle" value="resources.FieldLabels" />

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
        <html-el:options collection="${options}" property="${valueProperty}" labelProperty="${labelProperty}"/>
</html-el:select>






