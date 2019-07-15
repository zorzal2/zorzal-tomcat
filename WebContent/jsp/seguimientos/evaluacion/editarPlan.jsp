<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c"%>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters"	prefix="rapidFilters"%>

<script type="text/javascript">
function actualizarCampos() {
	submitForm();
	window.setTimeout("", 1000);
	window.opener.location.reload(); 
}

</script>
<body onload="checkVariableClose('windowClose');">
<html:form action="/ObservacionGuardar">
<html:hidden property="id"/>	
<html:hidden property="esEtapa"/>	
<html:hidden property="windowClose" />
<h3>
	<bean:message bundle="titles" key="app.title.evaluacionSeguimiento.editarObservacion"/>
</h3>	

<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.avance"/>
		</td>
		
		<td>
			<html:text property="avance" maxlength="100" size="60%" />
			<pragmatags:error property="avance" />
		</td>
	</tr>		
</table>
	
<table class="formulario">
	<tr>
		<td class="opcional">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</td>
	</tr>
	<tr>
		<td>
	   		<html:textarea property="observaciones" cols="100" rows="3"/>
	   		<pragmatags:error property="observaciones" />	
   		</td>
	</tr>
</table>


<pragmatags:btnOkCancel okJavascript="actualizarCampos()" cancelJavascript="window.close()"/>
</html:form>
</body>
