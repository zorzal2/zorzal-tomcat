<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<!--  Detalle Evaluacion -->
<table class="formulario">
	<c:if test="${tieneEvaluadores}">
	<tr>
		<td colspan="2">
			<pragmatags:boton action="/EditarEvaluacionProyecto?id=${id}" permissions="EVALUACIONES-EDITAR" label="app.label.editarEvaluacion"/>
		</td>
	</tr>
	</c:if>

	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.general" />
		</th>
	</tr>
	<tr>
		<td width="20%">
			<bean:message bundle="labels" key="app.label.tipo"/>
		</td>
		<td align="left">
			<bean:write name="evaluacion" property="tipos"/>
		</td>
	</tr>		
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaInicial"/>
		</td>
		<td align="left">
			<bean:write name="evaluacion" property="fechaInicial"/>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaEntrega"/>
		</td>
		<td align="left">
			<bean:write name="evaluacion" property="fechaEntregaComprometida"/>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaEvaluacion"/>
		</td>		
		<td align="left">
			<bean:write name="evaluacion" property="fecha"/>
		</td>
	</tr>
	<c:if test="${!evaluacion.resumido}">
		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.resultado"/>
			</td>
			<td align="left">
				<bean:write name="evaluacion" property="resultadoDescripcion"/>
			</td>
		</tr>
		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.fundamentacion"/>
			</td>
			<td align="left">
				<bean:write name="evaluacion" property="fundamentacion"/>
			</td>
		</tr>
	</c:if>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</td>
		<td>
			<bean:write name="evaluacion" property="observacion"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="obligatorio">
			<bean:message bundle="labels" key="app.label.evaluadores" />
		</td>
	</tr>
	
	<tr>
		<td colspan="2">
			<!--  evaluadores -->
			<jsp:include page="evaluadores.jsp" flush="true"/>
		</td>
	</tr>

	<c:if test="${!empty esProyecto}">
		<tr>
			<td colspan="2" class="obligatorio">
				<bean:message bundle="labels" key="app.label.criterios" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<!--  criterios -->
				<jsp:include page="criterios.jsp" flush="true"/>
			</td>
		</tr>
	</c:if>
</table>