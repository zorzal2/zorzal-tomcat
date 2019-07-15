<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 

<h3>
	<bean:message bundle="titles" key="app.title.moneda.agregar"/>
</h3>

<html:form action="/MonedaCrear">

<jsp:include page="form.jsp" flush="true"/>

</html:form>