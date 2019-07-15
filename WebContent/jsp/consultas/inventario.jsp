<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<h3>
	<bean:message bundle="titles" key="app.title.consultas"/>
</h3>

<display-el:table name="consultas"  requestURI="ConsultasFiltrar.do" class="inventario" pagesize="15" decorator="com.fontar.web.action.consultas.ConsultasWrapper">
	<display-el:setProperty name="basic.show.header" value="false">
	<display-el:caption>	
		<tr>
		    <th width="20%">Nombre</th>
		    <th width="70%">Descripción</th>
		    <th>Acciones</th>
		</tr>
	</display-el:caption>	
	<display-el:column  property="titulo"/>
		<display-el:column  property="descripcion"/>
		<display-el:column  property="visualizar"/>
	</display-el:setProperty>
</display-el:table>
