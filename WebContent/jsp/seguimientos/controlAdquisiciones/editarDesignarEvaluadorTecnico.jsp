<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<body>
<html:form action="/DesignarEvaluadorGuardarDatosGestion">

<table class="formulario">		
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.general" />
		</th>
	</tr>
	<tr></tr>

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.evaluador"/>
		</td>
		
		<td>
			<html:select property="idPersonaEvaluador">
				<html:options collection="evaluadores" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="idPersonaEvaluador" />
		</td>
	</tr>
		
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fecha"/>
		</td>
		<td>
			<pragmatags:calendar propertyName="fechaAsignacionEvaluador" top="255" left="250" />
			<pragmatags:error property="fechaAsignacionEvaluador" />
		</td>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</td>
		<td>
			<html:textarea property="descripcionAsignacionEvaluador" rows="3" cols="100"/>
			<pragmatags:error property="descripcionAsignacionEvaluador" />
		</td>
	</tr>
</table>

</html:form>
</body>
