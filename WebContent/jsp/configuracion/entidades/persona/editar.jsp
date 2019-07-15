<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

	<c:set var="headerKey" value="app.title.altaPersona" />
	<c:if test="${!empty param.id}" >
		<c:set var="headerKey" value="app.title.edicionPersona" />
	</c:if>

	<title>
		<fmt:bundle basename="resources.Titles">
			<fmt:message key="${headerKey}"/>
		</fmt:bundle>
	</title>
	
<body onload="switchLayerLocalizacion('<c:out value="localizacion"/>','1');
			toggleVisibility('tdEsEvaluador',document.getElementById('esEvaluador').checked);
			">
<br>
<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>
<html:form action="${target}" >
<html:hidden property="id"/>
<table class="formulario">	
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.nombre"/>
		</td>		
		<td colspan="5">
	   		<html:text property="nombre" maxlength="50" size="60%"/>	
			<pragmatags:error property="nombre" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.sexo"/>
		</td>		
		<td class="opcional" align="left" colspan="5">
			<html:radio property="sexo" value="M">
				<bean:message bundle="labels" key="app.label.M"/>
			</html:radio>
			&nbsp;&nbsp;&nbsp;
			<html:radio property="sexo" value="F">
				<bean:message bundle="labels" key="app.label.F"/>
			</html:radio>
			<pragmatags:error property="sexo" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.cuitCuil"/>
		</td>		
		<td colspan="5">
	   		<html:text property="cuit" maxlength="50" size="60%"/>
			<pragmatags:error property="cuit" />
		</td>
	</tr>
	<tr>
		<td class="opcional" valign="top">
			<bean:message bundle="labels" key="app.label.localizacion"/>
		</td>		
		<td colspan="1">
			<pragmatags:localizacion id="localizacion"/>
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.cargo"/>
		</td>		
		<td colspan="5">
	   		<html:text property="cargo" maxlength="50" size="60%"/>	
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.tituloGrado"/>
		</td>		
		<td colspan="5">
	   		<html:text property="tituloGrado" maxlength="50" size="60%"/>	
		</td>
	</tr>

	<%-- Tipo Persona --%>
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.tipoPersona" />
		</th>		
	<tr>
		<td class="opcional">
			<html:checkbox property="esEvaluador" onclick="javascript:toggleVisibility('tdEsEvaluador',this.checked==true);"/>
			&nbsp;&nbsp;&nbsp;
			<bean:message bundle="labels" key="app.label.evaluador"/>
		</td>
		<td id="tdEsEvaluador" colspan="5">
			<button onmouseOver="javascript:this.style.cursor='hand'" onclick="popUpPersonaEvaluador();">
				...
			</button>
		</td>		
	</tr>

	<c:if test="${!empty param.id}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.estado"/>
			</td>		
			<td class="opcional">
				<html:select property="activo">
					<html:options collection="estadosEntidad" property="value" labelProperty="label" />
				</html:select> 
				<pragmatags:error property="activo" />
			</td>
		</tr>
	</c:if>	
			
	<%-- Observaciones --%>

	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</th>
	</tr>
	<tr>
		<td colspan="6">
	   		<html:textarea property="observacion" rows="3" cols="100" />	
			<pragmatags:error property="observacion" />
   		</td>
	</tr>		
</table>

<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/PersonaCancelar"/>
</html:form>
</body>