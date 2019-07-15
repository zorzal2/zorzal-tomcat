<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/consultas" prefix="consultas" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib tagdir="/WEB-INF/tags/reportes" prefix="reportes" %>


<html:form action="/EjecutarReporte" >

	<input type="hidden" id="id" name="id" value="${reporte.nombre}"/>

	<table class="filtros">
		<tr>
			<td>
				<consultas:resolucionPresentacionFilter/>
			</td>
			<td>
				<reportes:applyFilters />
			</td>
			<td>
				<reportes:clearFilters />
			</td>
		</tr>
	</table>
		
</html:form>


