<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<h3>
	<bean:message bundle="titles" key="app.title.desembolso.autorizar"/>
</h3>

<html:form action="/ProyectoDesembolsoAutorizar">
<html:hidden property="id"/>
<html:hidden property="idSeguimientoDeAutorizacion"/>
<table class="formulario">	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.desembolso.montoAutorizado"/>
		</td>		
		<td>
	   		<html:text property="monto"  maxlength="20" size="15%" style="text-align: right"/>	
			<pragmatags:error property="monto" />
		</td>
	</tr>
</table>

<pragmatags:btnOkCancel
	okJavascript="submitForm();"
	cancelJavascript="cerrarPopUp();" />

</html:form>