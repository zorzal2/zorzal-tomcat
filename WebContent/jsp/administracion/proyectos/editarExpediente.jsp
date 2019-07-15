<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<script type="text/javascript" src="js/libreria.js"></script>
<script type="text/javascript" src="js/popUpWindow.js"></script>
<script type="text/javascript">
	function validarTotal() {
		myForm = document.forms[0];
		var cuerpo = myForm.cuerpo.value;
		var desde = myForm.folioDesde.value;
		var hasta = myForm.folioHasta.value;
		var valido = true;
        if (!isInt(cuerpo)) {
		   	alert('El Valor de Cuerpo ingresado debe ser numérico');
		        	valido = false;
	    }
        if (!isInt(desde)) {
		   	alert('El Valor de Folio Desde ingresado debe ser numérico');
		        	valido = false;
	    }
        if (!isInt(hasta)) {
		   	alert('El Valor de Folio Hasta ingresado debe ser numérico');
		        	valido = false;
	    }
	    if (valido) {
			createRowEvaluacion();
		}
	}
		
function createRowEvaluacion() {
	createRow('expedienteTabla','ExpedienteProyectoEditarDynaForm');
}

</script>

<body>

<h3>
	<bean:message bundle="titles" key="app.title.expedienteCuerpo"/>
</h3>

<html:form action="/ExpedienteProyectoAgregar">

<input type="hidden" name="selectionEvent" id="selectionEvent"/>

<html:hidden property="windowClose" />
<c:if test="${!empty id}">
	<html:hidden property="id" />
</c:if>

<table class="formulario">
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
</table>
	<table class="formulario">
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.cuerpo"/></td>
      	<td><html:text property="cuerpo" maxlength="20" style="text-align:right"/>
      	</td>
    </tr>
	<tr>
		<td class="obligatorio"><bean:message bundle="labels"
			key="app.label.folioDesde" /></td>
		<td><html:text property="folioDesde" maxlength="20" style="text-align:right"/>
		</td>
	</tr>
	<tr>
		<td class="obligatorio"><bean:message bundle="labels"
			key="app.label.folioHasta" /></td>
		<td><html:text property="folioHasta" maxlength="20" style="text-align:right"/>
		</td>
	</tr>
	</table>
	<pragmatags:btnOkCancel
		okJavascript="validarTotal();"
		cancelJavascript="cerrarPopUp();" />
</html:form>
</body>