<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 

<table class="formulario">	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.cotizaciones.moneda"/>
		</td>		
		<td>
			<html:select property="idMoneda">
				<html:options collection="monedas" property="value" labelProperty="label" />
			</html:select> 
			<pragmatags:error property="moneda" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.cotizaciones.fecha"/>
		</td>		
		<td>
			<pragmatags:calendar propertyName="fecha" top="0" left="0" />
			<pragmatags:error property="fecha" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.cotizaciones.monto"/>
		</td>		
		<td>
	   		<html:text property="monto" style="text-align:right" maxlength="10" size="10"/>	
			<pragmatags:error property="monto" />
		</td>
	</tr>
</table>
<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/CotizacionInventario"/>