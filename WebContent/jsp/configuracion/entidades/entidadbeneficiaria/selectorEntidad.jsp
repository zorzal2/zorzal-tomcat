<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<h3>
	<bean:message bundle="titles" key="app.title.entidadesBeneficiarias"/>	
</h3>
<html:form action="/EntidadBeneficiariaSelInventario">

	<pragmatags:tabla 
		toolbar="no"
		toolbaroptions=""
		columns="id,denominacion,cuit"
		actions="linkSeleccionEntidades"
		decorator="com.fontar.web.decorator.configuracion.entidadesbeneficiarias.EntidadesBeneficiariasWrapper"				   
		requestURI="EntidadBeneficiariaSelSelectorPopUp.do" 
		collection="collection" 
		entity="EntidadBeneficiaria" />

</html:form>
<pragmatags:btnCerrar javascript="window.close();"/>