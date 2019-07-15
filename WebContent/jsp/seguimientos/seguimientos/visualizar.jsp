<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<br>
<br>
<div>
	<table class="formulario">
		<tr>
			<td>
				<c:if test="${iniciado}">
					<pragma:authorize permissions="SEGUIMIENTOS-EDITAR">
						<pragmatags:btnDynaLabel action="/SeguimientoEditar?visualizando=yes&id=${id}" label="app.label.editar" />
					</pragma:authorize>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.tipo"/>
			</td>
			<td align="left">				
				<c:if test="${seguimientoVisualizar.esTecnico == 'true'}">
					<bean:message bundle="labels" key="app.label.tecnico"/>
				</c:if>
				<c:if test="${seguimientoVisualizar.esFinanciero == 'true'}">
					<bean:message bundle="labels" key="app.label.contable"/>
				</c:if>				
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.descripcion"/>
			</td>
			<td>				
				<bean:write name="seguimientoVisualizar" property="descripcion"/>
			</td>
		</tr>
	
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.observaciones"/>
			</td>
			<td>
				<bean:write name="seguimientoVisualizar" property="observacion" />
			</td>
		</tr>
	</table>
</div>

