<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<c:set var="headerKey" value="app.title.altaInstrumento" />
<c:if test="${!empty param.id}" >
	<c:set var="headerKey" value="app.title.edicionInstrumento" />
</c:if>

<title>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}" />
	</fmt:bundle>
</title>

<script type="text/javascript" src="js/libreria.js"></script>

<script language="javascript">								
	function guardar()
	{
		var validate = validateInstrumentosDefEditarDynaForm(document.InstrumentosDefEditarDynaForm);
		if (validate) {
			document.InstrumentosDefEditarDynaForm.submit();
		}	
	}
</script>

<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>

<html:form action="/InstrumentosDefGuardar" >

<html:hidden property="id"/>

<!-- FF / HARDCODED -->
<table class="formulario">	
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.identificador"/>
		</td>				
		<td>
	   		<html:text property="identificador" maxlength="20" size="20%"/>	
			<pragmatags:error property="identificador" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.denominacion"/>
		</td>		
		<td>
	   		<html:text property="denominacion" maxlength="50" size="95"/>	
			<pragmatags:error property="denominacion" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fuenteFinanciamiento"/>
		</td>		
		<td>
			<html:select property="idFuenteFinanciamiento">
				<html:options collection="fuentesFinanciamiento" property="value" labelProperty="label" />
			</html:select>
			<pragmatags:error property="idFuenteFinanciamiento" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.credito-subsidio"/>
		</td>		
		<td>	
			<html:select property="codigoTipo">
				<html:options collection="tipos" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="codigoTipo" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.modalidadBeneficio"/>
		</td>		
		<td>
			<html:textarea property="modalidad" rows="2" cols="100" />
			<pragmatags:error property="modalidad" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.limitesFinanciamiento"/>
		</td>		
		<td>
	   		<html:text property="monto" maxlength="10" size="15" style="text-align:right"/>	
			<pragmatags:error property="monto" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.proporcionApoyo"/>
		</td>		
		<td>
	   		<html:text property="proporcionApoyo" maxlength="6" size="15" style="text-align:right"/>	
			<pragmatags:error property="proporcionApoyo" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.periodoGracia" />
		</td>
		<td>
	   		<html:textarea property="periodoGracia" rows="3" cols="100" />	
 			<pragmatags:error property="periodoGracia" />
   		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.plazoEjecucion" />
		</td>
		<td>
	   		<html:textarea property="plazoEjecucion" rows="3" cols="100" />	
 			<pragmatags:error property="plazoEjecucion" />
   		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.plazoAmortizacion" />
		</td>
		<td>
	   		<html:textarea property="plazoAmortizacion" rows="3" cols="100" />	
 			<pragmatags:error property="plazoAmortizacion" />
   		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.tasaInteres" />
		</td>
		<td>
	   		<html:textarea property="tasaInteres" rows="3" cols="100"/>	
 			<pragmatags:error property="tasaInteres" />
   		</td>
	</tr>	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.garantias" />
		</td>
		<td>
	   		<html:textarea property="garantia" rows="3" cols="100" />	
 			<pragmatags:error property="garantia" />
   		</td>
	</tr>
	<tr rowspan="4">
		<td class="opcional" valign="middle">
			<bean:message bundle="labels" key="app.label.circuitoEvaluacion"/>			
		</td>
		<td>
			<html:checkbox property="permiteFinanciamientoBancario" />
			<bean:message bundle="labels" key="app.label.financiacionBancaria"/>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<html:checkbox property="permiteComision" />
			<bean:message bundle="labels" key="app.label.comision"/>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<html:checkbox property="permiteSecretaria" />
			<bean:message bundle="labels" key="app.label.secretariaPermanente"/>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<html:checkbox property="permiteAdjudicacion"/>
			<bean:message bundle="labels" key="app.label.adjudicacionDirecta"/>
		</td>
	</tr>	
	<tr>
		<td></td>
	</tr>	

	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.permiteAdquisicion"/>
		</td>
		<td>
			<html:radio property="permiteAdquisicion" value="true">
				<bean:message bundle="labels" key="app.label.si"/>
			</html:radio>
			<html:radio property="permiteAdquisicion" value="false">
				<bean:message bundle="labels" key="app.label.no"/>
			</html:radio>
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.multiplesJurisdicciones"/>
		</td>
		<td>
			<html:radio property="permiteMultipleJurisdiccion" value="true">
				<bean:message bundle="labels" key="app.label.si"/>
			</html:radio>
			<html:radio property="permiteMultipleJurisdiccion" value="false">
				<bean:message bundle="labels" key="app.label.no"/>
			</html:radio>
			<pragmatags:error property="permiteMultipleJurisdiccion" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.propiciar"/>
		</td>
		<td>
			<html:radio property="permitePropiciado" value="true">
				<bean:message bundle="labels" key="app.label.si"/>
			</html:radio>
			<html:radio property="permitePropiciado" value="false">
				<bean:message bundle="labels" key="app.label.no"/>
			</html:radio>
			<pragmatags:error property="permitePropiciado" />
		</td>
	</tr>	

	<c:if test="${!empty param.id}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.estado"/>
			</td>		
			<td class="opcional">
				<html:select property="activo">
					<html:options collection="estadosEntidad" property="value" labelProperty="label" />
				</html:select> 
				<pragmatags:error property="activo" />
			</td>
		</tr>
	</c:if>	
	<tr>
		<td class="opcional">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</td>
		<td>
	   		<html:textarea property="observacion" rows="3" cols="100" />	
 			<pragmatags:error property="observacion" />
   		</td>
	</tr>
	
</table>

<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/InstrumentosDefInventario"/>
</html:form>