<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el"%>

<html:form action="/FinalizarControlEvaluacionGuardar">

	<script>
function editarPresupuesto() {
	var idProyecto = document.getElementsByName('idProyecto')[0].value;
	var idPresupuestoInicial = document.getElementsByName('idPresupuestoInicial')[0].value;
	var idPresupuestoDefinitivo_randomVarName = document.getElementsByName("idPresupuestoDefinitivo_randomVarName")[0].value;
	var url = new Pragma.URL();
	url.
		setPathFile('EditarPresupuestoFinalizarControlIngresar.do').
		setParameter('idPresupuesto', idPresupuestoInicial).
		setParameter('idProyecto', idProyecto).
		setParameter('idPresupuestoDefinitivo_randomVarName', idPresupuestoDefinitivo_randomVarName);

 	window.open(url.toString(), "EditarPresupuestoFinalizarControl","width=700,height=580,left=100,top=70,resizable=yes,status=yes,help=yes,scrollbars=1");
}
</script>

	<html:hidden property="idEvaluacion" />
	<html:hidden property="idProyecto" />
	<input type="hidden" name="idPresupuestoDefinitivo_randomVarName"
		value='<bean:write name="idPresupuestoDefinitivo_randomVarName"/>' />
	<input type="hidden" name="idPresupuestoInicial"
		value='<bean:write name="idPresupuestoInicial" ignore="true" />' />

	<table class="formulario">
		<tr>
			<td colspan="3" class="obligatorio"><bean:message bundle="headers"
				key="app.header.evaluacionesConfirmadas" /></td>
		</tr>

		<tr>
			<td colspan="3"><display-el:table name="evaluacionesList"
				class="inventario">
				<display-el:setProperty name="basic.show.header" value="false" />

				<display-el:caption>
					<tr>
						<th><bean:message bundle="headers" key="app.header.nroEvaluacion" /></th>
						<th><bean:message bundle="headers" key="app.header.tipoEvaluacion" /></th>
						<th><bean:message bundle="headers" key="app.header.evaluadores" /></th>
						<th><bean:message bundle="headers"
							key="app.header.resultadoEvaluacion" /></th>
					</tr>
				</display-el:caption>

				<display-el:column media="html" property="idEvaluacion"
					escapeXml="true" />
				<display-el:column media="html" property="tipo" escapeXml="true" />
				<display-el:column media="html" property="evaluadores" />
				<display-el:column media="html" property="resultado"
					escapeXml="true" />
			</display-el:table></td>
		</tr>
	</table>
	<br>
	<jsp:include page="/jsp/evaluacion/resumenDePresupuesto.jsp" flush="true"/>
	<br>
	<table class="formulario">
		<!--  Resultado -->
		<tr>
			<td class="obligatorio"><bean:message bundle="labels"
				key="app.label.fecha" /></td>
			<td align="left"><pragmatags:calendar propertyName="fechaEvaluacion"
				top="0" left="0" /> <pragmatags:error property="fechaEvaluacion" />
			</td>
			<td align="right"><pragmatags:btnDynaLabel
				javascript="editarPresupuesto()" label="app.label.editarPresupuesto" />&nbsp;
			</td>
		</tr>
		<tr>
			<td class="obligatorio"><bean:message bundle="labels"
				key="app.label.resultado" /></td>
			<td align="left"><html:select property="recomendacion">
				<html:options collection="recomendaciones" property="value"
					labelProperty="label" />
			</html:select> <pragmatags:error property="recomendacion" /></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td class="opcional"><bean:message bundle="labels"
				key="app.label.fundamentacion" /></td>
			<td align="left" colspan="2"><html:textarea property="fundamentacion"
				rows="4" cols="100" /></td>
		</tr>

	</table>
</html:form>
