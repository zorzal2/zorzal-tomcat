<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-menu" prefix="menu" %>
<%@ taglib uri="/tags/struts-menu-el" prefix="menu-el" %>
<%@ taglib uri="/tags/authz" prefix="authz" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<script>
	function popUpLogon()
	{
		window.open('LogonCripto.do','','toolbar=no,menubar=no,location=no,height=200,width=400,left=300,top=220');
		return false;
	}
	
	function popUpLogout()
	{
		encriptionOff();
		window.open('LogoutCripto.do','','toolbar=no,menubar=no,location=no,height=200,width=400,left=300,top=220');
		return false;
	}
	
	function popUpModificarPassword()
	{
		window.open('ChangePassword.do','','toolbar=no,menubar=no,location=no,height=230,width=400,left=300,top=205');
		return false;
	}
	
	function logout()
	{
		//TODO: FF /  PARA QUE FUNCIONE EN LA PRESENTACION
		window.location.href = "/fontar/LogoutApplication.do";
	}
	
	function cambiarContrasenia()
	{
		window.location.href = "/fontar/CambiarClaveAutenticacionInput.do?user=<c:out value="${sessionScope['ACEGI_SECURITY_LAST_USERNAME']}"/>";
	}
	
	function encriptionOn(){
		var container = $('encriptionContainer');
		container.innerHTML = '<img src="images/candadoAbierto.gif" onclick="javascript:popUpLogout()" alt="Salir de zona de Encriptación"/>';
	}
	
	function encriptionOff(){
		var container = $('encriptionContainer');
		container.innerHTML = '<img src="images/candado.gif" onclick="javascript:popUpLogon()" alt="Ingresar Contraseña de Encriptación"/>';
	}
	
	
</script>

<table width="100%" border="0">
	<tr>
		<td width="85%" rowspan="2">
			<html:image page="/images/logo_agencia2.gif" />
		</td>
		<td width="15%" style="font-size:9px;">
			<bean:message bundle="labels" key="app.label.usuario"/>:&nbsp;
			<i><b><c:out value="${sessionScope['ACEGI_SECURITY_LAST_USERNAME']}"/></b></i>
		</td>
	</tr>
	<tr>
		<td>
			<table>
				<tr>
				
					<td id="encriptionContainer">
						<pragma:encryptionContext/>
						
					</td>
					<td>
						<pragma:authorize permissions="ENCRIPTACION">
							<html:image page="/images/modificarPassword.gif" onclick="javascript:popUpModificarPassword()" alt="Modificar Contraseña de Encriptación" />
						</pragma:authorize>
					</td>				
					<td>
						<html:image page="/images/cambiarContrasenia.gif" onclick="javascript:cambiarContrasenia()" alt="Cambiar Contraseña de Inicio de Sesión" border="0"/>
					</td>				
					<td>
						<html:image page="/images/salir.gif" onclick="javascript:logout()" alt="Salir de la Aplicación" border="0"/>
					</td>				
				</tr>	
			</table>
 		</td> 		
	</tr>
</table>   
