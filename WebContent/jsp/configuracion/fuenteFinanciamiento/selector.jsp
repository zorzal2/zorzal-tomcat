<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<h3>
	<bean:message bundle="titles" key="app.title.fuenteFinanciamiento"/>	
</h3>

<html:form action="/FuenteFinanciamientoInventario">

	<pragmatags:tabla 
		toolbar="no"
		toolbaroptions=""
		columns="id,denominacion,identificador"
		actions="linkSeleccion"
		decorator="com.fontar.web.decorator.configuracion.fuenteFinanciamiento.FuenteFinanciamientoWrapper"				   
		requestURI="FuenteFinanciamientoSelectorPopUp.do" 
		collection="collection" 
		entity="FuenteFinanciamiento" />

</html:form>

<pragmatags:btnCerrar javascript="window.close();"/>