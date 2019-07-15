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
			<td colspan="7">
				Poner aquí una descripción del reporte
			</td>
		</tr>
		<tr>
			<td colspan="7">
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.convocatoria"/>:
			</td>
			<td>
				<!-- Ejemplo de filtro en forma de listado desplegable -->
				<html:select property="filter(llamadoAConvocatoria)">        
					<html:options collection="llamadoAConvocatoria" property="value" labelProperty="label"/>
				</html:select>
			</td>
			
			<td>
				<bean:message bundle="labels" key="app.label.nombreEntidad"/>:
			</td>
			<td>
				<!-- Ejemplo de filtro de texto libre -->
				<html:text property="filter(nombreEntidad)" />
			</td>
			<td width="50%">
				<!-- Espacio -->
			</td>
			<td>
				<!-- Botón "Generar" -->
				<reportes:applyFilters />
			</td>
			<td>
				<!-- Botón "Limpiar" -->
				<reportes:clearFilters />
			</td>
		</tr>
	</table>
		
</html:form>


