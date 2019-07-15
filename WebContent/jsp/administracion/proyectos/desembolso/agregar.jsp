<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<h3>
	<bean:message bundle="titles" key="app.title.desembolso.agregar"/>
</h3>

<html:form action="/ProyectoDesembolsoCrear">

	<jsp:include page="form.jsp" flush="true"/>
	<br>
	<pragmatags:btnOkCancel okJavascript="submitForm();"
		cancelAction="/ProyectoDesembolsoVolver" />
</html:form>