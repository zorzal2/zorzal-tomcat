<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<script type="text/javascript">
function round2DecimalsToStrig(n) {
	if(n==null) return "--";
	//Redondeo
	var rounded = Math.round(n*100.0)/100.0;
	//Trunco el error
	return (''+rounded).match(/[0-9]*(?:\.[0-9][0-9]?)?/)[0];
}
function extractValue(inputName) {
	var list = document.getElementsByName(inputName);
	if(list==null) return 0;
	else {
		if(list.length==0 || list.length>1) return 0;
		else {
			if(list[0].value=='') return 0;
			var value = parseFloat(list[0].value);
			if(isNaN(value)) return null;
			else return value;
		}
	}
}


var namesFontar = [
		'montoFontarBienCapital',
		'montoFontarRrhh',
		'montoFontarConsultoriaServicio',
		'montoFontarMaterialInsumo',
		'montoFontarOtro'
	]
		
var namesContraparte = [
		'montoContraparteBienCapital',
		'montoContraparteRrhh',
		'montoContraparteConsultoriaServicio',
		'montoContraparteMaterialInsumo',
		'montoContraparteOtro'
	];
function __calcNow() {
	var totalFontar = 0;
	for(var i = 0; i<namesFontar.length; i++) {
		var amount = extractValue(namesFontar[i]);
		if(amount==null) {
			totalFontar=null;
			break;
		} else {
			totalFontar += amount;
		}
	}
	var totalContraparte = 0;
	for(var i = 0; i<namesContraparte.length; i++) {
		var amount = extractValue(namesContraparte[i]);
		if(amount==null) {
			totalContraparte=null;
			break;
		} else {
			totalContraparte += amount;
		}
	}
	var txtTotalRendicionesFontar = document.getElementById('totalRendicionesFontar')
	if(txtTotalRendicionesFontar) txtTotalRendicionesFontar.innerHTML = round2DecimalsToStrig(totalFontar);
	var txtTotalRendicionesContraparte = document.getElementById('totalRendicionesContraparte');
	if(txtTotalRendicionesContraparte) txtTotalRendicionesContraparte.innerHTML = round2DecimalsToStrig(totalContraparte);
}
function recalcTotal() {
	setTimeout(__calcNow, 0);
}
if(!window.onload) {
	window.onload = recalcTotal;
} else {
	var previowsOnload = window.onload;
	window.onload = function() {previowsOnload(); recalcTotal();}
}
</script>

<h3>
	<bean:message bundle="titles" key="app.title.completarDatosIniciales"/>
</h3> 

<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

<html:form action="/CompletarDatosInicialesGuardar">
<table class="formulario">		

	<tr>	
		<th colspan="5" class="titulo">
			<bean:message bundle="headers" key="app.header.proyectoHistorico.datosAdjudicacion"/>
		</th>
	</tr>	
	
	<tr>
		<td class="obligatorio" width="20%">
			<bean:message bundle="labels" key="app.label.proyectoHistorico.recomendacionFinal"/>
		</td>
		<td>
			<html:select property="recomendacionFinal" >
				<html:options collection="recomendacionesFinales" property="value" labelProperty="label" />
			</html:select> 
			<pragmatags:error property="recomendacionFinal"/>
		</td>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.proyectoHistorico.fechaResolucion"/>
		</td>
		<td>
			<pragmatags:calendar propertyName="fechaResolucion" top="255" left="250" />
			<pragmatags:error property="fechaResolucion" />
		</td>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.proyectoHistorico.codigoResolucion"/>
		</td>
		<td>
			<html:text property="codigoResolucion" maxlength="60" />
			<pragmatags:error property="codigoResolucion"/>
		</td>
	</tr>	
	<html:hidden property="aplicaCargaAlicuotaCF" />
	<c:if test="${permiteAlicuotaAdjudicada}">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.proyectoHistorico.alicuotaAdjudicada"/>
		</td>
		<td>
			<html:text property="porcentajeAlicuotaAdjudicada" maxlength="60" />
			<pragmatags:error property="porcentajeAlicuotaAdjudicada"/>
		</td>
	</tr>	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.proyectoHistorico.alicuotaSolicitada"/>
		</td>
		<td>
			<html:text property="porcentajeAlicuotaSolicitada" maxlength="60" />
			<pragmatags:error property="porcentajeAlicuotaSolicitada"/>
		</td>
	</tr>	
	</c:if>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.proyectoHistorico.fechaFirmaDeContrato"/>
		</td>
		<td>
			<pragmatags:calendar propertyName="fechaFirmaDeContrato" top="255" left="250" />
			<pragmatags:error property="fechaFirmaDeContrato" />
		</td>
	</tr>
</table>

