<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
<table class="formulario">		
	<tr>		
		<th colspan="8" class="titulo">
			<bean:message bundle="headers" key="app.header.datos" />
		</th>	
	</tr>
	
	<tr>
		<td width="25%">
			<bean:message bundle="labels" key="app.label.nroProcedimiento"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="codigo" />
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaRecepcion"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="fechaRecepcion" formatKey="app.date.largeFormat"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="estado.descripcion" />
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.tipoAdquisicion"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="tipoAdquisicion.descripcion" />
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.descripcion"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="descripcion" />
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.nroProyecto"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="proyecto.codigo" />
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.evaluador"/>:&nbsp;
		</td>
		<td class="opcional">
			<c:if test="${not empty procedimiento.evaluador}">
			<bean:write name="procedimiento" property="evaluador.nombre" />
			</c:if>
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaAsignacionEvaluador"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="fechaAsignacionEvaluador" formatKey="app.date.largeFormat"/>
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.observacionesAsignacion"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="descripcionAsignacionEvaluador" />
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaFundamentacion"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="fechaFundamentacion" formatKey="app.date.largeFormat"/>
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.observacionesFundamentacion"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="descripcionFundamentacion" />
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaResultadoFontar"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="fechaResultadoFontar" formatKey="app.date.largeFormat"/>
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.resultadoFontar"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="observacionResultadoFontar" />
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaRemisionUffa"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="fechaRemisionUffa" formatKey="app.date.largeFormat"/>
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.remisionUffa"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="observacionRemisionUffa" />
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaRemisionBid"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="fechaRemisionBid" formatKey="app.date.largeFormat"/>
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.remisionBid"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="observacionRemisionBid" />
		</td>
	<tr/>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaResultadoUffa"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="fechaResultadoUffa" formatKey="app.date.largeFormat"/>
		</td>
	<tr/>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.resultadoUffa"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="observacionResultadoUffa" />
		</td>
	<tr/>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaResultadoBid"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="fechaResultadoBid" formatKey="app.date.largeFormat"/>
		</td>
	<tr/>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.resultadoBid"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="procedimiento" property="observacionResultadoBid" />
		</td>
	<tr/>
</table>

</div>