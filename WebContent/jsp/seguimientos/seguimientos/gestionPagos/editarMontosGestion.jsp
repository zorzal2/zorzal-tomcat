<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<script type="text/javascript">
	function actualizarValores() {
		window.opener.document.location.reload();
		submitForm();
	}
</script>

<body>
<html:form action="/GuardarEdicionMontosGestionadosRendicion?id=${id}">
<html:hidden property="id" />
<h3>
	<bean:message bundle="titles" key="app.title.evaluacionSeguimiento.editarMontos"/>
</h3>	

<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<c:if test="${desplegarEnParteYContraparte}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.montoParteGestionado"/>
			</td>
			<td>
				<html:text property="montoParteGestion" maxlength="20" size="15%" style="text-align:right"/>
				<pragmatags:error property="montoParteGestion" />
			</td>	
		</tr>		
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.montoContraparteGestionado"/>
			</td>
			<td>
				<html:text property="montoContraparteGestion" maxlength="20" size="15%" style="text-align:right"/>
				<pragmatags:error property="montoContraparteGestion" />
				
				<html:hidden property="montoTotalGestion"/>
			</td>
		</tr>
	</c:if>
	<c:if test="${!desplegarEnParteYContraparte}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.montoTotalGestionado"/>
			</td>
			<td>
				<html:text property="montoTotalGestion" maxlength="20" size="15%" style="text-align:right"/>
				<pragmatags:error property="montoTotalGestion" />
				
				<html:hidden property="montoContraparteGestion"/>
				<html:hidden property="montoParteGestion"/>
			</td>	
		</tr>		
	</c:if>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</td>
		<td>
			<html:textarea property="observaciones" rows="3" cols="80"/>
			<pragmatags:error property="observaciones" />
		</td>
	</tr>
</table>

<br>
<pragmatags:btnOkCancel okJavascript="actualizarValores()"  cancelJavascript="cerrarPopUp()"/>	
	
</html:form>
</body>
