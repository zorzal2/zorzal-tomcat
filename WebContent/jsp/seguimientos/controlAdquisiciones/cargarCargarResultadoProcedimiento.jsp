<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<pragmatags:tabla 
	toolbar="no"
	pagesize="1000" 
	toolbaroptions="" 
	columns="item,descripcion,etapa,rubro.nombre,pacItem.montoFontar,fecha,proveedor,moneda,montoFontar,resultadoFontar.descripcion" 
	align="left,left,left,left,right,center,left,left,right,left"
	actions="linkEditarResultadoFontar"
	decorator="com.fontar.web.decorator.seguimientos.controlAdquisicion.ItmesCargarResultadoWrapper"
	requestURI="CargarResultadoProcedimiento.do" 
	collection="listaItems" 
	entity=""/>

<br>

<body>
<html:form action="/CargarResultadoProcedimientoGuardar">

<table class="formulario">		
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.general" />
		</th>
	</tr>

	<tr></tr>
		
	<tr>
		<td width="25%" class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaFundamentacion"/>
		</td>
		<td>
			<bean:write name="procedimiento" property="fechaFundamentacion" formatKey="app.date.largeFormat" />
		</td>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fundamentacion"/>
		</td>
		<td>
			<bean:write name="procedimiento" property="descripcionFundamentacion"/>
		</td>
	</tr>

	<p>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaResultadoFontar"/>
		</td>
		<td>
			<pragmatags:calendar propertyName="fechaResultadoFontar" top="255" left="250" />
			<pragmatags:error property="fechaResultadoFontar" />
		</td>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</td>
		<td>
			<html:textarea property="observacionResultadoFontar" rows="3" cols="100"/>
			<pragmatags:error property="observacionResultadoFontar" />
		</td>
	</tr>
</table>

</html:form>
</body>
