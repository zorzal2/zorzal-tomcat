<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<c:set var="headerKey" value="app.title.cartera" />
<c:if test="${!empty param.id}" >
	<c:set var="headerKey" value="app.title.cartera" />
</c:if>

<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>

<html:form action="/CarteraGuardar">
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
	   		<html:text property="codigo" maxlength="50" size="60%"/>	
			<pragmatags:error property="codigo" />
		</td>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.denominacion"/>
		</td>		
		<td>
	   		<html:text property="denominacion" maxlength="50" size="60%"/>	
			<pragmatags:error property="denominacion" />
		</td>
	</tr>
	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.emerix"/>
		</td>		
		<td>
	   		<html:text property="emerix" maxlength="50" size="60%"/>	
		</td>
	</tr>
	
	<!-- Estado -->
	<c:if test="${!empty param.id}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.estado"/>
			</td>		
			<td>
				<html:select property="activo">
					<html:options collection="estadosEntidad" property="value" labelProperty="label" />
				</html:select> 
				<pragmatags:error property="activo" />
			</td>
		</tr>
	</c:if>	

</table>

<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/CarteraInventario"/>
</html:form>