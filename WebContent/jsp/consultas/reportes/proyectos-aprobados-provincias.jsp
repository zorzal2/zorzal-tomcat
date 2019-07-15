<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/consultas" prefix="consultas" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib tagdir="/WEB-INF/tags/reportes" prefix="reportes" %>


<html:form action="/EjecutarReporte" >
	<input type="hidden" id="id" name="id" value="${reporte.nombre}"/>

	<table class="filtros">
		<tr>
			<td>
			Jurisdicción
				<html:select property="filter(jurisdiccion)">        
					<html:options collection="jurisdicciones" property="value" labelProperty="label"/>
				</html:select>
			</td>
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
