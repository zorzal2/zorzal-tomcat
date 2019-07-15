<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<table class="formulario">
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
	<tr>
		<th colspan="5" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.nroProyecto"/>
		</td>		
		<td class="opcional">
			<bean:write name="ideaProyectoVisualizar" property="codigoIdeaProyecto"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaIngreso"/>
		</td>		
		<td>
			<bean:write name="ideaProyectoVisualizar" property="fechaIngreso"/>
		</td>
	</tr>		
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.entidadBeneficiaria"/>
		</td>		
		<td class="opcional">
			<bean:write name="ideaProyectoVisualizar" property="entidadBeneficiaria"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.titulo"/>
		</td>		

		<td class="opcional">
			<bean:write name="ideaProyectoVisualizar" property="titulo"/>
		</td>
	</tr>
		
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.jurisdiccion"/>
		</td>		
		<td>
			<bean:write name="ideaProyectoVisualizar" property="jurisdiccion"/>
		</td>
	</tr>		

	<tr>
		<td valign="top" style="padding-top: 6px">
			<bean:message bundle="labels" key="app.label.responsableLegal"/>
		</td>		
		<td class="opcional" valign="top">
			<pragmatags:persona bean="ideaProyectoVisualizar.personaLegal" />
		</td>
	</tr>
	<tr>
		<td valign="top" style="padding-top: 6px">
			<bean:message bundle="labels" key="app.label.directorProyecto"/>
		</td>		
		<td class="opcional">
			<pragmatags:persona bean="ideaProyectoVisualizar.personaDirector" />
		</td>
	</tr>
	<tr>
		<td valign="top" style="padding-top: 6px">
			<bean:message bundle="labels" key="app.label.representante"/>
		</td>		
		<td class="opcional">
			<pragmatags:persona bean="ideaProyectoVisualizar.personaRepresentante" />
		</td>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.resumen"/>
		</td>		
		<td class="opcional">
			<bean:write name="ideaProyectoVisualizar" property="resumen"/>
		</td>
	</tr>	

 	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.tipoProyecto"/>
		</td>		
		<td>
			<bean:write name="ideaProyectoVisualizar" property="tipoProyecto"/>
		</td>
	</tr>		

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.instrumentoSolicitado"/>
		</td>		
		<td>
			<bean:write name="ideaProyectoVisualizar" property="instrumentoSolicitado"/>
		</td>
	</tr>		

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.recomendacionInstrumento"/>
		</td>		
		<td>
			<bean:write name="ideaProyectoVisualizar" property="instrumentoRecomendado"/>
		</td>
	</tr>		

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.montoTotal"/>
		</td>		
		<td>
			<bean:write name="ideaProyectoVisualizar" property="montoTotalAsString"/>
		</td>
	</tr>		

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.montoSolicitado"/>
		</td>		
		<td>
			<bean:write name="ideaProyectoVisualizar" property="montoSolicitadoAsString"/>
		</td>
	</tr>		

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.duracionMeses"/>
		</td>		
		<td>
			<bean:write name="ideaProyectoVisualizar" property="duracion"/>
		</td>
	</tr>		
		
		
</table>

<br>
<br>

<table class="formulario">
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>	
	<tr>	
		<th colspan="5" class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones"/>
		</th>		
	</tr>
	
	<tr>
		<td colspan="5" class="opcional">
			<bean:write name="ideaProyectoVisualizar" property="observaciones"/>
		</td>
	</tr>
</table>
