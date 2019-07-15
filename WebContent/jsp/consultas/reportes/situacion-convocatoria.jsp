<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/reportes" prefix="reportes" %>


<html:form action="/EjecutarReporte" >

	<input type="hidden" id="id" name="id" value="${reporte.nombre}"/>
	
	<table class="filtros">
		<tr>
			<td>
				<span> Convocatorias
					<html:select property="filter(convocatoria)">        
						<html:options collection="convocatorias" property="value" labelProperty="label"/>
					</html:select>
				</span>
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
