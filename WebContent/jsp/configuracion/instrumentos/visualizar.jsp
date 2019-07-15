<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.visualizarInstrumento"/></h3> 


	<table class="formulario">	
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="headers" key="app.header.general" />
			</th>
		</tr>
		<tr>
			<td width="20%" class="obligatorio" >
				<bean:message bundle="labels" key="app.label.identificador"/>
			</td>				
			<td>
		   		<bean:write name="instrumento" property="identificador"/>	
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.denominacion"/>
			</td>		
			<td>
		   		<bean:write name="instrumento" property="fuenteFinanciamiento.denominacion"/>
		   	</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.credito-subsidio"/>
			</td>		
			<td>	
				<bean:write name="instrumento" property="codigoTipo"/>		
			</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.modalidadBeneficio"/>
			</td>		
			<td>
				<bean:write name="instrumento" property="modalidad"/>		
			</td>
		</tr>
		<tr>
		<td class="opcional">
				<bean:message bundle="labels" key="app.label.limitesFinanciamiento"/>
			</td>		
			<td>
				<bean:write name="instrumento" property="monto"/>		
			</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.proporcionApoyo"/>
			</td>		
			<td>
				<bean:write name="instrumento" property="proporcionApoyo"/>	
			</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.periodoGracia" />
			</td>
			<td>
				<bean:write name="instrumento" property="periodoGracia"/>	
	   		</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.plazoEjecucion" />
			</td>
			<td>
				<bean:write name="instrumento" property="plazoEjecucion"/>	
	   		</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.plazoAmortizacion" />
			</td>
			<td>
				<bean:write name="instrumento" property="plazoAmortizacion"/>	
	   		</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.tasaInteres" />
			</td>
			<td>
				<bean:write name="instrumento" property="tasaInteres"/>	
	   		</td>
		</tr>	
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.garantias" />
			</td>
			<td>
				<bean:write name="instrumento" property="garantia"/>	
	   		</td>
		</tr>
		<tr rowspan="4">
			<td class="opcional" valign="middle">
				<bean:message bundle="labels" key="app.label.circuitoEvaluacion"/>			
			</td>
			<td>
				<bean:message bundle="labels" key="app.label.financiacionBancaria"/>
				<bean:write name="instrumento" property="permiteFinanciamientoBancario"/>	
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<bean:message bundle="labels" key="app.label.comision"/>
				<bean:write name="instrumento" property="permiteComision"/>			
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<bean:message bundle="labels" key="app.label.secretariaPermanente"/>
				<bean:write name="instrumento" property="permiteSecretaria"/>			
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<bean:message bundle="labels" key="app.label.adjudicacionDirecta"/>
				<bean:write name="instrumento" property="permiteAdjudicacion"/>					
			</td>
		</tr>	
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.permiteAdquisicion"/>
			</td>
			<td>
				<bean:write name="instrumento" property="permiteAdquisicion"/>							
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.multiplesJurisdicciones"/>
			</td>
			<td>
				<bean:write name="instrumento" property="permiteMultipleJurisdiccion"/>							
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.propiciar"/>
			</td>
			<td>
				<bean:write name="instrumento" property="permitePropiciado"/>		
			</td>
		</tr>	
		<tr>
			<td class="opcional">
				<bean:message bundle="headers" key="app.header.observaciones" />
			</td>
			<td>
				<bean:write name="instrumento" property="observacion"/>
	   		</td>
		</tr>
		
	</table>
	<br/>
	<!-- Volver al inventario -->
	<pragmatags:btnCerrar/>	
	
</div>
