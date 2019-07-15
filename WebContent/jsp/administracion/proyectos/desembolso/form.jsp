<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 

<html:hidden property="idProyecto"/>
<table class="formulario">	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.desembolso.concepto"/>
		</td>		
		<td>
	   		<html:text property="concepto" maxlength="20" size="35%"/>	
			<pragmatags:error property="concepto" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.desembolso.objetivo"/>
		</td>		
		<td>
	   		<html:textarea property="objetivo" rows="8" cols="100" />	
			<pragmatags:error property="objetivo" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.desembolso.montoOriginal"/>
		</td>		
		<td>
	   		<html:text property="montoOriginal"  maxlength="20" size="15%" style="text-align: right"/>	
			<pragmatags:error property="montoOriginal" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.desembolso.plazo"/>
		</td>		
		<td>
	   		<html:text property="plazo"  maxlength="20" size="15%" style="text-align: right"/>	
			<pragmatags:error property="plazo" />
		</td>
	</tr>
</table>
