<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body onload="document.forms[0]._esFinanciero.value = document.forms[0].esFinanciero.checked;">
	<html:form action="${submitAction}">
	
		<html:hidden property="id" />
		<html:hidden property="idProyecto" />
	
		<table class="formulario">
			<tr rowspan="2">
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.tipo"/>
				</td>
				<td>
					<html:checkbox property="esFinanciero" disabled="${tieneRendiciones}" onclick="document.forms[0]._esFinanciero.value = this.checked;"/>
					
					<html:hidden property="_esFinanciero" />
					
					<bean:message bundle="labels" key="app.label.contable"/>
				</td>
				<td colspan="3">
					<pragmatags:error property="_esFinanciero" />
				</td>			
			</tr>
			<tr>
				<td></td>	
				<td colspan="5">
					<html:checkbox property="esTecnico" />
					<bean:message bundle="labels" key="app.label.tecnico"/>
				</td>
			</tr>
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.descripcion"/>
				</td>
				<td colspan="4">
					<html:text property="descripcion" size="50" maxlength="500"/>
					<pragmatags:error property="descripcion" />
				</td>
			</tr>
	
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.observaciones"/>
				</td>
				<td colspan="4">
					<html:textarea property="observaciones" rows="3" cols="100"/>
					<pragmatags:error property="observaciones" />
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<pragmatags:btnDynaLabel javascript="submitForm();" label="app.label.guardar" />
					<pragmatags:btnCancelar action="${cancelAction}" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
