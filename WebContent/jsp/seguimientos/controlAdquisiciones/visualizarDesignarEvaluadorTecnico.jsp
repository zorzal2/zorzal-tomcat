<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<body>
<html:form action="/DesignarEvaluadorTecnicoGuardar">

<table class="formulario">
	<tr>
		<td>
			<pragmatags:btnDynaLabel action="/DesignarEvaluadorEditarDatosGestion?id=${id}" label="app.label.editar" />
		</td>
	</tr>
	 
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.general" />
		</th>
	</tr>
	<tr>
		<td width="10%" class="obligatorio">
			<bean:message bundle="labels" key="app.label.evaluador"/>:&nbsp;
		</td>
		<td>
			<c:if test="${not empty procedimiento.evaluador}">
			<bean:write name="procedimiento" property="evaluador.nombre"/>
			</c:if>
		</td>			
	</tr>		
	<tr>
		<td width="10%" class="obligatorio">
			<bean:message bundle="labels" key="app.label.fecha"/>:&nbsp;
		</td>
		<td>
			<bean:write name="procedimiento" property="fechaAsignacionEvaluador" formatKey="app.date.largeFormat" />
		</td>
	</tr>
	<tr>
		<td width="10%" class="obligatorio">
			<bean:message bundle="labels" key="app.label.observaciones"/>:&nbsp;
		</td>
		<td>
			<bean:write name="procedimiento" property="descripcionAsignacionEvaluador"/>
		</td>
	</tr>
</table>
</html:form>

</body>
