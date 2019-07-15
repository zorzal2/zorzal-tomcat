<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>

<div>
<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.general" />
		</th>
	</tr>
		<tr>
		<td width="10%">
			<bean:message bundle="labels" key="app.label.nroProyecto"/>:&nbsp;
		</td>
		<td>
			<c:if test="${not empty procedimiento.proyecto}">
			<bean:write name="procedimiento" property="proyecto.codigo"/>
			</c:if>
		</td>			
	</tr>
	<tr>
		<td width="10%">
			<bean:message bundle="labels" key="app.label.nroProcedimiento"/>:&nbsp;
		</td>
		<td>
			<bean:write name="procedimiento" property="codigo"/>
		</td>			
	</tr>
	<tr>
		<td width="10%">
			<bean:message bundle="labels" key="app.label.tipoAdquisicion"/>:&nbsp;
		</td>
		<td>
			<bean:write name="procedimiento" property="tipoAdquisicion.descripcion"/>
		</td>			
	</tr>
	<tr>
		<td width="10%">
			<bean:message bundle="labels" key="app.label.estado"/>:&nbsp;
		</td>
		<td>
			<bean:write name="procedimiento" property="estado.descripcion"/>
		</td>			
	</tr>
	<tr>
		<td width="10%">
			<bean:message bundle="labels" key="app.label.evaluador"/>:&nbsp;
		</td>
		<td>
			<c:if test="${not empty procedimiento.evaluador}">
			<bean:write name="procedimiento" property="evaluador.nombre"/>
			</c:if>
		</td>			
	</tr>
	<tr>
		<td width="10%">
			<bean:message bundle="labels" key="app.label.fecha"/>:&nbsp;
		</td>
		<td>
			<bean:write name="procedimiento" property="fechaRemisionUffa" formatKey="app.date.largeFormat"/>
		</td>
	</tr>
		<tr>
		<td width="10%">
			<bean:message bundle="labels" key="app.label.fechaRecepcion"/>:&nbsp;
		</td>
		<td>
			<c:if test="${not empty procedimiento.fechaRecepcion}">
			<bean:write name="procedimiento" property="fechaRecepcion" formatKey="app.date.largeFormat" />
			</c:if>
		</td>			
	</tr>
		<tr>
		<td width="10%">
			<bean:message bundle="labels" key="app.label.fechaFundamentacion"/>:&nbsp;
		</td>
		<td>
			<bean:write name="procedimiento" property="fechaFundamentacion" formatKey="app.date.largeFormat"/>
		</td>
	</tr>
	<tr>
		<td width="10%">
			<bean:message bundle="labels" key="app.label.fundamentacion"/>:&nbsp;
		</td>
		<td>
			<bean:write name="procedimiento" property="descripcionFundamentacion"/>
		</td>
	</tr>
	<tr>
		<td width="10%">
			<bean:message bundle="labels" key="app.label.observaciones"/>:&nbsp;
		</td>
		<td>
			<bean:write name="procedimiento" property="observacionRemisionUffa"/>
		</td>
	</tr>
</table>

<br>

<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.items" />
		</th>
	</tr>
	<toolbar:table model="list" requestURI="/ItemsProcedimientoInventario.do" />	
</table>


<br><br><br>
<br><br><br>

<table class="formulario">
	<tr>
		<td width="70%"></td>
		<td align="center">
			<bean:message bundle="labels" key="app.label.firma"/>
		</td>
	</tr>
</table>
</div>

