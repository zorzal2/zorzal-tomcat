<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<script>
	function cancelar() {
		form = document.AdjuntoUploadForm;
		form.action = "SeguimientoTabInventarioAdjuntoAction.do";
	    form.submit();
	}
</script>

<h3>
	<bean:message bundle="titles" key="app.title.adjuntos.agregar"/>
</h3>

<br/>

<html-el:form action="/SeguimientoTabGuardarAdjuntoAction" method="post" enctype="multipart/form-data">

<html-el:hidden property="id"/>	

<table cellpadding="3" cellspacing="0">
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.descripcion"/>
		</td>
		<td>
			<html:text property="description" size="100" maxlength="2000"/>
			<pragmatags:error property="description" />
		</td>
	</tr>
	<tr>
		<td><bean:message bundle="labels" key="app.label.archivo"/></td>
		<td>
			<html:file property="content"/>
			<pragmatags:error property="content" />
		</td>
	</tr>
</table>

<pragmatags:btnOkCancel okJavascript="submitForm()" cancelJavascript="cancelar()"/>

</html-el:form>


