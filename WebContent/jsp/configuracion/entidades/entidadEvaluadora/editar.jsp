<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

	<c:set var="headerKey" value="app.title.altaEntidadEvaluadora" />
	<c:if test="${!empty param.id}" >
		<c:set var="headerKey" value="app.title.edicionEntidadEvaluadora" />
	</c:if>

	<title>
		<fmt:bundle basename="resources.Titles">
			<fmt:message key="${headerKey}" />
		</fmt:bundle>
	</title>
<br>
<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>

<html:form action="/EntidadEvaluadoraGuardar">
<html:hidden property="id"/>
<table class="formulario">	
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<c:if test="${!empty param.id}">
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.identificador"/>
			</td>		
			<td colspan="5">
				<c:out value="${param.id}" />
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.denominacion"/>
		</td>		
		<td colspan="5">
			<c:out value="${param.denominacion}" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.nroCBU"/>
		</td>		
		<td colspan="5">
	   		<html:text property="nroCBU" maxlength="22" size="60%"/>	
			<pragmatags:error property="nroCBU" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.nroCuenta"/>
		</td>		
		<td colspan="5">
	   		<html:text property="nroCuenta" maxlength="50" size="60%"/>	
			<pragmatags:error property="nroCuenta" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.nombreBeneficiario"/>
		</td>		
		<td colspan="5">
	   		<html:text property="nombreBeneficiario" maxlength="50" size="60%"/>	
			<pragmatags:error property="nombreBeneficiario"/>
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.entidadBancaria"/>
		</td>		
		<td colspan="5">
			<html:select property="idEntidadBancaria">
					<html:options collection="entidadesBancarias" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="idEntidadBancaria"/>
		</td>
	</tr>
</table>
<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/EntidadInventario"/>
</html:form>

