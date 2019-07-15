<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<script type="text/javascript" src="js/libreria.js"></script>
<script type="text/javascript" src="js/popUpWindow.js"></script>
<script type="text/javascript">
function createRowEvaluacion() {
	createRowEntidad('<c:out value="${param.idTable}" />','EntidadIntervinientesEditarDynaForm');
}

function validate() {
	
		var myForm = document.forms[0];
		var entidad = myForm.txtEntidad.value;
		var tipo = myForm.tipoEntidad.value;
		var func = myForm.funcion.value;
		var rel = myForm.relacion.value;
		var valido = true;
        if (entidad == "") {
		   	alert('Debe seleccionar una "Entidad"');
		    valido = false;
		    return;
	    }
	    if (tipo == "") {
			alert('Debe seleccionar un "Tipo"');
	        valido = false;
   		    return;
	    }
/**	    if (func == "") {
		   	alert('Debe definir la "Función en el Proyecto"');
		   	valido = false;
   		    return;
		   	
	    }
	    if (rel == "") {
			alert('Debe definir la "Relación Contractual"');
	        valido = false;
   		    return;
	    } */
	    if (valido) {
			createRowEvaluacion();
		}
}

</script>

<body>


<h3><bean:message bundle="titles" key="app.title.entidadIntervinientes" /></h3>
<html:form action="/EntidadIntervinientesGuardar">
<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<html:hidden property="id" />

	<table class="formulario">
	   	<tr>
			<td class="obligatorio"><bean:message bundle="labels" key="app.label.entidad"/></td>
      		<td>
				<pragmatags:selectorInventario entidad="Entidad" />
				<pragmatags:error property="txtEntidad" />
			</td>
    	</tr>    
		<tr>
			<td class="obligatorio"><bean:message bundle="labels" key="app.label.tipoEntidad" /></td>
			<td colspan="2">
			<html:select property="tipoEntidad">
				<html:options collection="tipoEnt"
					property="value" labelProperty="label" />
			</html:select>
			<pragmatags:error property="tipoEntidad" />
			</td>
		</tr>
	<tr>
		<td class="opcional"><bean:message bundle="labels"
			key="app.label.funcion" /></td>
		<td><html:text property="funcion" size="50" maxlength="500"/>
			<pragmatags:error property="funcion" />
		</td>
	</tr>
   	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.relacion"/></td>
      	<td><html:text property="relacion" size="50" maxlength="500"/>
			<pragmatags:error property="relacion"/>
      	</td>
    </tr>
	</table>

	<pragmatags:btnOkCancel
		okJavascript="validate();"
		cancelJavascript="cerrarPopUp();" />

</html:form>

</body>
