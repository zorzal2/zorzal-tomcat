<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<c:set var="headerKey" value="app.title.altaFuenteFinanciamiento" />
<c:if test="${!empty param.id}" >
	<c:set var="headerKey" value="app.title.edicionFuenteFinanciamiento" />
</c:if>
<title>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}" />
	</fmt:bundle>
</title>

<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>

<html:form action="/FuenteFinanciamientoGuardar">
<html:hidden property="id"/>

<table class="formulario">	
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.identificador"/>
		</td>		
		<td>
	   		<html:text property="identificador" maxlength="50" size="70%"/>	
			<pragmatags:error property="identificador" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.denominacion"/>
		</td>		
		<td>
	   		<html:text property="denominacion" maxlength="100" size="70%"/>	
			<pragmatags:error property="denominacion" />
		</td>
	</tr>
	<c:if test="${!empty param.id}">
		<c:if test="${param.id != '0'}">
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.estado"/>
				</td>		
				<td class="opcional">
					<html:select property="activo">
						<html:options collection="estadosEntidad" property="value" labelProperty="label" />
					</html:select> 
					<pragmatags:error property="activo" />
				</td>
			</tr>
		</c:if>
	</c:if>	
</table>

<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/FuenteFinanciamientoInventario"/>
</html:form>