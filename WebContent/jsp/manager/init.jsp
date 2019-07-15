<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<h3>Contexto Criptografía - Inicialización</h3>

<c:choose>

	<c:when test="${status.started}">
		<table class="formulario">
			<tr>
				<td>El contexto de criptografía ha sido inicializado</td>
			</tr>
				<tr>
			<td colspan="2">
				<table border="0" style="width:50%" class="inventario">
					<tr>
						<th>Usuario</th>
						<th>Tipo de datos</th>
					</tr>
					<c:forEach items="${status.claves}" var="clave">
						<tr>
							<td><c:out value="${clave.nombreUsuario}"/></td>
							<td><c:out value="${clave.bean}"/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
			
		</table>
	</c:when>
	<c:otherwise>
	
		<html:form action="/ApplicationManagerInitializeEncryption">
			<table class="formulario">
				<tr></tr>
				<tr>
					<td class="obligatorio">
						<bean:message bundle="labels" key="app.label.appPassword"/>
					</td>		
					<td colspan="5">
			   			<html:password property="password" maxlength="20"/>
			   			<pragmatags:error property="password"/>
					</td>
				</tr>	
				<tr>
					<td class="obligatorio">
						<bean:message bundle="labels" key="app.label.confPassword"/>
					</td>		
					<td colspan="5">
			   			<html:password property="confPassword" maxlength="20"/>
			   			<pragmatags:error property="confPassword"/>
					</td>
				</tr>	
				<tr><td colspan="2"><br/></td></tr>
		</table>
		<html:submit>Inicializar</html:submit>
		
		</html:form>
		
	</c:otherwise>
	
</c:choose>
