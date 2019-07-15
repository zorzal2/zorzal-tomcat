<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<pragmatags:tabla 
	toolbar="no" 
	pagesize="1000"
	toolbaroptions="" 
	columns="item,descripcion,etapa,rubro.nombre,pacItem.montoFontar,fecha,proveedor,moneda,montoBid,resultadoBid.descripcion" 
	align="left,left,left,left,right,center,left,left,right,left"
	actions="linkEditarResultadoBid"
	decorator="com.fontar.web.decorator.seguimientos.controlAdquisicion.ItmesCargarResultadoWrapper"
	requestURI="CargarResultadoBid.do" 
	collection="listaItems" 
	entity=""/>

<br>

<body>
<html:form action="/CargarResultadoBidGuardar">

<table class="formulario">		
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.general" />
		</th>
	</tr>

	<tr></tr>
		
	<tr>
		<td width="25%" class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaResultadoFontar"/>
		</td>
		<td>
			<bean:write name="procedimiento" property="fechaResultadoFontar" formatKey="app.date.largeFormat" />
		</td>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</td>
		<td>
			<bean:write name="procedimiento" property="observacionResultadoFontar"/>
		</td>
	</tr>

	<p>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaResultadoBid"/>
		</td>
		<td>
			<pragmatags:calendar propertyName="fechaResultadoBid" top="255" left="250" />
			<pragmatags:error property="fechaResultadoBid" />
		</td>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.resultadoBid"/>
		</td>
		<td>
			<html:textarea property="observacionResultadoBid" rows="3" cols="100"/>
			<pragmatags:error property="observacionResultadoBid" />
		</td>
	</tr>
</table>

</html:form>
</body>
