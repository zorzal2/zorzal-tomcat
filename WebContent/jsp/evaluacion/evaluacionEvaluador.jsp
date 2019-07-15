<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<script type="text/javascript">

	function validateEvaluador(){
		
		var currentForm = window.document.EvaluacionEvaluadorEditarDynaForm;
		var entidad = currentForm.idEntidadEvaluadora.value;
		if(entidad!=null  && entidad!=""){
			return true;
		}else{
			alert("Debe seleccionar la entidad evaluadora");		
			return false;
		}
	}
	
	function createRowEvaluacion() {
		if(validateEvaluador() )
			createRow('<c:out value="${param.idTable}" />','EvaluacionEvaluadorEditarDynaForm');
	}

	function updateEntidadEvaluadora(){
		var button = $("buttonEvaluador");
		button.disabled = false;
		var input = $("idEntidadEvaluadora");
		if( input.oldValue ){
			if(input.oldValue != input.value)
				$("idEvaluador").value = null;
				$("txtEvaluador").value = "";
		}
		input.oldValue = input.value;
	}
	
	function init(){
		var observer = new Form.Element.Observer($("idEntidadEvaluadora"), 1, updateEntidadEvaluadora);
		var button = $("buttonEvaluador");
		button.disabled = true;
	}
	
	function selectorEvaluadorParameters(){
		return '?idEntidadEvaluadora=' + $("idEntidadEvaluadora").value;
	}
	
</script>

<body onload="init();">

<h3><bean:message bundle="titles" key="app.title.evaluacionEvaluador" /></h3>

<html:form action="/EvaluacionEvaluadorGuardar">
<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<html:hidden property="id" />

	<table class="formulario">
		<tr>
			<td class="obligatorio"><bean:message bundle="labels" key="app.label.entidadEvaluadora" /></td>
			<td><pragmatags:selectorInventario width="60" entidad="EntidadEvaluadora" /></td>
		</tr>
		<tr>
			<td class="opcional"><bean:message bundle="labels" key="app.label.evaluador" /></td>
			<td><pragmatags:selector width="40" entidad="Evaluador" parametersBuilder="selectorEvaluadorParameters" /></td>
		</tr>		
		<tr>
			<td class="opcional"><bean:message bundle="labels" key="app.label.lugar" /></td>
			<td><html:text property="lugar" size="60" maxlength="1000"/></td>
		</tr>
	</table>
	<pragmatags:btnOkCancel okJavascript="createRowEvaluacion();" cancelJavascript="cerrarPopUp();" />
</html:form>
</body>