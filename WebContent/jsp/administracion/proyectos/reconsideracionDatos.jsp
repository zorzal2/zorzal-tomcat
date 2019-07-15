<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 

<html:form action="/FinalizarPosibilidadReconsideracionGuardar">
<table class="formulario">
	<tr>	
		<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.observaciones"/></th>
	</tr>	
	<tr>
		<td align="left" colspan="2">
	   		<html:textarea property="descripcion" rows="4" cols="100" />
	   		<pragmatags:error property="descripcion" />
		</td>
	</tr>	
</table>
</html:form>