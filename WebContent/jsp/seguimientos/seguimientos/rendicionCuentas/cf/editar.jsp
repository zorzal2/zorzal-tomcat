<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<script type="text/javascript" src="js/libreria.js"></script>
<script type="text/javascript" src="js/popUpWindow.js"></script>

<h3>
	<pragmatags:titulo-edicion clase="RendicionCuentas" />
</h3>

<jsp:include page="/jsp/cabecera/cabeceraSeguimiento.jsp" flush="true"/>
<body>

<html:form action="/RendicionCuentasCFGuardar">
<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<html:hidden property="id"/>

	<table class="formulario">	
		<tr>
			<th colspan="6" class="titulo">
				<bean:message bundle="headers" key="app.header.general" />
			</th>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.rubro"/>
			</td>
			<td colspan="5">
				<html:select property="idRubro" onchange="javascript:changeFormAction('RendicionCuentasCFAgregar')">
					<html:options collection="rubros" property="value" labelProperty="label" />
				</html:select>
				<pragmatags:error property="idRubro" />
			</td>			
		</tr>

		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.fecha"/>
			</td>
			<td colspan="5">
				<pragmatags:calendar propertyName="fecha" top="255" left="250" />
				<pragmatags:error property="fecha" />
			</td>
		</tr>
		
		<html:hidden property="tipoRubro" value="${rubro.tipo}" />
		<html:hidden property="tipoRendicion" value="${rubro.tipoRendicion}" />
		
		<jsp:include page="editarDatos.jsp" flush="true"/>
		
	</table>
		
		<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/RendicionCuentasCFInventario"/>

</html:form>

</body>

