<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/displaytag-12" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="/tags/pragma" prefix="pragma"%>

<c:set var="headerKey" value="app.title.bitacoraAlta" />
<c:if test="${!empty idBitacora}" >
	<c:set var="headerKey" value="app.title.bitacoraEditar" />
</c:if>
<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>

<html:form action="/BitacoraGuardar">

	<html:hidden property="idProyecto" />
	<html:hidden property="idBitacora" />	

	<table class="formulario">

		<!--  Resultado -->
		<tr>
			<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.general" /></th>
		</tr>

		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.fechaRegistro" />
			</td>
			<td align="left"><c:out value="${fechaRegistro}" /></td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.fechaAsunto"/>
			</td>
			<td>
		   		<pragmatags:calendar propertyName="fechaAsunto" top="210" left="750" />
				<pragmatags:error property="fechaAsunto" />
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.tema" />
			</td>
			<td align="left"><html:text property="tema"	maxlength="255" /> <pragmatags:error property="tema" /></td>
		</tr>
		<tr>
			<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.observaciones" /></th>
		</tr>

		<tr>
			<td colspan="2" class="opcional" align="left">
				<html:textarea property="observaciones" rows="4" cols="100" />
				<pragmatags:error property="observaciones" />
			</td>
		</tr>
	</table>

	<pragmatags:btnOkCancel okJavascript="submitForm()"	cancelAction="/BitacoraInventario.do" />
</html:form>