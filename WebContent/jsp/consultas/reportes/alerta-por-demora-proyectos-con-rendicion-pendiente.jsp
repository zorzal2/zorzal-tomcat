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
			<td colspan="5">
				Este reporte muestra los proyectos para los cuales se han realizado todos los pagos previstos pero aún no se ha rendido la totalidad del monto desembolsado.
			</td>
		</tr>
		<tr>
			<td colspan="5">
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.convocatoria"/>:
			</td>
			<td>
				<html:select property="filter(llamadoAConvocatoria)">        
					<html:options collection="llamadosAConvocatoria" property="value" labelProperty="label"/>
				</html:select>
			</td>
			<td width="50%">
				<!-- Espacio -->
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


