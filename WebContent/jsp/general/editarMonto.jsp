
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>

<h3>
	<bean:message bundle="titles" key="app.title.edicionDeMontos.ingresarMonto"/>
</h3>

<html:form action="/GuardarEdicionMonto">
<html:hidden property="entidad"/>
<html:hidden property="propiedad"/>
<html:hidden property="id"/>
<html:hidden property="handler"/>

<table class="formulario">	
	<tr>
		<td class="obligatorio" width="20%">
			<bean:message bundle="labels" key="app.label.edicionDeMontos.monto"/>
		</td>		
		<td width="20%">
	   		<html:text property="montoNuevo"  maxlength="10" size="20" style="text-align:right"/>	
			<pragmatags:error property="montoNuevo" />
		</td>
	</tr>
</table>
<pragmatags:btnOkCancel
	okJavascript="submitForm();"
	cancelJavascript="cerrarPopUp();" />
</html:form>

