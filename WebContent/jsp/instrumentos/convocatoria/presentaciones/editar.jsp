<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<h3>
	<!--  bean:message bundle="titles" key="app.title.edicionPresentacionConvocatoria"/-->
	<pragmatags:titulo-edicion clase="PresentacionConvocatoria"/>
</h3>

<html:form action="/PresentacionConvocatoriaGuardar">
<html:hidden property="id"/>

<table class="formulario">
	
	<tr>
		<th colspan="5" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.convocatoria"/>
		</td>
		
		<td>
			<html:select property="idInstrumento">
				<html:options collection="convocatorias" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="idInstrumento" />
		</td>

		<!-- El estado es visible solo en la edicion y no es editable -->		
		<c:if test="${not empty param.id}">
			<td><bean:message bundle="labels" key="app.label.estado"/></td>
			<td>&nbsp;<bean:write name="PresentacionConvocatoriaEditarDynaForm" property="estado"/></td>
		</c:if>
		
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.jurisdiccion"/>
		</td>
		<td>
			<html:select property="idJurisdiccion">
				<html:options collection="jurisdicciones" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="idJurisdiccion" />
		</td>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaIngreso"/>
		</td>
		<td>
	   		<pragmatags:calendar propertyName="fechaIngreso" top="210" left="750" />
			<pragmatags:error property="fechaIngreso" />
		</td>
	</tr>

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.codigo"/>
		</td>
		
		<td>
	   		<html:text property="codigo" maxlength="20" size="20%"/>	
			<pragmatags:error property="codigo" />
		</td>
	</tr>

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.solicitante"/>
		</td>
		<td>
			<html:text property="nombreEntidad" maxlength="50" size="20%"/>
			<pragmatags:error property="nombreEntidad" />
		</td>
	</tr>
</table>

<br>
<br>

<table class="formulario">
	<tr>
		<th class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</th>
	</tr>
	<tr>
		<td>
	   		<html:textarea property="observaciones" rows="4" cols="100" />	
	   		<pragmatags:error property="observaciones" />
   		</td>
	</tr>
</table>
<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/PresentacionConvocatoriaInventario"/>
</html:form>
