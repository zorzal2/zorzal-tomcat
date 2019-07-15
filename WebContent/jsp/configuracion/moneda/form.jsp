<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 

<table class="formulario">	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.monedas.tipoMoneda"/>
		</td>		
		<td>
	   		<html:text property="tipoMoneda" maxlength="20" size="60%"/>	
			<pragmatags:error property="tipoMoneda" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.monedas.descripcion"/>
		</td>		
		<td>
	   		<html:text property="descripcion"  maxlength="1000" size="60%"/>	
			<pragmatags:error property="descripcion" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.monedas.codigoEmerix"/>
		</td>		
		<td>
	   		<html:text property="codigoEmerix"  maxlength="20" size="60%"/>	
			<pragmatags:error property="codigoEmerix" />
		</td>
	</tr>
</table>
<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/MonedaInventario"/>