<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>


<html:form action="GruposSelectorInventario">
	<display-el:table name="grupos"  requestURI="GruposSelectorInventario.do" class="inventario" pagesize="15" decorator="com.fontar.web.decorator.configuracion.seguridad.GruposWrapper">
		<display-el:setProperty name="basic.show.header" value="false">
			<display-el:column  property="nombre" />
			<display-el:column  property="instrumento"/>
		    <display-el:column  property="linkSeleccionar" />
		</display-el:setProperty>
	</display-el:table>
</html:form>