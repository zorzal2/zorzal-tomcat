<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>

<h1><bean:message bundle="titles" key="app.title.analisisDeGastos.presupuestoSegunInformeDeAvance"/></h1>
<html:form action="/ModificarPresupuestoSegunAvanceGuardar">
<html:hidden property="id"/>	
<table class="formulario">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.monto"/>
		</td>
		<td>
			<html:text property="valor" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<pragmatags:error property="valor" />&nbsp;
		</td>
	</tr>
</table>
</html:form>
<pragmatags:btnOkCancel okJavascript="submitForm()" cancelJavascript="window.close()"/>