<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 

<h3>
	<bean:message bundle="titles" key="app.title.cotizaciones.agregar"/>
</h3>

<html:form action="/CotizacionCrear">

<jsp:include page="form.jsp" flush="true"/>

</html:form>