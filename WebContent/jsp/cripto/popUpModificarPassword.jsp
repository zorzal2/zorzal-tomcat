<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<script type="text/javascript" src="js/libreria.js"></script>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript" src="js/popUpWindow.js"></script>
<script>

	function modificarPassword()
	{
		var validate = validateModificarPasswordDynaForm(document.ModificarPasswordDynaForm) && validateEquals(document.ModificarPasswordDynaForm);
		if (validate) {
			requerimiento(document.forms[0]);
//			document.ModificarPasswordDynaForm.submit();
		}

	}
	
	function validateEquals(form)
	{
		return (form.newPassword.value == form.confirmNewPassword.value);
	}
	
	function setFocus()
	{
		// seteo el foco en el textbox	
		document.ModificarPasswordDynaForm.oldPassword.focus();	
	}
	
</script>

</head>

<body onload="javascript:setFocus()">

<br>
<h3>
	<bean:message bundle="titles" key="app.title.modificarClaveCriptografia"/>	
</h3>

<html:javascript formName="ModificarPasswordDynaForm"/>

<html:form action="/ChangePasswordValidate">
<table class="formulario">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.contraseniaCriptografiaAnterior" />:
		</td>
		<td>
			<html:password property="oldPassword" />
		</td>
	</tr>

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.nuevaContraseniaCriptografia" />:
		</td>
		<td>
			<html:password property="newPassword" />
		</td>
	</tr>

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.confirmacionNuevaContraseniaCriptografia" />:
		</td>
		<td>
			<html:password property="confirmNewPassword" />
		</td>
		
	</tr>
	<tr></tr>
	<tr align="right">
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

