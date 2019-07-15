<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body>
<html:form action="/AnularPaqueteGuardar">

	<html:hidden property="idPaquete" />	
	
	<table class="formulario">
		<!--  Resultado -->
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="headers" key="app.header.observaciones" />
			</th>
		</tr>
		<tr>
			<td class="opcional" align="left">
		   		<html:textarea property="observaciones" rows="4" cols="100" />
				<pragmatags:error property="observaciones" />
			</td>
		</tr>		
	</table>
	
</html:form>
</body>
