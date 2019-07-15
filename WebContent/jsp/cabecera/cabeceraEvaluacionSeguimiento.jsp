<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div>	
	<table class="recuadro">
		<!-- Proyecto  y Seguimiento-->
		<tr>
			<td class="label">
				<bean:message bundle="labels" key="app.label.nroProyecto"/>:
			</td>
			<td>
				<bean:write name="evaluacionSeguimiento" property="idProyecto" />
			</td>
			<td  class="label">
				<bean:message bundle="labels" key="app.label.instrumento"/>:
			</td>
			<td>
				<bean:write name="evaluacionSeguimiento" property="idInstrumento" />
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.entidadBeneficiaria"/>:
			</td>
			<td>
				<span><bean:write name="evaluacionSeguimiento" property="entidadBeneficiaria" /></span>
			</td>	
			<td class="label">
				<bean:message bundle="labels" key="app.label.seguimiento"/>:
			</td>
			<td>
				<bean:write name="evaluacionSeguimiento" property="descripcionDeSeguimiento" />
				(<bean:write name="evaluacionSeguimiento" property="idSeguimiento" />)
			</td>
		</tr>

		<%-- Datos de evaluacionSeguimiento --%>
		<tr>
			<td class="label">
				<bean:message bundle="labels" key="app.label.evaluacion"/>:
			</td>			
			<td>
				<bean:write name="evaluacionSeguimiento" property="idEvaluacion" />
			</td>

			<td class="label">
				<bean:message bundle="labels" key="app.label.fecha"/>:
			</td>			
			<td>
				<bean:write name="evaluacionSeguimiento" property="fecha" />
			</td>
			
			<td class="label">
				<bean:message bundle="labels" key="app.label.estado"/>:
			</td>			
			<td>
				<bean:write name="evaluacionSeguimiento" property="estado" />
			</td>

			<td class="label">
				<bean:message bundle="labels" key="app.label.tipo"/>:
			</td>			
			<td>
				<bean:write name="evaluacionSeguimiento" property="tipo" />
			</td>

		</tr>
		
	</table>
	<br>
</div>


