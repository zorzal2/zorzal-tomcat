<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<h3>
	<bean:message bundle="titles" key="app.title.codigoIndustrialInternacionalUniforme"/>	
</h3>

<html:form action="/CiiuInventario">

	<pragmatags:tabla 
		toolbar="no"
		toolbaroptions=""
		columns="codigo,nombre"
		actions="linkSeleccion"
		decorator="com.fontar.web.decorator.configuracion.ciiu.CiiuWrapper"				   
		requestURI="CiiuSelectorPopUp.do" 
		collection="collection" 
		entity="Ciiu" />

</html:form>
<pragmatags:btnCerrar javascript="window.close();"/>



