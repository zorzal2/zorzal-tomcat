<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.visualizarUsuario"/></h3> 

	<!--  Detalle Usuario -->
	<table class="formulario">
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
		   		<bean:write name="usuario" property="nombreCompleto"/>	
			</td>
		</tr>	
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.persona"/>
			</td>		
			<td colspan="5">
		   		<bean:write name="usuario" property="nombrePersona"/>	
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
				<table border="0" style="width:50%" class="inventario">
					<tr>
						<th>Nombre</th>
						<th>Instrumento</th>
					</tr>
					<c:forEach items="${usuario.grupos}" var="grupo">
						<tr>
							<td><c:out value="${grupo.nombre}"/></td>
							<td>
								<c:if test="${grupo.instrumentable}">
									<c:out value="${grupo.instrumento}"/>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr><td colspan="2"><br/></td></tr>
	</table>



	<!-- Volver al inventario -->
	<pragmatags:btnCerrar action="UsuariosInventario.do"/>	
	
</div>