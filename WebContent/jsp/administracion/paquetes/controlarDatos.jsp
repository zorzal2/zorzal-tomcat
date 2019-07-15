<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body>
<html:form action="/ControlarPaqueteGuardar">

<html:hidden property="id"/>
<html:hidden property="tipoPaquete"/>
<pragmatags:tabla 
	toolbar="no" 
	pagesize="1000"
	toolbaroptions="" 
	columns="codigo,entidadBeneficiaria,titulo,recomendacion" 
	actions="selectorProyecto"
	decorator="com.fontar.web.decorator.administracion.paquete.ProyectoFilaModificacionPaqueteDTOWrapper"
	requestURI="ControlarPaquete.do" 
	collection="proyectosList" 
	entity=""/>

</html:form>
</body>
