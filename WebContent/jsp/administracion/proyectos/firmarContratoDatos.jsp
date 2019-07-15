<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<pragma:form action="/FirmarContratoGuardar">
<html:hidden property="idProyecto"/>

<input type="hidden" id="selectionEvent" name="selectionEvent"/>

<table class="form">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.montoAdjudicacion"/>
		</td>		
		<td align="left" class="opcional">
			<span class="opcional">
				<c:out value="${adjudicacion}" />
			</span>
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.responsableLegal"/>
		</td>		
		<td class="opcional">
			<pragmatags:selectorInventario entidad="PersonaLegal"/>
			<pragmatags:error property="idPersonaLegal" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaFirma"/>
		</td>		
		<td class="opcional">
			<pragmatags:calendar propertyName="fechaFirma" top="255" left="250" />
			<pragmatags:error property="fechaFirma" />			
		</td>
	</tr>
</table>

<table class="formulario">
	<tr>
		<th class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</th>
	</tr>
	<tr>
		<td class="opcional">
	   		<html:textarea property="observaciones" rows="4" cols="100" />	
	   		<pragmatags:error property="observaciones" />
   		</td>
	</tr>
</table>

</pragma:form>