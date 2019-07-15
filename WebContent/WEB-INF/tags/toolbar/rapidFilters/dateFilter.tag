<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 


<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%@attribute name="property" required="true" description="propiedad del objeto del modelo sobre el que se aplica el filtro"%>

<%@attribute name="title" required="true" description="titulo del filtros"%>
<%@attribute name="filterType" required="true" description="opciones de busqueda, ToolbarFilter."%>


<c:set var="filtersLabelsBundle" value="resources.FiltersLabels" />
(- Label del filtro -)
<%-- Descomentar cuando esté disponible el archivo de recursos --%>
<%-- fmt:message bundle="filtersLabelsBundle" key="${title}" / --%>
-- - COMPONENTE CALENDARIO - --
