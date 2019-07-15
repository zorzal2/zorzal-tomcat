<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body onload="checkVariableClose('windowClose');">
<br>
<html:form action="/PersonaEvaluadorGuardar" >

<!-- input para seleccion   -->
<input type="hidden" name="selectionEvent" id="selectionEvent"/>
<c:if test="${!empty id}">
	<html:hidden property="id" />
</c:if>

<html:hidden property="windowClose" />
<table class="formulario">	
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.general"/>
		</th>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.nombre"/>
		</td>
		<td><c:out value="${nombre}"/></td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaIngreso"/>
		</td>		
		<td colspan="5">
	   		<pragmatags:calendar propertyName="fechaIngreso" top="0" left="0"/>
			<pragmatags:error property="fechaIngreso"/>
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.entDesemp"/>
		</td>
		<td><pragmatags:selectorInventario width="60" entidad="EntidadEvaluadora"/></td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.tituloPosgrado"/>
		</td>		
		<td colspan="5">
	   		<html:text property="tituloPosgrado" maxlength="50" size="60%"/>	
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.primaria"/>
		</td>		
		<td colspan="5">
			<pragmatags:selectorInventario width="60" entidad="Primaria" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.secundaria"/>
		</td>		
		<td colspan="5">
			<pragmatags:selectorInventario width="60" entidad="Secundaria" />
		</td>
	</tr>
</table>

<pragmatags:btnOkCancel okJavascript="submitForm()" cancelJavascript="cerrarPopUp()"/>
</html:form>
</body>