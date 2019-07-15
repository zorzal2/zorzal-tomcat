<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

	<c:set var="headerKey" value="app.title.altaEspecialidadEvaluador" />
	<c:if test="${!empty param.id}" >
		<c:set var="headerKey" value="app.title.edicionEspecialidadEvaluador" />
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

<html:form action="/EspecialidadEvaluadorGuardar">
<html:hidden property="id"/>

<table class="formulario">	
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.codigo"/>
		</td>		
		<td>
	   		<html:text property="codigo" maxlength="20" size="70%"/>	
			<pragmatags:error property="codigo" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.nombre"/>
		</td>		
		<td>
	   		<html:text property="nombre" maxlength="60" size="70%"/>	
			<pragmatags:error property="nombre" />
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
<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/EspecialidadEvaluadorInventario"/>
</html:form>