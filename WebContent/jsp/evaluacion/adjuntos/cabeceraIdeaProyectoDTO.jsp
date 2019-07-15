<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<!--  Header proyecto + evaluacion -->
<div>	
	<table class="recuadro">
		
		<tr>
			<td class="label">
				<bean:message bundle="labels" key="app.label.numeroIdeaProyecto"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="evaluacion" property="evaluable.codigoIdeaProyecto" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.jurisdiccion"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="evaluacion" property="evaluable.jurisdiccion" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.entidadBeneficiaria"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="evaluacion" property="cabecera.entidadBeneficiaria" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="evaluacion" property="cabecera.estadoProyecto" /></span>
			</td>
		</tr>			
		
		<tr><td colspan="8">&nbsp;</td></tr>
		
		<!--  Evaluacion -->
		<tr>
			<td class="label">
				<bean:message bundle="labels" key="app.label.evaluacion"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="evaluacion" property="id" /></span>
			</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="evaluacion" property="estado.descripcion" /></span>
			</td>
		</tr>
	</table>
	<br>
</div>
