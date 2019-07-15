<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<h3>
	<bean:message bundle="titles" key="app.title.detalleProyectosAgregadosExcel"/>
</h3> 
<br>
<display:table name="proyectos" class="inventario">
	<display:column title="Código" property="codigo" />   
	<display:column title="Título" property="proyectoDatos.titulo" /> 
	<display:column title="Entidad Beneficiaria" property="proyectoDatos.txtEntidadBeneficiaria" />
</display:table>
<br/>
<!-- Volver al inventario -->
<html-el:link action="/ProyectoInventario.do">
	<bean:message bundle="labels" key="app.label.volver"/>	
</html-el:link>