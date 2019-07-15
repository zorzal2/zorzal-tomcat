<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<body>
<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>

	<tr>
		<td width="25%">
			<bean:message bundle="labels" key="app.label.instrumento"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="instrumentoDef.denominacion"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.version"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="instrumentoVersionActual.version"/>
		</td>		
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.estado" />:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="estado"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.identificador" />:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="identificador"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.apertura" />:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="fechaApertura" formatKey="app.date.largeFormat"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.cierre" />:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento"  property="fechaCierre" formatKey="app.date.largeFormat"/>
		</td>
	</tr> 
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.denominacion"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="denominacion"/>
		</td>
	</tr>  
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.resoluciones" />:&nbsp;
		</td>
		<td>
		<button onclick="javascript:popUpMostrarVersionInstrumento('Convocatoria','${instrumento.id}','${instrumento.identificador}');">
			<img src="images/historial.gif" alt="Historial">
		</button>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.modalidadBeneficio" />:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="modalidad"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.paraProyectoHistorico" />:&nbsp;
		</td>
		<td class="opcional">
			<html:radio name="instrumento" property="paraProyectoHistorico" value="true" disabled="true">
				<bean:message bundle="labels" key="app.label.si" />
			</html:radio> 
			<html:radio name="instrumento" property="paraProyectoHistorico" value="false" disabled="true">
				<bean:message bundle="labels" key="app.label.no" />
			</html:radio>
		</td>
	</tr>	
		
	<tr rowspan="4">
		<td valign="middle">
			<bean:message bundle="labels"key="app.label.circuitoEvaluacion"/>:&nbsp;
		</td>
		<td class="opcional">
			<html:checkbox name="instrumento" property="permiteFinanciamientoBancario" disabled="true"/> 
			<bean:message bundle="labels" key="app.label.financiacionBancaria" />
		</td>
	</tr>
	
	<tr>
		<td></td>
		<td class="opcional">
			<html:checkbox name="instrumento" property="permiteComision" disabled="true"/> 
			<bean:message bundle="labels" key="app.label.comision" />:&nbsp;
			<c:if test="${instrumento.permiteComision}">
				<bean:write name="instrumento" property="comision.denominacion"/>			
			</c:if>
		</td>
	</tr>
	
	<tr>
		<td></td>
		<td class="opcional">
			<html:checkbox name="instrumento" property="permiteSecretaria" disabled="true"/> 
			<bean:message bundle="labels" key="app.label.secretariaPermanente" />
		</td>
	</tr>
	
	<tr>
		<td></td>
		<td class="opcional">
			<html:checkbox name="instrumento" property="permiteAdjudicacion" disabled="true" /> 
			<bean:message bundle="labels" key="app.label.adjudicacionDirecta" />
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.plazoReadmision" />:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="plazoReadmision"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.plazoReconsideracion"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="plazoReconsideracion"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.plazoFirmaContrato" />:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="firmaContrato"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.multiplesJurisdicciones" />:&nbsp;
		</td>
		<td class="opcional">
			<html:radio name="instrumento" property="permiteMultipleJurisdiccion" value="true" disabled="true">
				<bean:message bundle="labels" key="app.label.si" />
			</html:radio> 
			<html:radio name="instrumento" property="permiteMultipleJurisdiccion" value="false" disabled="true">
				<bean:message bundle="labels" key="app.label.no" />
			</html:radio>
		</td>
	</tr>	
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.propiciar" />:&nbsp;
		</td>
		<td class="opcional">
			<html:radio name="instrumento" property="permitePropiciado" value="true" disabled="true">
				<bean:message bundle="labels" key="app.label.si" />
			</html:radio> 
			<html:radio name="instrumento" property="permitePropiciado" value="false" disabled="true">
				<bean:message bundle="labels" key="app.label.no" />
			</html:radio>
		</td>
	</tr>	
	
	<%-- Financiamiento --%>
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.financiamiento"/>
		</th>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.montoFinanciamientoTotal"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="montoFinanciamientoTotal" format="###,##0.00"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.tipoAsignacionFinanciamiento"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="tipoFinanciamientoAsignacion.descripcion"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.limitesFinanciamiento"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="tipoFinanciamiento.descripcion"/>
		</td>
	</tr>	

	<c:if test="${instrumento.tipoFinanciamiento.name=='POR_BENEFICIARIO'}">
		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.montoLimitesFinanciamiento"/>:&nbsp;
			</td>
			<td class="opcional">
				<bean:write name="instrumento" property="montoFinanciamiento" format="###,##0.00"/>
			</td>
		</tr>	
	</c:if>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaReconocimientoGastos"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="fechaReconocimientoGastos" formatKey="app.date.largeFormat"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.proporcionApoyo"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="proporcionApoyo"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.periodoGracia"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="periodoGracia"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.plazoEjecucion"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="plazoEjecucion"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.plazoAmortizacion"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="plazoAmortizacion"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.tasaInteres"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="tasaInteres"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.garantias"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="garantia"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.matrizPresupuesto"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="matrizPresupuesto.nombre"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.observaciones"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="observaciones"/>
		</td>
	</tr>

	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	
	<%-- Emerix --%>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.emerix"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="emerix"/>
		</td>
	</tr>	
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.cartera"/>:&nbsp;
		</td>
		<td class="opcional">
			<c:if test="${not empty instrumento.cartera}">
			<bean:write name="instrumento" property="cartera.codigo"/>
			</c:if>
		</td>
	</tr>	
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.variante"/>:&nbsp;
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="varianteEmerix"/>
		</td>
	</tr>
</table>
</body>
