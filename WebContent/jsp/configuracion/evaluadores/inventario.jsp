<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<h3>
	<bean:message bundle="titles" key="app.title.evaluadores"/>
</h3>
<html:form action="EvaluadoresInventario">	

	<pragmatags:tabla 
		toolbar="yes"
		toolbaroptions="list,selcolumns,filter,sort,page,excel"
		columns="id,cuit,nombre" 
		actions="linkAlta,linkEdicion,linkBorrado"
		decorator="com.fontar.web.decorator.configuracion.evaluadores.EvaluadoresWrapper"
		requestURI="EvaluadoresInventario.do" 
		collection="evaluadores" 
		entity="Evaluadores"/>

</html:form>