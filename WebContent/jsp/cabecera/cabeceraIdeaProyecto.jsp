<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<!--  Header Idea Proyecto -->
<div>	
	<table class="recuadro">
		<tr>
			<td class="label">
				<bean:message bundle="labels" key="app.label.ideaProyecto"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="ideaProyecto" property="codigoIdeaProyecto" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.jurisdiccion"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="ideaProyecto" property="jurisdiccion" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.entidadBeneficiaria"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="ideaProyecto" property="entidadBeneficiaria" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
			</td>
			<!-- Estado proyecto -->
			<td>
				<span><bean:write name="ideaProyecto" property="estado.descripcion" /></span>
			</td>
		</tr>			
	</table>
	<br>
</div>
