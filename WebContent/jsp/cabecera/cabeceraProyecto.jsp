<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<!--  Header de Proyecto solito -->
<div>	
	<table class="recuadro">
		<tr>
			<!-- Proyecto -->
			<td class="label">
				<bean:message bundle="labels" key="app.label.nroProyecto"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="proyecto" property="codigo" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.instrumento"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="proyecto" property="instrumento" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.entidadBeneficiaria"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="proyecto" property="txtEntidadBeneficiaria" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
			</td>
			<!-- Estado proyecto -->
			<td>
				<span><bean:write name="proyecto" property="estado.descripcion" /></span>
				<c:if test="${proyecto.estaEnPaquete}">
					(	
						<bean:message bundle="labels" key="app.label.enPaquete"/>
					)
				</c:if>
				<c:if test="${proyecto.isCerrado}">
					(	
						<span><bean:write name="proyecto" property="motivoCierre" /></span>
					)
				</c:if>
			</td>
		</tr>
	</table>
	<br>
</div>