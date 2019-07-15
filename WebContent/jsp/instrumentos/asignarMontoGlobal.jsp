<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<title>
	<bean:message bundle="titles" key="app.title.asignacionMonto"/>
</title>

	<html:form action="/VentanillaPermanenteGuardarMontoGlobal">
		<table class="formulario">	
			<tr>
				<th colspan="6" class="titulo">
					<bean:message bundle="headers" key="app.header.asignacionMonto" />
				</th>
			</tr>
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.monto"/>
				</td>		
				<td colspan="5">
			   		<html:text property="monto" maxlength="50" size="20%" style="text-align:right"/>	
					<pragmatags:error property="monto" />
				</td>
			</tr>			
		</table>
		
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="window.close()"/>	
	</html:form>