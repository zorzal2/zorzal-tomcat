<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<div>
	<h3>Definicion de Procesos - Deployment</h3>
</div>
<html-el:form action="/WFDeployProcessDefinition" method="post" enctype="multipart/form-data">
<table class="formulario">
	<tr>
		<td class=obligatorio><bean:message bundle="labels" key="app.label.archivo"/></td>
		<td>
			<html:file property="content"/><pragmatags:error property="content" />
			<pragmatags:error property="size" />
		</td>
	</tr>
</table>
<pragmatags:btnOkCancel okJavascript="submitForm();"  cancelAction="/WFProcesosDefinidos.do"/>

</html-el:form>