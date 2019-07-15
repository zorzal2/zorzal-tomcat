<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<html:form action="/PasarProximaEtapaGuardar">
<html:hidden property="idProyecto"/>
<table class="formulario">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.resultado"/>
		</td>
		<td colspan="2">
			<span><bean:write name="proyecto" property="recomendacion.descripcion" /></span>
		</td>
	</tr>		

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fundamentacion"/>
		</td>

		<td align="left" colspan="2">
	   		<html:textarea property="fundamentacion" rows="4" cols="100" />
		<pragmatags:error property="fundamentacion" />
		</td>
	</tr>
</table>
</html:form>