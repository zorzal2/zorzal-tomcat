<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %>

<c:set var="headerKey" value="app.title.altaGrupo" />

<!-- Titulo -->
<h3><bean:message bundle="titles" key="app.title.visualizarGrupo"/></h3> 


<html:form action="GruposGuardar.do">

	<table class="formulario">	
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="headers" key="app.header.general" />
			</th>
		</tr>
		<c:choose>
			<c:when test="${!empty grupo}" >
				<input type="hidden" value="${grupo.idGrupo}" name="id" id="id"/>
				<tr>
					<td class="obligatorio">
						<bean:message bundle="labels" key="app.label.nombre"/>
					</td>
					<td>
						<html:text property="nombre" maxlength="50" size="60%"/>	
						<pragmatags:error property="nombre"/>
					</td>
				</tr>
				<c:if test="${grupo.instrumentable}">
					<tr>
						<td class="opcional">
							<bean:message bundle="labels" key="app.label.instrumento"/>
						</td>		
						<td colspan="5">
		   					<bean:write name="grupo" property="instrumento"/>	
					</td>
				</tr>		
				</c:if>
			</c:when>
			<c:otherwise>
				<tr>
					<td class="obligatorio">
						<bean:message bundle="labels" key="app.label.nombre"/>
					</td>
					<td>
						<html:text property="nombre" maxlength="50" size="60%"/>	
						<pragmatags:error property="nombre"/>
					</td>
				</tr>
				<tr>
					<td class="opcional">
						<bean:message bundle="labels" key="app.label.instrumento"/>
					</td>
					<td>
						<html:select property="instrumento">        
					        <html:options collection="instrumentos" property="value" labelProperty="label"/>
						</html:select>	
					</td>
				</tr>
			</c:otherwise>
		</c:choose>	
		<tr><td><br/></td></tr>
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="titles" key="app.title.permisos" />
			</th>
		</tr>
		<!--  Muestra el error en caso de que no se haya seleccionado algun permiso -->
		<tr><td><pragmatags:error property="permisosSeleccionados"/></td></tr>
		
		<c:if test="${!empty permisos}">
			<tr>
				<td colspan="2" >
					<pragmatags:btnDynaLabel javascript="javascript:checkAll('permisosSeleccionados')" label="app.label.seleccionarTodos"/>		
				</td>	
			</tr>
		</c:if>
		
		<tr>
			<td colspan="2">
				<table id="tablaPermisos" border="0" width="70%" class="inventario">
					<tr>
						<th width="5%"></th>
						<th>Módulo</th>
						<th>Permiso</th>
		 			 </tr>
		 			 
					<c:forEach items="${permisos}" var="selectableItem">
					<tr>
						<td>
							<c:choose>
								<c:when test="${selectableItem.selected}">
									<input type="checkbox" name="permisosSeleccionados" value="${selectableItem.selectable.idPermiso}" checked="checked"/>
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="permisosSeleccionados" value="${selectableItem.selectable.idPermiso}"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td><bean:write name="selectableItem" property="selectable.modulo"/></td>
						<td><bean:write name="selectableItem" property="selectable.idPermiso"/></td>
					</tr>
					</c:forEach>
				</table>			
			</td>
		</tr>
		<tr><td><br/></td></tr>
			<%--  Usuarios
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
		</tr> --%>
	</table>
	
	<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/GruposInventario.do"/>
	
</html:form>