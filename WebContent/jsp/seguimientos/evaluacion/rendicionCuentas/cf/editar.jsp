<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<script type="text/javascript">
	function actualizarValores() {
		window.opener.document.location.reload();
		submitForm();
	}
</script>

<body>
<html:form action="/GuardarEdicionMontosRendicionEvaluacionSeguimientoCF?id=${id}">
<html:hidden property="id" />
<h3>
	<bean:message bundle="titles" key="app.title.evaluacionSeguimiento.editarMontos"/>
</h3>	

<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.montoAprobado"/>
		</td>
		<td>
			<html:text property="montoTotalEvaluacion" maxlength="20" size="15%" style="text-align:right"/>
			<pragmatags:error property="montoTotalEvaluacion" />
		</td>	
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</td>
		<td>
			<html:textarea property="observaciones" rows="3" cols="80"/>
			<pragmatags:error property="observaciones" />
		</td>
	</tr>
</table>

<br>
<pragmatags:btnOkCancel okJavascript="actualizarValores()"  cancelJavascript="cerrarPopUp()"/>	
	
</html:form>
</body>
