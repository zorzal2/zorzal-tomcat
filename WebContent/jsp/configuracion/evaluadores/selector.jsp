<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<h3>
	<bean:message bundle="titles" key="app.title.evaluador"/>	
</h3>

<html:form action="/EvaluadorInventario">

	<pragmatags:tabla 
		toolbar="no"
		toolbaroptions=""
		columns="id,cuit,nombre"
		actions="linkSeleccion"
		decorator="com.fontar.web.decorator.configuracion.EvaluadorSelectorWrapper"	
		requestURI="EvaluadorSelectorPopUp.do" 			   
		collection="collection" 
		entity="Evaluador" />

</html:form>
<pragmatags:btnCerrar javascript="window.close();"/>