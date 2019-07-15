<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<script type="text/javascript" src="js/libreria.js"></script>
<script type="text/javascript" src="js/popUpWindow.js"></script>
<script type="text/javascript">
	function validarTotal() {
		myForm = document.forms[0];
		var anio = myForm.numeroPeriodico.value;
		var fac = myForm.numeroFacturacion.value;
		var valido = true;
        if (!isInt(anio)) {
		   	alert('El Año ingresado debe ser numérico');
		        	valido = false;
	    }
	    if (anio < 1980) {
			alert('El Año ingresado debe ser mayor que 1980');
	        valido = false;
	    }
	    if (anio > 2050) {
			alert('El Año ingresado debe ser menor a 2050');
	        valido = false;
	    }
	    if (!isDouble(fac)) {
		   	alert('La Facturacion ingresada debe ser numérico');
		   	valido = false;
	    }
	    if (fac < 0) {
			alert('La Facturacion ingresada debe ser positiva');
	        valido = false;
	    }
	    if (valido) {
			createRowEvaluacion();
		}
	}
		
function createRowEvaluacion() {
	createRow('<c:out value="${param.idTable}" />','FacturacionDataEditarDynaForm');
}

</script>

<body>


<h3><bean:message bundle="titles" key="app.title.datos" /></h3>
<html:form action="/FacturacionDataGuardar">

<html:hidden property="id" />

	<table class="formulario">
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.anio"/></td>
      	<td><html:text property="numeroPeriodico" maxlength="4" style="width: 40px; text-align:right;"/>
      	</td>
    </tr>
	<tr>
		<td class="obligatorio"><bean:message bundle="labels"
			key="app.label.facturacion" /></td>
		<td><html:text property="numeroFacturacion" maxlength="10" style="text-align:right"/>
		</td>
	</tr>
	</table>

	<pragmatags:btnOkCancel
		okJavascript="validarTotal();"
		cancelJavascript="cerrarPopUp();" />

</html:form>

</body>
