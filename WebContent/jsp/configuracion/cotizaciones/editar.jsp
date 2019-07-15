<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 

<h3>
	<bean:message bundle="titles" key="app.title.cotizaciones.editar"/>
</h3>

<html:form action="/CotizacionGuardar">
<html:hidden property="id"/>

<jsp:include page="form.jsp" flush="true"/>

</html:form>