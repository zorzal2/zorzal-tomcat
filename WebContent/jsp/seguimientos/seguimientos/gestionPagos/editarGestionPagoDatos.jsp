<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<body>
<html:form action="/EvaluarGestionPagoSeguimientoGuardar">

<jsp:include page="/jsp/seguimientos/resumenDeRendicion.jsp" flush="true"></jsp:include>
<br>

<table class="formulario">		
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.resultado" />
		</th>
	</tr>

	<tr>
		<td class="obligatorio"> 
			<bean:message bundle="labels" key="app.label.resultado" />
		</td>
		<td>
			<html:select property="estado">
				<html:options collection="resultados" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="estado" />
		</td>
	</tr>

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</td>
		<td>
			<html:textarea property="observacion" rows="3" cols="100"/>
			<pragmatags:error property="observacion" />
		</td>
	</tr>
</table>

</html:form>
</body>
