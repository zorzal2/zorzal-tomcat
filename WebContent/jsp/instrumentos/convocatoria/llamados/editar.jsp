<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<script>
	function setValueCheckBox() {
		var permiteFinanciamientoBancario = document.forms[0].permiteFinanciamientoBancario;
		var permiteFinanciamientoBancario_ = document.forms[0].permiteFinanciamientoBancario_;
		if (permiteFinanciamientoBancario_.value != '') {
			if (permiteFinanciamientoBancario_.value == 'true') {
				permiteFinanciamientoBancario.checked = true;
			} else {
				permiteFinanciamientoBancario.checked = false;
			}
		}
		
		var permiteComision = document.forms[0].permiteComision;
		var permiteComision_ = document.forms[0].permiteComision_;
		if (permiteComision_.value != '') {
			if (permiteComision_.value == 'true') {
				permiteComision.checked = true;
			} else {
				permiteComision.checked = false;
			}
		}		
		
		var permiteSecretaria = document.forms[0].permiteSecretaria;
		var permiteSecretaria_ = document.forms[0].permiteSecretaria_;
		if (permiteSecretaria_.value != '') {
			if (permiteSecretaria_.value == 'true') {
				permiteSecretaria.checked = true;
			} else {
				permiteSecretaria.checked = false;
			}
		}
		
		var permiteAdjudicacion = document.forms[0].permiteAdjudicacion;
		var permiteAdjudicacion_ = document.forms[0].permiteAdjudicacion_;
		if (permiteAdjudicacion_.value != '') {
			if (permiteAdjudicacion_.value == 'true') {
				permiteAdjudicacion.checked = true;
			} else {
				permiteAdjudicacion.checked = false;
			}
		}
	}
	
	function setHiddenValue(){
		document.forms[0].permiteFinanciamientoBancario_.value = document.forms[0].permiteFinanciamientoBancario.checked;
		document.forms[0].permiteComision_.value = document.forms[0].permiteComision.checked;
		document.forms[0].permiteSecretaria_.value = document.forms[0].permiteSecretaria.checked;
		document.forms[0].permiteAdjudicacion_.value = document.forms[0].permiteAdjudicacion.checked;
	}

	function changeComision(estado) 
	{		
		//document.LlamadoConvocatoriaEditarDynaForm.permiteComision_.value = estado;
		
		if (!estado) 
		{
			txtComisionField = document.LlamadoConvocatoriaEditarDynaForm.txtComision;
			if (txtComisionField)
				txtComisionField.value = '';
			
			idComisionField = document.LlamadoConvocatoriaEditarDynaForm.idComision;
			if (idComisionField)			
				idComisionField.value = '';	
		}
		
		toggleVisibility('selectorComision',estado);
	}
	
	function setCheckBoxComision() 
	{	
		permiteComision = document.LlamadoConvocatoriaEditarDynaForm.permiteComision;
		
		if (!permiteComision.checked) 
		{			
			permiteComision.checked = true;	
			
			txtComision = document.LlamadoConvocatoriaEditarDynaForm.txtComision;
			if (txtComision) {	
				if (txtComision.value == '')
					permiteComision.checked = false;  
			} 			
		}
		
		changeComision(permiteComision.checked);
		
		toggleVisibility('selectorComision', permiteComision.checked);
	}

</script>

<body onload="javascript:setValueCheckBox();setCheckBoxComision();
			  toggleVisibility('divBoxMonto',document.getElementById('codigoTipoFinanciamiento').value=='POR_BENEFICIARIO');
			  toggleVisibility('botonAsignar',!document.forms[0].elements['tipoDistribucionFinanciamiento'][0].checked);">
<br>

<h3>
	<pragmatags:titulo-edicion clase="LlamadoConvocatoria"/>
</h3>

<html:form action="/LlamadoConvocatoriaEditarNext">

<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<html:hidden property="id"/>
<html:hidden property="page" value="1"/>

