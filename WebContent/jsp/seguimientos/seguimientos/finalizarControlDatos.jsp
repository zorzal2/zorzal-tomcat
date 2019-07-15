<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>

<html:form action="/FinalizarControlSeguimientoGuardar">
<html:hidden property="idEvaluacion"/>	
<html:hidden property="idProyecto"/>	
<html:hidden property="idSeguimiento"/>	
<table class="formulario">
	<tr><td colspan="2" class="obligatorio">
		<bean:message bundle="headers" key="app.header.evaluacionesConfirmadas"/>
	</td></tr>

	<tr><td colspan="2">
		<display-el:table name="evaluacionesList" class="inventario">
			<display-el:setProperty name="basic.show.header" value="false" /> 
		
			<display-el:caption>	
				<tr>
				    <th><bean:message bundle="headers" key="app.header.nroEvaluacion"/></th>	
				    <th><bean:message bundle="headers" key="app.header.tipoEvaluacion"/></th>
			    	<th><bean:message bundle="headers" key="app.header.evaluadores"/></th>
				    <th><bean:message bundle="headers" key="app.header.resultadoEvaluacion"/></th>
		        </tr>
			</display-el:caption>	
	
			<display-el:column media="html" property="idEvaluacion" escapeXml="true"/>
			<display-el:column media="html" property="tipo" escapeXml="true"/>
			<display-el:column media="html" property="evaluadores"/>
			<display-el:column media="html" property="resultado" escapeXml="true"/>
		</display-el:table>
	</td></tr>
	<!--  Resultado -->
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fecha"/>
		</td>		
		<td align="left">
			<pragmatags:calendar propertyName="fechaEvaluacion" top="0" left="0"/>	
			<pragmatags:error property="fechaEvaluacion" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.resultado"/>
		</td>		
		<td align="left">
			<html:select property="recomendacion">
				<html:options collection="recomendaciones" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="recomendacion" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.fundamentacion"/>
		</td>		
		<td align="left">
	   		<html:textarea property="fundamentacion" rows="4" cols="100" />
		</td>
	</tr>
</table>
</html:form>