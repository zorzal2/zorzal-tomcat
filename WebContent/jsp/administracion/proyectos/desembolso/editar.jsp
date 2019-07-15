<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<h3>
	<bean:message bundle="titles" key="app.title.desembolso.editar"/>
</h3>

<html:form action="/ProyectoDesembolsoGuardar">
<html:hidden property="id"/>

	<jsp:include page="form.jsp" flush="true"/>
	<br>
	<pragmatags:btnOkCancel okJavascript="submitForm();"
		cancelJavascript="cerrarPopUp();" />
</html:form>