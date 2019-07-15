<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<div>
<!-- Titulo -->
	<h3><bean-el:message bundle="titles" key="app.title.cambiarContraseniaDe" arg0="${usuario}"/></h3> 

	<html:form action="/CambiarClaveAutenticacion.do">
		<html:hidden property="user"/>
		<table class="formulario">
			<tr>
				<td class="obligatorio">
					<bean-el:message bundle="labels" key="app.label.changePassword.passwordAnterior"/>
				</td>		
				<td>
		   			<html:password property="passwordAnterior"/>
	   				<pragmatags:error property="passwordAnterior"/>
				</td>
			</tr>
			<tr>
				<td class="obligatorio">
					<bean-el:message bundle="labels" key="app.label.changePassword.password"/>
				</td>		
				<td>
		   			<html:password property="password" maxlength="1000"/>
	   				<pragmatags:error property="password"/>
				</td>
			</tr>
			<tr>
				<td class="obligatorio">
					<bean-el:message bundle="labels" key="app.label.changePassword.confirmarPassword"/>
				</td>		
				<td>
		   			<html:password property="confPassword" maxlength="1000"/>
	   				<pragmatags:error property="confPassword"/>
				</td>
			</tr>
		</table>	
	</html:form>

	<pragmatags:btnOkCancel okJavascript="submitForm()"	cancelAction="/CambiarClaveAutenticacionCancelar.do" />
</div>