<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK href="css/estilo.css" type=text/css rel=stylesheet>

<script language="javascript">

function guardar()
{
	var validate = validateAutorizarEvaluacionDynaForm(document.AutorizarEvaluacionDynaForm);
	if (validate) {
		document.AutorizarEvaluacionDynaForm.submit();
	}	
}
</script>

</head>

<body>
<br>

<h3>
	<bean:message bundle="titles" key="app.title.autorizarEvaluacion"/>
</h3>

<html:javascript formName="AutorizarEvaluacionDynaForm"/>

<html:form action="/AutorizarEvaluacionGuardar">

	<html:hidden property="idEvaluacion"/>	
	<html:hidden property="idProyecto"/>	
	
	<table class="recuadro">
		<tr>
			<td class="label">
				<bean:message bundle="labels" key="app.label.nroProyecto"/>:&nbsp;
			</td>
			<td>
				<span><c:out value="${txtProyecto}" /></span>
			</td>

			<td class="label">
				<bean:message bundle="labels" key="app.label.evaluacion"/>:&nbsp;
			</td>
			<td>
				<span><c:out value="${txtEvaluacion}" /></span>
			</td>
		</tr>
	</table>
	<br>
	
	<table class="formulario">


		<!--  Resultado -->

		<tr>
			<th colspan="2">
				<bean:message bundle="headers" key="app.header.resultado" />
			</th>
		</tr>
		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.autorizaEvaluacion"/>
			</td>		
			<td class="opcional" align="left">
				<html:radio property="autorizaEvaluacion" value="1"> 
					<bean:message bundle="labels" key="app.label.si"/>
				</html:radio>
				&nbsp;
				<html:radio property="autorizaEvaluacion" value="2"> 
					<bean:message bundle="labels" key="app.label.no"/>
				</html:radio>
			</td>
		</tr>

		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.fundamentacion"/>
			</td>		
			<td class="opcional" align="left">
		   		<html:textarea property="fundamentacion" rows="4" cols="100" />
			</td>
		</tr>		
	</table>
	
	<pragmatags:btnOkCancel okJavascript="guardar()" cancelAction="/WFCancelarTarea.do"/>	
</html:form>


</body>
</html>





