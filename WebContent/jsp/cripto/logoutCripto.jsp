<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<LINK href="css/estilo.css" type=text/css rel=stylesheet>
</head>
<body>
	<h2>
		<bean:message key="app.msj.cripto.logout" bundle="informationals"/>
	</h2>
	<br>
	<center>
	<html:link href="#" onclick="javascript:window.close();" >
			<html:image page="/images/aceptar.gif" />
	</html:link>
	</center>
</body>
</html>

