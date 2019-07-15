<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>

<h3>
	<bean:message bundle="titles" key="app.title.desembolso.pagar"/>
</h3>

<html:form action="/ProyectoDesembolsoRegistrarPago">
<html:hidden property="id"/>
<table class="formulario">	
	<tr>
		<td class="obligatorio" width="20%">
			<bean:message bundle="labels" key="app.label.desembolso.montoDesembolsado"/>
		</td>		
		<td width="20%">
	   		<html:text property="monto"  maxlength="10" size="20" style="text-align:right"/>	
			<pragmatags:error property="monto" />
		</td>
		<td>&nbsp;
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.desembolso.fechaPago"/>
		</td>		
		<td>
			<pragmatags:calendar propertyName="fechaPago" top="255" left="250"/>
			<pragmatags:error property="fechaPago" />
		</td>
	</tr>
</table>
<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/jsp/util/closeAndParentReload.jsp"/>
</html:form>