<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter" %>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.acegisecurity.AuthenticationException" %>

<html>
<head>
	<title>FONTAR</title>
	<script language="javascript">
			function checkBrowser(){
				var ua = window.navigator.userAgent;
				var msie = ua.indexOf ( "MSIE " );
				if ( msie <= 0 )      // If Internet Explorer, return version number
					alert('<bean:message bundle="errors" key="app.browser.unsupported"/>');
			}
	</script>
</head>
<link rel="stylesheet" type="text/css" media="screen" href="css/estilo.css" />

<body onload="document.forms[0].j_username.focus();checkBrowser();">

<table cellspacing=0 cellpadding=0  border="0">
	<tr>
		<td align="left"><img src="images/logo_agencia2.gif"></td>
	</tr>
</table>  

<BR><BR><BR><BR>

<form method="POST" action="j_acegi_security_check">
    <table class="login" border="1" cellpadding="1" cellspacing="1" width="200" align="center">
        <tr >
            <th colspan="2">
                <bean:message bundle="labels" key="app.label.autenticacion"/>
            </th>
        </tr>
        <tr>
            <td>
                <bean:message bundle="labels" key="app.label.usuario"/>
            </td>
            <td>
                <input type="text" name="j_username" size="16" 
                maxlength="18" <c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'</c:if>>
            </td>
        </tr>
        <tr>
            <td>
                <bean:message bundle="labels" key="app.label.password"/>
            </td>
            <td>
                <input type="password" name="j_password" size="16" maxlength="18">
            </td>
        </tr>
            <td colspan="2">   
			    <c:if test="${not empty param.login_error}">
			      <span class="error"><pragma:loginFailure/></span>
			    </c:if>
            </td>
        </tr>
        <tr>
            <td class="footer">
                <input type="submit" value="Aceptar" title="Aceptar"/>
            </td>
            <td>
                <input type="reset" value="Restablecer" title="Restablecer"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
