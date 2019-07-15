<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

	<script type="text/javascript" src="js/libreria.js"></script>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript" src="js/popUpWindow.js"></script>
<script>
	function validar()
	{
		var validate = validateLogonCriptoDynaForm(document.LogonCriptoDynaForm);
		if (validate) {
			document.LogonCriptoDynaForm.submit();
		}
	}
	
	function setFocus()
	{
		// seteo el foco en el textbox	
		document.LogonCriptoDynaForm.criptoPassword.focus();	
	}
</script>

</head>

<body onload="javascript:setFocus()">

<br>
<h3>
	<bean:message bundle="titles" key="app.title.ingresarClaveCriptografia"/>	
</h3>

<html:javascript formName="LogonCriptoDynaForm"/>

<html:form action="/LogonCriptoValidate">
<table class="formulario">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.contraseniaCriptografia"/>:
		</td>
		<td>
			<html:password property="criptoPassword" />
		</td>
		<td colspan="2">
			<html:link href="#" onclick="javascript:submitForm()" >
				<html:image page="/images/aceptar.gif" />
			</html:link>
		</td>
	</tr>
	
</table>	

<pragma:showErrors></pragma:showErrors>

</html:form>

</body>
</html>

