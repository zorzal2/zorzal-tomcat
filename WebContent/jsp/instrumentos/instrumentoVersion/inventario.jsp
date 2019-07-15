<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>
<h3>
	<c:if test="${!empty param.instrumento}">
		<c:set var="tipoInstrumento" value="${param.instrumento}"/>		
	</c:if>
	<c:if test="${!empty param.identificador}">
		<c:set var="descInstrumento" value="${param.identificador}"/>		
	</c:if>
	
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="app.title.historial">
			<fmt:param value="${tipoInstrumento}" />
			<fmt:param value="${descInstrumento}" />
		</fmt:message>
	</fmt:bundle>
</h3>

<html:form action="/InstrumentoVersion">
<html:hidden property="idInstrumento" />

<display-el:table name="instrumentoVersionList" class="inventario">
	<display-el:setProperty name="basic.show.header" value="false" /> 

	<display-el:caption>	
		<tr>
		    <th><bean:message bundle="headers" key="app.header.version"/></th>	
		    <th><bean:message bundle="headers" key="app.header.fecha"/></th>	
		    <th><bean:message bundle="headers" key="app.header.codigo"/></th>	
		    <th><bean:message bundle="headers" key="app.header.descripcion"/></th>
        </tr>
	</display-el:caption>	

	<display-el:column media="html" property="version" escapeXml="true"/>
	<display-el:column media="html" property="fecha" escapeXml="true" decorator="com.pragma.toolbar.web.decorator.LongDateWrapper"/>
	<display-el:column media="html" property="codigo" escapeXml="true"/>
	<display-el:column media="html" property="descripcion" escapeXml="false" decorator="com.fontar.web.decorator.miscelaneos.StringMaxLengthWrapper"/>
</display-el:table>
<pragmatags:btnCerrar javascript="window.close();"/>
</html:form>