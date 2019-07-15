<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body>
<html:form action="/AnularNotificacionGuardar">

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

		<td width="11%">
			<bean:message bundle="labels" key="app.label.tipo"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="tipoNotificacion.descripcion" /></span>
		</td>

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

		<td width="11%">
			<bean:message bundle="labels" key="app.label.fechaEnvio"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="fechaEnvio" /></span>
		</td>

		<td width="11%">
			<bean:message bundle="labels" key="app.label.descripcion"/>:&nbsp;
		</td>
		<td class="opcional">
			<span><bean:write name="notificacion" property="descripcion" /></span>
		</td>
	</tr>
</table>

<br>

<table class="formulario">		
	<tr>		
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.descripcionMotivo" />
		</th>	
	</tr>
	
	<tr>
		<td class="obligatorio" width="15%">
			<bean:message bundle="labels" key="app.label.motivo" />
		</td>
		<td>
	   		<html:textarea property="observacion" rows="3" cols="100" />
			<pragmatags:error property="observacion" />
		</td>
	</tr>
</table>

</html:form>
</body>
