<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<c:set var="headerKey" value="app.title.altaEntidad" />
<c:if test="${!empty param.id}" >
	<c:set var="headerKey" value="app.title.visualizacionEntidad" />
</c:if>

<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>

<table class="formulario">
	<tr>
		<th colspan="2" class="titulo"><bean:message bundle="headers"
			key="app.header.general" /></th>
	</tr>
	<tr>
		<td class="obligatorio"><bean:message bundle="labels"
			key="app.label.denominacion" /></td>
		<td><bean:write name="entidad" property="denominacion" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio" style="width: 200px"><bean:message bundle="labels"
			key="app.label.cuit" /></td>
		<td><bean:write name="entidad" property="cuit" /></td>
	</tr>

	<c:if test="${not empty entidad.localizacion}">
		<tr>
			<td class="obligatorio" valign="top"><bean:message bundle="labels"
				key="app.label.localizacionLegal" /></td>
			<td><pragmatags:localizacion id="localizacion"
				esVisualizacion="true" nombreForm="entidad" /></td>
		</tr>
	</c:if>

	<tr>
		<td class="obligatorio"><bean:message bundle="labels"
			key="app.label.contacto" /></td>
		<td><bean:write name="entidad" property="contacto" /></td>
	</tr>

	<c:if test="${not empty entidad.entidadBeneficiaria}">
		<tr>
			<th colspan="2" class="titulo"><bean:message bundle="headers"
				key="app.header.entidadBeneficiaria" /></th>
		</tr>
		<tr>
			<td class="obligatorio"><bean:message bundle="labels"
				key="app.label.tipoEmpresa" /></td>
			<td><c:if
				test="${entidad.entidadBeneficiaria.tipo =='NO_EMPRESA'}">
				<bean:message bundle="labels" key="app.label.noEmpresa" />
			</c:if> <c:if test="${entidad.entidadBeneficiaria.tipo =='EMPRESA'}">
				<bean:message bundle="labels" key="app.label.empresa" />
			</c:if></td>
		<tr>
			<td class="obligatorio"><bean:message bundle="labels"
				key="app.label.subTipo" /></td>
			<td><c:if
				test="${entidad.entidadBeneficiaria.tipo =='NO_EMPRESA'}">
				<bean:write name="entidad"
					property="entidadBeneficiaria.tipoNoEmpresaDescripcion" />
			</c:if> <c:if test="${entidad.entidadBeneficiaria.tipo =='EMPRESA'}">
				<bean:write name="entidad"
					property="entidadBeneficiaria.tipoEmpresaDescripcion" />
			</c:if></td>
		</tr>



		<tr>

			<td class="obligatorio" valign="top"><bean:message bundle="labels"
				key="app.label.domicilioFiscal" /></td>
			<td><pragmatags:localizacion
				id="entidadBeneficiaria.localizacionEconomica"
				esVisualizacion="true" nombreForm="entidad" /></td>

		</tr>
	</c:if>
	<c:if test="${not empty entidad.entidadEvaluadora}">
		<tr>
			<th class="titulo" colspan="2"><bean:message bundle="headers"
				key="app.header.entidadEvaluadora" /></th>
		</tr>
		<c:if test="${not empty entidad.entidadEvaluadora.nroCBU}">
			<tr>
				<td class="obligatorio"><bean:message bundle="labels"
					key="app.label.nroCBU" /></td>
				<td><bean:write name="entidad"
					property="entidadEvaluadora.nroCBU" /></td>
			</tr>
		</c:if>
		<c:if test="${not empty entidad.entidadEvaluadora.nroCuenta}">
			<tr>
				<td class="obligatorio"><bean:message bundle="labels"
					key="app.label.nroCuenta" /></td>
				<td><bean:write name="entidad"
					property="entidadEvaluadora.nroCuenta" /></td>
			</tr>
		</c:if>
		<c:if test="${not empty entidad.entidadEvaluadora.nombreBeneficiario}">
			<tr>
				<td class="obligatorio"><bean:message bundle="labels"
					key="app.label.nombreBeneficiario" /></td>
				<td><bean:write name="entidad"
					property="entidadEvaluadora.nombreBeneficiario" /></td>
			</tr>
		</c:if>
	</c:if>
	<c:if test="${entidad.bancaria}">
		<tr>
			<th class="tildado"><bean:message bundle="headers"
				key="app.header.entidadBancaria" /></th>
			<td>&nbsp;</td>
		</tr>
	</c:if>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><pragmatags:btnCerrar/></td>
		</tr>


</table>
