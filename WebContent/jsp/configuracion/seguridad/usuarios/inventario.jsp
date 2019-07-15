<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>


<h3><bean:message bundle="titles" key="app.title.usuarios" /></h3>
<pragmatags:btnAgregar action="/UsuariosAgregar" permissions="USUARIOS-AGREGAR"/>	
<br/>			
<html:form action="UsuariosInventario">	
	<display-el:table name="usuarios"  requestURI="UsuariosInventario.do" class="inventario" pagesize="15" decorator="com.fontar.web.decorator.configuracion.seguridad.UsuariosWrapper">
		<display-el:setProperty name="basic.show.header" value="false">
			<display-el:caption>	
				<tr>
				    <th>Identificador</th>	
				    <th>Nombre</th>
			    	<th>Apellido</th>
			    	<th>Persona Relacionada</th>
				    <th colspan="4"></th>
		        </tr>
			</display-el:caption>	
			<display-el:column  property="userId"/>
			<display-el:column  property="nombre" />
			<display-el:column  property="apellido" />	
			<display-el:column  property="nombrePersona" />	
			<display-el:column  property="linkVisualizar" />	
			<display-el:column  property="linkEditar"/>
			<display-el:column  property="linkResetearClave"/>
			<display-el:column  property="linkBorrar"/>	
		</display-el:setProperty>
	</display-el:table>
	<br/>
</html:form>

