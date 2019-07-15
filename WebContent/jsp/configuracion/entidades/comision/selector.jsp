<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<h3>
	<bean:message bundle="titles" key="app.title.comision"/>	
</h3>
<html:form action="/ComisionInventario">
	<pragmatags:tabla 
		toolbar="no"
		toolbaroptions=""
		columns="id,denominacion,fechaAlta,fechaBaja" 
		actions="linkSeleccion"
		decorator="com.fontar.web.decorator.configuracion.comisiones.ComisionesWrapper"
		requestURI="ComisionSelectorPopUp.do" 
		collection="collection" 
		entity="Comision" />		
</html:form>
<pragmatags:btnCerrar javascript="window.close();"/>




