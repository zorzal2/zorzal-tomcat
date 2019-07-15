<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<div>
	<h3><bean:message bundle="titles" key="app.title.visualizarUsuario"/></h3> 

<html:form action="/UsuariosActualizar.do">
	<!-- Auxiliar para la seleccion de grupos -->
	<input type="hidden" id="selectionEvent" name="selectionEvent"/>
	<!-- Auxiliar para la seleccion de personas -->
	<input type="hidden" id="selectionPersonaEvent" name="selectionPersonaEvent"/>
	<!--  Detalle Usuario -->
	<table class="formulario">

		<input type="hidden" id="userId" name="userId" value="${usuario.userId}"/>
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="labels" key="app.label.general" />
			</th>
		</tr>
		<tr>
			<td class="obligatorio" width="20%"> 
				<bean:message bundle="labels" key="app.label.identificador"/>
			</td>		
			<td colspan="5">
	   			<bean:write name="usuario" property="userId"/>
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.nombre"/>
			</td>		
			<td colspan="5">
	   			<html:text property="userName" maxlength="25"></html:text>
	   			<pragmatags:error property="userName" />
			</td>
		</tr>	
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.apellido"/>
			</td>		
			<td colspan="5">
	   			<html:text property="userLastName" maxlength="25"></html:text>
	   			<pragmatags:error property="userLastName"/>
			</td>
		</tr>	
		<tr>		
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.persona"/>
			</td>
			<td colspan="5">
				<pragmatags:selectorInventario entidad="Persona" clearButton="yes" eventName="selectionPersonaEvent"/>
			</td>
		</tr>
		<tr><td colspan="2"><br/></td></tr>
		<!-- Grupos -->
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="titles" key="app.title.grupos" />
			</th>
		</tr>
		<tr>
			<td colspan="2">
				<pragmatags:btnAgregar javascript="popUpGrupos('tablaGrupos');"/>
			</td>
		</tr>
		<tr>
		<td colspan="2">
		<table id="tablaGrupos" border="0" width="70%" class="inventario">
			<tr>
				<th>Grupo</th>
				<th>Instrumento</th>
				<th>Acciones</th>
 			 </tr>
 			 <c:if test="${!empty  grupos}">
			<c:forEach items="${grupos}" var="grupo">
			<tr>
				<input id="groups" name="groups" value="${grupo.idGrupo}" type="hidden"/>
				<td><c:out value="${grupo.nombre}"/></td>
				<td>
					<c:if test="${grupo.instrumentable}">
						<c:out value="${grupo.instrumento}"/>
					</c:if>
				</td>
				<td><button name="Delete" onclick="deleteRow(this , 'tablaGrupos')"><img src="images/eliminar.gif"/></button></td>
			</tr>
			</c:forEach>
			</c:if>
		</table>			
		
		</td>
	</tr>
		<tr><td colspan="2"><br/></td></tr>
	</table>


	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/UsuariosInventario.do"/>
	
</html:form>
</div>