<br>
<!-- Rendiciones historicas para proyectos que no son de CF -->
<c:if test="${not aplicaCargaDeAlicuotaCF}">
	<table class="formulario" onkeyup="recalcTotal()">
		<tr>	
			<th colspan="5" class="titulo">
				<bean:message bundle="headers" key="app.header.proyectoHistorico.rendicionesHistoricas"/>
			</th>
		</tr>	
		
		<tr>
			<td style="width:20%"></td>
				<td class="obligatorio" style="width:12%" align="center" nowrap="nowrap">
					<bean:message bundle="labels" key="app.label.montoParte"/>
				</td>
			<td>
			</td>
			<td class="obligatorio" style="width:12%" align="center" nowrap="nowrap">
				<bean:message bundle="labels" key="app.label.montoContraparte"/>
			</td>
			<td style="width:100%">
			</td>
		</tr>		
		
		<tr>
			<td class="obligatorio" >
				<bean:message bundle="labels" key="app.label.proyectoHistorico.bienesCapital"/>
			</td>
			<td align="center" >
				<html:text property="montoFontarBienCapital" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoFontarBienCapital"/>
			</td>
			<td align="center">
				<html:text property="montoContraparteBienCapital" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoContraparteBienCapital"/>
			</td>
		</tr>		
	
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.proyectoHistorico.recursosHumanos"/>
			</td>
			<td align="center">
				<html:text property="montoFontarRrhh" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoFontarRrhh"/>
			</td>
			<td align="center">
				<html:text property="montoContraparteRrhh" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoContraparteRrhh"/>
			</td>
		</tr>		
		
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.proyectoHistorico.consultoriaServicio"/>
			</td>
			<td align="center">
				<html:text property="montoFontarConsultoriaServicio" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoFontarConsultoriaServicio"/>
			</td>
			<td align="center">
				<html:text property="montoContraparteConsultoriaServicio" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoContraparteConsultoriaServicio"/>
			</td>
		</tr>		
	
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.proyectoHistorico.materialesInsumos"/>
			</td>
			<td align="center">
				<html:text property="montoFontarMaterialInsumo" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoFontarMaterialInsumo"/>
			</td>
			<td align="center">
				<html:text property="montoContraparteMaterialInsumo" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoContraparteMaterialInsumo"/>
			</td>
		</tr>			
	
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.proyectoHistorico.otros"/>
			</td>
			<td align="center">
				<html:text property="montoFontarOtro" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoFontarOtro"/>
			</td>
			<td align="center">
				<html:text property="montoContraparteOtro" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoContraparteOtro"/>
			</td>
		</tr>	
		<!-- Totales -->
		<tr><td class="separador" colspan="5"></td></tr>
		<tr class="total">
			<td>
				<bean:message bundle="labels" key="app.label.total"/><br>
			</td>
			<td align="center" id="totalRendicionesFontar">
				0
			</td>
			<td nowrap="nowrap" />
			<td align="center" id="totalRendicionesContraparte">
				0
			</td>
			<td nowrap="nowrap" />
		</tr>	
		
	</table>
</c:if>
<c:if test="${aplicaCargaDeAlicuotaCF}">
	<table class="formulario" onkeyup="recalcTotal()">
		<tr>	
			<th colspan="3" class="titulo">
				<bean:message bundle="headers" key="app.header.proyectoHistorico.rendicionesHistoricas"/>
			</th>
		</tr>	
		
		<tr>
			<td style="width:20%"></td>
			<td class="obligatorio" style="width:12%" align="center" nowrap="nowrap">
				<bean:message bundle="labels" key="app.label.montoTotal"/>
			</td>
			<td style="width:100%">
			</td>
		</tr>		
		
		<tr>
			<td class="obligatorio" >
				<bean:message bundle="labels" key="app.label.proyectoHistorico.bienesCapital"/>
			</td>
			<td align="center">
				<html:text property="montoContraparteBienCapital" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoContraparteBienCapital"/>
			</td>
		</tr>		
	
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.proyectoHistorico.recursosHumanos"/>
			</td>
			<td align="center">
				<html:text property="montoContraparteRrhh" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoContraparteRrhh"/>
			</td>
		</tr>		
		
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.proyectoHistorico.consultoriaServicio"/>
			</td>
			<td align="center">
				<html:text property="montoContraparteConsultoriaServicio" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoContraparteConsultoriaServicio"/>
			</td>
		</tr>		
	
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.proyectoHistorico.materialesInsumos"/>
			</td>
			<td align="center">
				<html:text property="montoContraparteMaterialInsumo" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoContraparteMaterialInsumo"/>
			</td>
		</tr>			
	
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.proyectoHistorico.otros"/>
			</td>
			<td align="center">
				<html:text property="montoContraparteOtro" maxlength="60" size="10%" style="text-align:right"/>
			</td>
			<td nowrap="nowrap">
				<pragmatags:error property="montoContraparteOtro"/>
			</td>
		</tr>	
		<!-- Totales -->
		<tr><td class="separador" colspan="5"></td></tr>
		<tr class="total">
			<td>
				<bean:message bundle="labels" key="app.label.total"/><br>
			</td>
			<td align="center" id="totalRendicionesContraparte">
				0
			</td>
			<td nowrap="nowrap" />
		</tr>	
	</table>
</c:if>
<br>
<table class="formulario">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</td>
		<td>
			<html:textarea property="observacion" rows="3" cols="100" />
			<pragmatags:error property="observacion" />
		</td>
	</tr>	
</table>
</html:form>

<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
