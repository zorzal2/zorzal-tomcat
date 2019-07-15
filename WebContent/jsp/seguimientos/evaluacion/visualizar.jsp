<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<div>
<table class="formulario">
	<pragma:actionAuthorize permission="${sessionScope['ACTION_AUTHORIZE']}">
		<tr>
			<td>
				<pragmatags:btnDynaLabel action="/EditarEvaluacionSeguimiento?visualizando=yes&id=${id}" label="app.label.editar" />
			</td>
		</tr>
	</pragma:actionAuthorize>
	
	<pragma:actionAuthorize permission="${!sessionScope['ACTION_AUTHORIZE']}">
		<tr>
			<td colspan="2">
				<pragmatags:boton action="/EditarEvaluacion" permissions="EVALUACIONESSEGUIMIENTO-EDITAR" label="app.label.editarEvaluacion"/>
			</td>
		</tr>
	</pragma:actionAuthorize>
	
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.general" />
		</th>
	</tr>
	<tr>
		<th width="20%">
			<bean:message bundle="labels" key="app.label.tipo"/>
		</th>
		<td align="left">
			<bean:write name="evaluacion" property="clasificacion"/>
		</td>
	</tr>		
	<tr>
		<th>
			<bean:message bundle="labels" key="app.label.fechaInicial"/>
		</th>
		<td align="left">
			<bean:write name="evaluacion" property="fechaInicial"/>
		</td>
	</tr>
	<tr>
		<th>
			<bean:message bundle="labels" key="app.label.fechaEntrega"/>
		</th>
		<td align="left">
			<bean:write name="evaluacion" property="fechaEntregaComprometida"/>
		</td>
	</tr>
	<tr>
		<th>
			<bean:message bundle="labels" key="app.label.fechaEvaluacion"/>
		</th>		
		<td align="left">
			<bean:write name="evaluacion" property="fecha"/>
		</td>
	</tr>
	<c:if test="${!evaluacion.resumido}">
		<tr>
			<th>
				<bean:message bundle="labels" key="app.label.resultado"/>
			</th>
			<td align="left">
				<bean:write name="evaluacion" property="resultado"/>
			</td>
		</tr>
		<tr>
			<th colspan="2">
				<bean:message bundle="labels" key="app.label.fundamentacion"/>
			</th>
		</tr>
		<tr>
			<td align="left" colspan="2">
				<bean:write name="evaluacion" property="fundamentacion"/>
			</td>
		</tr>
	</c:if>
	<tr>
		<th>
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</th>
		<td>
			<bean:write name="evaluacion" property="observacion"/>
		</td>
	</tr>
	
	<tr>
	</tr>

	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.evaluadores" />
		</th>
	</tr>
	
	<tr>
		<td colspan="2">
			<jsp:include page="/jsp/administracion/evaluacion/evaluadores.jsp" flush="true"/>
		</td>
	</tr>
</table>
</div>

