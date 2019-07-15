<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div>	
	<table class="recuadro">
		<tr>
			<td class="label">
				<bean:message bundle="labels" key="app.label.nroProyecto"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="procedimiento" property="proyecto.codigo" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.nroProcedimiento"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="procedimiento" property="codigo" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.fechaRecepcion"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="procedimiento" property="fechaRecepcion" formatKey="app.date.largeFormat"/></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="procedimiento" property="estado.descripcion" /></span>
			</td>
		</tr>
	</table>
	<br>
</div>


