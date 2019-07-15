<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<html:form action="/FinalizarProyectoGuardar">
<html:hidden property="idProyecto"/>

<table class="formulario">		
	<tr>	
		<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.descripcionMotivo"/></th>
	</tr>	
	<tr>
		<td colspan="2"><html:textarea property="observacion" rows="4" cols="80" />
		<pragmatags:error property="observacion" />
		</td>
	</tr>
</table>

</html:form>