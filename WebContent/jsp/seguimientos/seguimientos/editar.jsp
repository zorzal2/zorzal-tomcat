<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<h3>
	<c:choose>
		<c:when test="${operacion == 'editar'}">
			<bean:message bundle="titles" key="app.title.edicionSeguimiento"/>
		</c:when>
		<c:otherwise>
			<bean:message bundle="titles" key="app.title.altaSeguimiento"/>				
		</c:otherwise>
	</c:choose>
</h3>

<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>
	
<body onload="document.forms[0]._esFinanciero.value = document.forms[0].esFinanciero.checked;">
	<html:form action="${submitAction}">
	
		<html:hidden property="id" />
		<html:hidden property="idProyecto" />
	
		<table class="formulario">
			<tr>
				<td class="obligatorio" rowspan="2">
					<bean:message bundle="labels" key="app.label.tipo"/>
				</td>
				<td>
					<html:hidden property="_esFinanciero" />

					<html:checkbox property="esFinanciero" disabled="${tieneRendiciones}" onclick="document.forms[0]._esFinanciero.value = this.checked;"/>
					<bean:message bundle="labels" key="app.label.contable"/>
				</td>
				<td rowspan="2">
					<pragmatags:error property="_esFinanciero" />
				</td>			
			</tr>
			<tr>
				<td>
					<html:checkbox property="esTecnico" />
					<bean:message bundle="labels" key="app.label.tecnico"/>
				</td>
			</tr>
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.descripcion"/>
				</td>
				<td colspan="4">
					<html:text property="descripcion" size="50" maxlength="500"/>
					<pragmatags:error property="descripcion" />
				</td>
			</tr>
	
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.observaciones"/>
				</td>
				<td colspan="4">
					<html:textarea property="observaciones" rows="3" cols="100"/>
					<pragmatags:error property="observaciones" />
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<pragmatags:btnDynaLabel javascript="submitForm();" label="app.label.guardar" />
					<pragmatags:btnCancelar action="${cancelAction}" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
