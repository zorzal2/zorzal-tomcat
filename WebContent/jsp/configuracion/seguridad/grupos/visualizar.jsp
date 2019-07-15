<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.visualizarGrupo"/></h3> 

	<!--  Detalle Grupo -->
	<table class="formulario">
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="labels" key="app.label.general" />
			</th>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.nombre"/>
			</td>		
			<td colspan="5">
		   		<bean:write name="grupo" property="nombre"/>	
			</td>
		</tr>	
		<c:if test="${grupo.instrumentable}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.instrumento"/>
			</td>		
			<td colspan="5">
		   		<bean:write name="grupo" property="instrumento"/>	
			</td>
		</tr>	
			
		</c:if>
		<tr><td colspan="2"><br/></td></tr>
			
		<!-- Grupos -->
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="titles" key="app.title.permisos" />
			</th>
		</tr>
		<tr>
			<td colspan="2">
				<table border="0" style="width:70%" class="inventario">
					<tr>
						<th>Modulo</th>
						<th>Nombre</th>
					</tr>
					<c:forEach items="${grupo.permisos}" var="permiso">
						<tr>
							<td><c:out value="${permiso.modulo}"/></td>
							<td><c:out value="${permiso.idPermiso}"/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr><td colspan="2"><br/></td></tr>
		
		<!--  Usuarios -->
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="titles" key="app.title.usuarios" />
			</th>
		</tr>
		<tr>
			<td colspan="2">
				<table border="0" style="width:70%" class="inventario">
					<tr>
						<th>Id</th>
						<th>Nombre</th>
					</tr>
					<c:forEach items="${grupo.usuarios}" var="usuario">
						<tr>
							<td><c:out value="${usuario.userId}"/></td>
							<td><c:out value="${usuario.nombreCompleto}"/></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>



	<!-- Volver al inventario -->
	<pragmatags:btnCerrar action="GruposInventario.do"/>	
	
</div>