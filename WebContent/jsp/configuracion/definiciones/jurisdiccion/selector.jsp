<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<h3>
	<bean:message bundle="titles" key="app.title.jurisdicciones"/>	
</h3>
<html:form action="/JurisdiccionInventario">
	<pragmatags:tabla 
		toolbar="no"
		toolbaroptions=""
		columns="codigo,descripcion" 
		actions="linkSeleccion"
		decorator="com.fontar.web.decorator.configuracion.jurisdicciones.JurisdiccionesWrapper"
		requestURI="JurisdiccionSelectorPopUp.do" 
		collection="collection" 
		entity="Jurisdiccion" />		
</html:form>
<pragmatags:btnCerrar javascript="window.close();"/>



