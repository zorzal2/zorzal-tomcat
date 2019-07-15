<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<c:set var="headerKey" value="app.title.altaTipoProyecto" />
<c:if test="${!empty param.id}" >
	<c:set var="headerKey" value="app.title.edicionTipoProyecto" />
</c:if>

<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>

<html:form action="/TipoProyectoGuardar">
<html:hidden property="id"/>

<table class="formulario">	
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.nombre"/>
		</td>		
		<td>
	   		<html:text property="nombre" maxlength="500" size="80%"/>	
			<pragmatags:error property="nombre" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.nombreCorto"/>
		</td>		
		<td>
	   		<html:text property="nombreCorto" maxlength="50" size="60%"/>	
			<pragmatags:error property="nombreCorto" />
		</td>
	</tr>
	<c:if test="${!empty param.id}">
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
</table>
<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/TipoProyectoInventario"/>
</html:form>
