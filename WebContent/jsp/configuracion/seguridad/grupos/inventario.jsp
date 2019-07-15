<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>


<h3><bean:message bundle="titles" key="app.title.grupos" /></h3>

<pragmatags:btnAgregar action="/GruposAgregar" permissions="GRUPOS-AGREGAR"/>	
<br/>

<html:form action="GruposInventario">
	<display-el:table name="grupos"  requestURI="GruposInventario.do" class="inventario" pagesize="15" decorator="com.fontar.web.decorator.configuracion.seguridad.GruposWrapper">
		<display-el:setProperty name="basic.show.header" value="false">
		<display-el:caption>	
				<tr>
				    <th>Nombre</th>
				    <th>Instrumento</th>
				    <th colspan="3"></th>
		        </tr>
			</display-el:caption>	
			<display-el:column  property="nombre"/>
			<display-el:column  property="instrumento"/>
		    <display-el:column  property="linkVisualizar" />
			<display-el:column  property="linkEditar"/>
			<display-el:column  property="linkBorrar"/>	
		</display-el:setProperty>
	</display-el:table>
</html:form>

