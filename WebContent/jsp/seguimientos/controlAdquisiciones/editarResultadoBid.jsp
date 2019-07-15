<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<script type="text/javascript">
	function actualizarValores() {
		window.opener.document.location.reload();
		submitForm();
	}
	var resultadosSinMonto = [
	<%
		com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoProcedimiento[] enumConstants = (com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoProcedimiento[])request.getAttribute("resultadosEnum");
		for(int i = 0; i<enumConstants.length; i++) {
			if(enumConstants[i].esNegativo())out.write("'"+enumConstants[i].getName()+"',");
		}
	%>
		"dummy"];
	
	function esResultadoQueNoLlevaMonto(value) {
		for(var i = 0; i<resultadosSinMonto.length; i++) {
			if(resultadosSinMonto[i]==value) return true; 
		}
		return false;
	}

	function doResultadoChange(combo) {
		if(esResultadoQueNoLlevaMonto(combo.value)) {
			document.getElementById('trProveedor').style.display='none'
			document.getElementById('trIdMoneda').style.display='none'
			document.getElementById('trMonto').style.display='none'
		} else {
			document.getElementById('trProveedor').style.display=''
			document.getElementById('trIdMoneda').style.display=''
			document.getElementById('trMonto').style.display=''
		}
	}
	window.onload=function() {
		doResultadoChange(document.getElementsByName('resultadoBid')[0])
	}
</script>

<body>
<html:form action="/GuardarResultadoBid?id=${id}">
<html:hidden property="id" />

<h3>
	<bean:message bundle="titles" key="app.title.procedimientos.editarResultadoBid"/>
</h3>	

<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr id="trProveedor">
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.proveedor"/>
		</td>
		<td>
			<html:text property="proveedor" maxlength="200" size="15%"/>
			<pragmatags:error property="proveedor" />
		</td>	
	</tr>		
	<tr id="trIdMoneda">
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.moneda"/>
		</td>
		<td>
			<html:select property="idMoneda">
				<html:options collection="monedas" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="idMoneda" />
		</td>
	</tr>
	<tr id="trMonto">
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.monto_SinMoneda"/>
		</td>
		<td>
			<html:text property="montoBid" maxlength="20" size="15%" style="text-align:right"/>
			<pragmatags:error property="montoBid" />
		</td>	
	</tr>		
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.resultado"/>
		</td>
		<td>
			<html:select onchange="doResultadoChange(this)" property="resultadoBid">
				<html:options collection="resultados" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="resultadoBid" />
		</td>
	</tr>
</table>

<br>
<pragmatags:btnOkCancel okJavascript="actualizarValores()"  cancelJavascript="cerrarPopUp()"/>	
	
</html:form>
</body>