<table class="formulario">
	<tr>
		<th colspan="5" class="titulo"><bean:message bundle="headers"
			key="app.header.general" /></th>
	</tr>
	<c:if test="${empty instrumentoNombre}">
		<tr>
			<td class="obligatorio"><bean:message bundle="labels" key="app.label.instrumento"/></td>
			<td class="opcional">
				<html:select property="idInstrumentoDef" onchange="javascript:changeFormAction('LlamadoConvocatoriaGetInstrumentoDefData')">
					<html:options collection="instrumentosDef" property="value" labelProperty="label" />
				</html:select> 
				<pragmatags:error property="idInstrumentoDef"/>
			</td>
		</tr>
	</c:if>
	<c:if test="${not empty instrumentoNombre}">
		<tr>
			<td class="opcional"><bean:message bundle="labels" key="app.label.instrumento"/></td>
			<td><span class="opcional"><c:out value="${instrumentoNombre}"/></span></td>
			<td valign="middle" class="obligatorio">
				<bean:message bundle="labels" key="app.label.version"/>
				&nbsp;
				<span class="opcional"><c:out value="${version}"/></span>
			</td>		
			<td class="obligatorio"><bean:message bundle="labels" key="app.label.estado" /></td>
			<td class="opcional"><bean:write name="llamadoConvocatoriaBean" property="estado"/></td>
		</tr>
	</c:if>
	<tr>
		<td class="obligatorio"><bean:message bundle="labels"
			key="app.label.identificador" /></td>
		<td colspan="4">
			<html:text property="identificador" maxlength="20" size="20%" />
			<pragmatags:error property="identificador" />
		</td>
	</tr>
	<!-- TODO. SS-descomentar estas lineas y eliminar el hidden siguiente, se saco para la primera entrega -->
	<%--  
	<tr>
		<td class="obligatorio"><bean:message bundle="labels"
			key="app.label.ideaProyectoPitec" />
		</td>
		<td colspan="4">
			<html:radio property="esIdeaProyectoPitec" value="true">
				<bean:message bundle="labels" key="app.label.si" />
			</html:radio> 
			<html:radio property="esIdeaProyectoPitec" value="false">
				<bean:message bundle="labels" key="app.label.no" />
			</html:radio>
			<pragmatags:error property="esIdeaProyectoPitec" />	
		</td>
	</tr>
	--%>
	
	<input type="hidden" name="esIdeaProyectoPitec" value="false">
	<!-- TODO. SS-hasta aca -->
	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.apertura" />
		</td>
		<td colspan="4">
			<pragmatags:calendar propertyName="fechaApertura" top="255" left="250" />
			<pragmatags:error property="fechaApertura" />
		</td>
	</tr>
	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.cierre" /></td>
		<td colspan="4">
			<pragmatags:calendar propertyName="fechaCierre" top="285" left="250" />
			<pragmatags:error property="fechaCierre" />
		</td>
	</tr> 
	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.denominacion"/></td>
		<td colspan="4">
			<html:text property="denominacion" maxlength="60" size="60%"/>
			<pragmatags:error property="denominacion"/>
		</td>
	</tr>  
 	<c:if test="${not empty param.id}">
		<tr>
			<td class="opcional"><bean:message bundle="labels"
				key="app.label.resoluciones" /></td>
			<td colspan="4">
			<button onclick="popUpInstrumentoVersion('Convocatoria');">
				<img src="images/historial.gif" alt="Historial">
			</button>
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.modalidadBeneficio"/>
		</td>
		<td colspan="4">
			<html:textarea property="modalidad" rows="2" cols="100" />
			<pragmatags:error property="modalidad" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.paraProyectoHistorico"/>
		</td>
		<td colspan="5">
			<html:radio property="paraProyectoHistorico" value="true">
				<bean:message bundle="labels" key="app.label.si"/>
			</html:radio>
			<html:radio property="paraProyectoHistorico" value="false">
				<bean:message bundle="labels" key="app.label.no"/>
			</html:radio>
			<pragmatags:error property="paraProyectoHistorico"/>
		</td>
	</tr>	
	<tr rowspan="4">
		<td class="opcional" valign="middle"><bean:message bundle="labels"
			key="app.label.circuitoEvaluacion" /></td>
		<td colspan="4">
			<html:checkbox property="permiteFinanciamientoBancario" /> 
			<html:hidden property="permiteFinanciamientoBancario_" /> 
			<bean:message bundle="labels" key="app.label.financiacionBancaria" />
		</td>
	</tr>
	<tr>
		<td></td>
		<td colspan="4">
			<html:checkbox property="permiteComision" onclick="javascript:changeComision(this.checked);" />
		    <html:hidden property="permiteComision_" />
			<bean:message bundle="labels" key="app.label.comision" />
			
			<div id="selectorComision" style="position:absolute;visibility:hidden;">
				<pragmatags:selectorInventario entidad="Comision" />
			</div>
		</td>
	</tr>
	<tr>
		<td></td>
		<td colspan="4">
			<html:checkbox property="permiteSecretaria" /> 
			<html:hidden property="permiteSecretaria_" /> 
			<bean:message bundle="labels" key="app.label.secretariaPermanente" />
		</td>
	</tr>
	<tr>
		<td></td>
		<td colspan="4">
			<html:checkbox property="permiteAdjudicacion" /> 
			<html:hidden property="permiteAdjudicacion_" /> 
			<bean:message bundle="labels" key="app.label.adjudicacionDirecta" />
		</td>
	</tr>
	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.plazoReadmision" /></td>
		<td colspan="4"><html:text property="plazoReadmision" maxlength="2" 
			size="10" style="text-align:right"/>
			<pragmatags:error property="plazoReadmision" />			
		</td>
	</tr>
	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.plazoReconsideracion" /></td>
		<td colspan="4"><html:text property="plazoReconsideracion"
			maxlength="2" size="10" style="text-align:right"/>
			<pragmatags:error property="plazoReconsideracion" />			
		</td>
	</tr>
	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.plazoFirmaContrato" /></td>
		<td colspan="4"><html:text property="firmaContrato" maxlength="2"
			size="10" style="text-align:right"/>
			<pragmatags:error property="firmaContrato" />				
		</td>
	</tr>
	<tr>
		<td class="obligatorio"><bean:message bundle="labels"
			key="app.label.multiplesJurisdicciones" /></td>
		<td colspan="4">
		<html:radio property="permiteMultipleJurisdiccion"
			value="true">
			<bean:message bundle="labels" key="app.label.si" />
		</html:radio> <html:radio property="permiteMultipleJurisdiccion"
			value="false">
			<bean:message bundle="labels" key="app.label.no" />
		</html:radio>
			<pragmatags:error property="permiteMultipleJurisdiccion" />			
		</td>
	</tr>
	<tr>
		<td class="obligatorio"><bean:message bundle="labels"
			key="app.label.propiciar" /></td>
		<td colspan="4">
			<html:radio property="permitePropiciado" value="true">
				<bean:message bundle="labels" key="app.label.si" />
			</html:radio> 
			<html:radio property="permitePropiciado" value="false">
				<bean:message bundle="labels" key="app.label.no" />
			</html:radio>
			<pragmatags:error property="permitePropiciado" />			
		</td>
	</tr>



	<%-- Financiamiento --%>

	<tr>
		<th colspan="5" class="titulo"><bean:message bundle="headers"
			key="app.header.financiamiento" /></th>
	</tr>

	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.montoFinanciamientoTotal" /></td>
		<td colspan="4"><html:text property="montoFinanciamientoTotal"
			maxlength="15" size="20%" style="text-align:right"/>
			<pragmatags:error property="montoFinanciamientoTotal" />				
		</td>
	</tr>

	<tr>
		<td class="obligatorio"><bean:message bundle="labels"
			key="app.label.tipoAsignacionFinanciamiento" /></td>
		<td colspan="4"><html:select property="codigoTipoFinanciamientoAsignacion">
			<html:options collection="tiposFinanciamientoAsignacion"
				property="value" labelProperty="label" />
		</html:select></td>
	</tr>

	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.tipoDistribucionFinanciamiento" /></td>
		<td colspan="4">
			<html:radio property="tipoDistribucionFinanciamiento" value="GLOBAL" onclick="javascript:toggleVisibility('botonAsignar',false);" > 
				<bean:message bundle="labels" key="app.label.global" /> 
			</html:radio>
			<html:radio property="tipoDistribucionFinanciamiento" value="REGIONAL" onclick="javascript:toggleVisibility('botonAsignar',true);" > 
				<bean:message bundle="labels" key="app.label.regional" /> 
			</html:radio>					
			<html:radio property="tipoDistribucionFinanciamiento" value="JURISDICCIONAL" onclick="javascript:toggleVisibility('botonAsignar',true);" > 
				<bean:message bundle="labels" key="app.label.jurisdiccion" /> 
			</html:radio>			

			&nbsp;&nbsp;
			<button id="botonAsignar"
				onmouseOver="javascript:this.style.cursor='hand'"
				onclick="popUpDistribucionJurisdiccion();">
				<img src="images/asignar.gif" alt="Asignar" />
			</button>
			<pragmatags:error property="tipoDistribucionFinanciamiento" />				
		</td>
	</tr>

	<tr>
		<td class="obligatorio" valign="top"><bean:message bundle="labels"
			key="app.label.distribucionTipoDeProyecto" /></td>
		<td colspan="4">
			<button class="button" onclick="javascript:popUpDistribucionTipoProyecto();" >Distribución tipo proyecto</button>
		</td>
	</tr>

	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.limitesFinanciamiento" />
		</td>
		<td colspan="4">
			<html:select property="codigoTipoFinanciamiento"
						 onchange="toggleVisibility('divBoxMonto',this.value=='POR_BENEFICIARIO')">
				<html:options collection="tiposFinanciamiento" property="value"	labelProperty="label" />
			</html:select>

			<div id="divBoxMonto" style="position:absolute;">
				<html:text property="montoFinanciamiento" maxlength="10" size="20%" style="text-align:right"/> 
					<pragmatags:error property="montoFinanciamiento" />	
			</div>
		</td>
	</tr>


	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.fechaReconocimientoGastos" /></td>
		<td colspan="4">
			<pragmatags:calendar propertyName="fechaReconocimientoGastos" top="765" left="250" />
			<pragmatags:error property="fechaReconocimientoGastos" />			
		</td>
	</tr>

	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.proporcionApoyo" /></td>
		<td colspan="4"><html:text property="proporcionApoyo" maxlength="6"
			size="10" style="text-align:right"/>
			<pragmatags:error property="proporcionApoyo" />			
		</td>
	</tr>
	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.periodoGracia" /></td>
		<td colspan="4"><html:textarea property="periodoGracia" rows="2" cols="100" />
			<pragmatags:error property="periodoGracia" />
		</td>
	</tr>
	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.plazoEjecucion" /></td>
		<td colspan="4"><html:textarea property="plazoEjecucion" rows="2" cols="100" />
			<pragmatags:error property="plazoEjecucion" />
		</td>
	</tr>
	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.plazoAmortizacion" /></td>
		<td colspan="4"><html:textarea property="plazoAmortizacion" rows="2" cols="100" />
			<pragmatags:error property="plazoAmortizacion" />
		</td>
	</tr>
	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.tasaInteres" /></td>
		<td colspan="4"><html:textarea property="tasaInteres" rows="2" cols="100" />
			<pragmatags:error property="tasaInteres" />
		</td>
	</tr>
	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.garantias" /></td>
		<td colspan="4"><html:textarea property="garantia" rows="2" cols="100" />
			<pragmatags:error property="garantia" />
		</td>
	</tr>

	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.matrizPresupuesto" /></td>
		<td class="opcional" colspan="4">
			<html:select property="idMatrizPresupuesto">
				<html:options collection="matrices" property="value" labelProperty="label" />
			</html:select>
			<pragmatags:error property="idMatrizPresupuesto" />				
		</td>
	</tr>
	<tr>
		<td class="opcional"><bean:message bundle="headers"	key="app.header.observaciones" /></td>
		<td><html:textarea property="observaciones" rows="3" cols="100"/>
			<pragmatags:error property="observaciones" />
		</td>
	</tr>
	
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	
	<%-- Emerix --%>

	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.emerix"/>
		</td>
		
		<td>
			<html:text property="emerix" maxlength="20"/>
			<pragmatags:error property="emerix" />
		</td>
	</tr>

	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.cartera"/>
		</td>				
		<td class="opcional">
			<html:select property="idCartera">
				<html:options collection="carteras" property="value" labelProperty="label" />
			</html:select>
		</td>
	</tr>	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.variante"/>
		</td>
		
		<td>
			<html:text property="varianteEmerix" maxlength="20"/>
		</td>
	</tr>

	
</table>

<pragmatags:btnDynaLabel javascript="javascript:setHiddenValue();submitForm();" label="app.label.aceptar" />	
<pragmatags:btnDynaLabel action="/LlamadoConvocatoriaInventario" label="app.label.cancelar" />	

</html:form>

</body>