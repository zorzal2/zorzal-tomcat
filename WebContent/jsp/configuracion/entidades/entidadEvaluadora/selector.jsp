<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<h3>
	<bean:message bundle="titles" key="app.title.entidadEvaluadora"/>	
</h3>

<html:form action="/EntidadEvaluadoraInventario">

	<pragmatags:tabla 
		toolbar="no"
		toolbaroptions=""
		columns="id,cuit,denominacion"
		actions="linkSeleccion"
		decorator="com.fontar.web.decorator.configuracion.EntidadEvaluadoraSelectorWrapper"				   
		requestURI="EntidadBeneficiariaInventario.do" 
		collection="collection" 
		entity="EntidadBeneficiaria" />
</html:form>
<pragmatags:btnCerrar javascript="window.close();"/>


