<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-logic-el" prefix="logic-el" %>

<%@ attribute name="bean" required="true"%>

<c:set var="randomId" value="<%= String.valueOf(Math.random()) %>" />

<% 
jspContext.setAttribute("__persona__", jspContext.getExpressionEvaluator().evaluate("${"+jspContext.findAttribute("bean")+"}", com.fontar.data.impl.domain.dto.PersonaDTO.class, jspContext.getVariableResolver(), null));
%>

<c:if test="${not empty __persona__}">
	<script type="text/javascript" language="javascript">
	if(!window['expandPersona']) {
	
		expandPersona = function(controlId) {
			document.getElementById('persona_expanded_'+controlId).style.display = '';
		}
		collapsePersona = function(controlId) {
			document.getElementById('persona_expanded_'+controlId).style.display = 'none';
		}
		
		togglePersona = function(controlId) {
			var expanded = document.getElementById('persona_expanded_'+controlId);
			if(expanded.style.display == 'none') {
				expandPersona(controlId);
			} else {
				collapsePersona(controlId);
			}
		}
	
	}
	</script>
	<div valign="top">
		<b><c:out value="${__persona__.nombre}"/></b>&nbsp;&nbsp;&nbsp;
		<%--img onclick="togglePersona('<c:out value='${randomId}'/>')" src="images/visualizar.gif" title="Detalle de Persona" border=0/--%>
		<button onclick="togglePersona('<c:out value='${randomId}'/>')" title="Detalle de Persona">...</button>
	</div>
	<table class="mini_detail" id="persona_expanded_<c:out value='${randomId}'/>" style="display: none">
		<c:if test="${not empty __persona__.cuit}">
			<tr>
				<td class="label">
					<bean:message bundle="labels" key="app.label.cuitCuil"/>
				</td>		
				<td class="value">
					<bean:write name="__persona__" property="cuit"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty __persona__.localizacion.direccion}">
			<tr>
				<td class="label" >
					<bean:message bundle="labels" key="app.label.direccion"/>
				</td>		
				<td class="value">
					<bean:write name="__persona__" property="localizacion.direccion"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty __persona__.localizacion.localidad}">
			<tr>
				<td class="label" >
					<bean:message bundle="labels" key="app.label.localidad"/>
				</td>		
				<td class="value">
					<bean:write name="__persona__" property="localizacion.localidad"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty __persona__.localizacion.codigoPostal}">
			<tr>
				<td class="label" >
					<bean:message bundle="labels" key="app.label.codigoPostal"/>
				</td>		
				<td class="value">
					<bean:write name="__persona__" property="localizacion.codigoPostal"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty __persona__.localizacion.nombreJurisdiccion}">
			<tr>
				<td class="label" >
					<bean:message bundle="labels" key="app.label.jurisdiccion"/>
				</td>		
				<td class="value">
					<bean:write name="__persona__" property="localizacion.nombreJurisdiccion"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty __persona__.localizacion.telefono}">
			<tr>
				<td class="label" >
					<bean:message bundle="labels" key="app.label.telefono"/>
				</td>		
				<td class="value">
					<bean:write name="__persona__" property="localizacion.telefono"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty __persona__.localizacion.fax}">
			<tr>
				<td class="label" >
					<bean:message bundle="labels" key="app.label.fax"/>
				</td>		
				<td class="value">
					<bean:write name="__persona__" property="localizacion.fax"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty __persona__.localizacion.email}">
			<tr>
				<td class="label" >
					<bean:message bundle="labels" key="app.label.email"/>
				</td>		
				<td class="value">
					<bean:write name="__persona__" property="localizacion.email"/>
				</td>
			</tr>
		</c:if>
	</table>
</c:if>
