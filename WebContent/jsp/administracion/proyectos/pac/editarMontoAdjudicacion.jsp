<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<script type="text/javascript" src="js/libreria.js"></script>
<script type="text/javascript" src="js/popUpWindow.js"></script>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.editarMontoAdjudicacion"/></h3> 

	<%-- Header Editar Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

<html:form action="/ProyectoPACGuardarMontoAdjudicacion">
<html:hidden property="id" />
<table class="formulario">
	<tr>
		<td nowrap="nowrap" class="obligatorio" width="30%">
			<bean:message bundle="labels" key="app.label.item"/>
		</td>		
		<td class="obligatorio" width="30%">
			<c:out value="${pac.item}"/>
		</td>
		<td width="40%">&nbsp;
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.montoAdjudicado"/> (<c:out value="${pac.descripcionMoneda}"/>)
		</td>		
		<td>
	   		<html:text property="montoAdjudicacion" maxlength="20" style="text-align:right"/>
			<pragmatags:error property="montoAdjudicacion" />
		</td>
		<td>&nbsp;
		</td>
	</tr>
</table>

</html:form>
	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/ProyectoPACInventario"/>

</div>
