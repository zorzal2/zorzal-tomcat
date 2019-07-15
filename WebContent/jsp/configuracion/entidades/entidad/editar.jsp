<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<c:set var="headerKey" value="app.title.altaEntidad" />
<c:if test="${!empty param.id}" >
	<c:set var="headerKey" value="app.title.edicionEntidad" />
</c:if>

<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>

<body onload="switchLayerLocalizacion('<c:out value='localizacion'/>','1');
 			  toggleVisibility('tdEntidadEvaluadora',document.getElementById('evaluadora').checked);
 			  toggleVisibility('tdEntidadBeneficiaria',document.getElementById('beneficiaria').checked);
 			  ">
<html:form action="${target}" >
<html:hidden property="id"/>
<html:hidden property="idEntidadBeneficiaria"/>

<table class="formulario">	
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<c:if test="${!empty param.id}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.identificador"/>
			</td>		
			<td class="opcional" colspan="5">
				<c:out value="${param.id}" />
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.denominacion"/>
		</td>		
		<td colspan="5">
	   		<html:text property="denominacion" maxlength="200" size="60%"/>	
			<pragmatags:error property="denominacion" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.cuit"/>
		</td>		
		<td colspan="5">
	   		<html:text property="cuit" maxlength="50" size="60%"/>	
			<pragmatags:error property="cuit" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio" valign="top">
			<bean:message bundle="labels" key="app.label.localizacionLegal"/>
		</td>		
		<td colspan="1">
			<pragmatags:localizacion id="localizacion"/>
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.contacto"/>
		</td>		
		<td colspan="5">
	   		<html:text property="contacto" maxlength="50" size="60%"/>	
		</td>
	</tr>
	<!-- Estado -->
	<c:if test="${!empty param.id}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.estado"/>
			</td>		
			<td>
				<html:select property="activo">
					<html:options collection="estadosEntidad" property="value" labelProperty="label" />
				</html:select> 
				<pragmatags:error property="activo" />
			</td>
		</tr>
	</c:if>	

	<!-- Tipo Entidad -->
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.tipoEntidad" />
		</th>
	</tr>
	
	<tr rowspan="3">
		<td valign="top" class="opcional">
			<html:checkbox property="beneficiaria" onclick="javascript:toggleVisibility('tdEntidadBeneficiaria',this.checked==true);"/>
			&nbsp;&nbsp;&nbsp;
			<bean:message bundle="labels" key="app.label.beneficiaria"/>
		</td>
		<td id="tdEntidadBeneficiaria" colspan="5">
			<button onclick="popUpEntidadBeneficiaria();">
			...
			</button>
		</td>	
	</tr>
	<tr>
		<td valign="top" class="opcional">
			<html:checkbox property="evaluadora" onclick="javascript:toggleVisibility('tdEntidadEvaluadora',this.checked==true);"/>
			&nbsp;&nbsp;&nbsp;
			<bean:message bundle="labels" key="app.label.evaluadora"/>
		</td>
		<td id="tdEntidadEvaluadora" colspan="5">
				<pragmatags:evaluadora id="entidadEvaluadora"/>
		</td>
	</tr>
	<tr>
		<td colspan="6" class="opcional">
			<html:checkbox property="bancaria" />
			&nbsp;&nbsp;&nbsp;
			<bean:message bundle="labels" key="app.label.bancaria"/>			
		</td>
	</tr>
	
		<!-- Observaciones -->
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</th>
	</tr>
	<tr>
		<td colspan="6">
	   		<html:textarea property="descripcion" rows="3" cols="100" />	
 			<pragmatags:error property="descripcion" />
   		</td>
	</tr>		
</table>
<pragmatags:btnOkCancel okJavascript="submitForm();"/>
</html:form>
</body>