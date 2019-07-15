<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body>
<html:form action="/RecibirAcuseNotificacionGuardar">

<table class="formulario">		
	<tr>		
		<th colspan="8" class="titulo">
			<bean:message bundle="headers" key="app.header.datos" />
		</th>	
	</tr>
	
	<tr>
		<td width="11%">
			<bean:message bundle="labels" key="app.label.nroNotificacion"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="id" /></span>
		</td>
	<tr/>

	<tr>
		<td width="11%">
			<bean:message bundle="labels" key="app.label.nroProyecto"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="proyectoRaiz.codigo" /></span>
		</td>
	</tr>
	
	<tr>
		<td width="11%">
			<bean:message bundle="labels" key="app.label.tipo"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="tipoNotificacion.descripcion" /></span>
		</td>
	<tr/>

	<tr>
		<td width="11%">
			<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="estado.descripcion" /></span>
		</td>
	</tr>
	
	<tr>
		<td width="11%">
			<bean:message bundle="labels" key="app.label.fechaCreacion"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="fechaCreacion" /></span>
		</td>
	<tr/>

	<tr>
		<td width="11%">
			<bean:message bundle="labels" key="app.label.fechaEnvio"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="fechaEnvio" /></span>
		</td>
	<tr/>

	<tr>
		<td width="11%">
			<bean:message bundle="labels" key="app.label.fechaAcuse"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="fechaAcuse" /></span>
		</td>
	<tr/>

	<tr>
		<td width="11%">
			<bean:message bundle="labels" key="app.label.descripcion"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="descripcion" /></span>
		</td>
	</tr>
	
	<tr>
		<td width="11%">
			<bean:message bundle="labels" key="app.label.observaciones"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="observacion" /></span>
		</td>
	</tr>
	


	
</table>
</html:form>
</body>
