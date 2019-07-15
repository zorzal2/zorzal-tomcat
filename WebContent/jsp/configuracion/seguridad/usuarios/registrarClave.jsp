<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<div>
<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.registracionClaveUsuario"/></h3> 

	<html:form action="/UsuariosRegistrarClavePrivada.do">
		<table class="formulario">
			<tr>		
				<th colspan="3" class="titulo">
		Usted debe registrar su clave de acceso a datos encriptados
				</th>	
			</tr>
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.password"/>
				</td>		
				<td>
		   			<input type="password" name="password" id="password"/>
	   				<pragmatags:error property="password"/>
				</td>
			</tr>	
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.confPassword"/>
				</td>		
				<td>
			   		<input type="password" name="confPassword" id="confPassword"/>
		   			<pragmatags:error property="confPassword"/>
				</td>
			</tr>	
			<tr><td><br/></td></tr>
		</table>
	
	</html:form>
	
	<pragmatags:btnOk javascript="submitForm()"/>	
	
</div>