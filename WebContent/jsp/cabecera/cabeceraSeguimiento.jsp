<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div>	
	<table class="recuadro">
		<tr>
			<!-- Proyecto -->
			<td class="label">
				<bean:message bundle="labels" key="app.label.nroProyecto"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="seguimiento" property="cabeceraProyecto.codigo" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="seguimiento" property="cabeceraProyecto.estado.descripcion" /></span>
				<c:if test="${seguimiento.cabeceraProyecto.estaEnPaquete}">
					(	
						<bean:message bundle="labels" key="app.label.enPaquete"/>
					)
				</c:if>
				<c:if test="${seguimiento.cabeceraProyecto.isCerrado}">
					(	
						<span><bean:write name="seguimiento" property="cabeceraProyecto.motivoCierre" /></span>
					)
				</c:if>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.instrumento"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="seguimiento" property="cabeceraProyecto.instrumento" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.entidadBeneficiaria"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="seguimiento" property="cabeceraProyecto.txtEntidadBeneficiaria" /></span>
			</td>
		</tr>
		<%-- Datos del Seguimiento --%>
		<tr align="center">

			<td class="label">
				<bean:message bundle="labels" key="app.label.seguimiento"/>:
			</td>
			<td>
				<bean:write name="seguimiento" property="descripcion" />
				(<bean:write name="seguimiento" property="numeroSeguimiento" />)
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.fecha"/>:&nbsp;
			</td>			
			<td>
				<span>
					<bean:write name="seguimiento" property="fecha" />
				</span>
			</td>
			
			<td class="label">
				<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
			</td>			
			<td>
				<span>
					<bean:write name="seguimiento" property="descripcionEstado" />
				</span>
			</td>

			<td colspan="2">&nbsp;</td>
		</tr>
		
	</table>
	<br>
</div>


