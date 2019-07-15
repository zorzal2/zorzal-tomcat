<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<!--  Header de Proyecto solito -->
<div>	
	<table class="recuadro">
		<tr>
			<!-- Proyecto -->
			<td class="label">
				<bean:message bundle="labels" key="app.label.nroPaquete"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="paquete" property="idPaquete" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.instrumento"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="paquete" property="instrumento" /></span>
			</td>
			<c:if test="${!empty comision}">
				<td class="label">
					<bean:message bundle="labels" key="app.label.comision"/>:&nbsp;
				</td>
				<td>
					<span><bean:write name="paquete" property="comision" /></span>
				</td>
			</c:if>
			<td class="label">
				<bean:message bundle="labels" key="app.label.tratamiento"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="paquete" property="tratamiento.descripcion" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="paquete" property="estado.descripcion" /></span>
			</td>
		</tr>
	</table>
	<br>
</div>