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


<body>

<html:form action="/ExpedienteMovimientoGuardar">
<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<c:if test="${!empty id}">
	<html:hidden property="id" />
</c:if>

<table class="formulario">
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.ubicacion" />
			</td>
			<td>
				<html:text property="ubicacion"	maxlength="500" /> 
				<pragmatags:error property="ubicacion" />
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.fecha"/>
			</td>
			<td>
		   		<pragmatags:calendar propertyName="fecha" top="210" left="750" />
				<pragmatags:error property="fecha" />
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.responsable"/>
			</td>		
			<td>
				<pragmatags:selectorInventario entidad="PersonaRepresentante"/>
			</td>
		</tr>
		<tr>
			<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.observaciones" /></th>
		</tr>

		<tr>
			<td colspan="2" class="opcional" align="left">
				<html:textarea property="observaciones" rows="4" cols="100" />
				<pragmatags:error property="observaciones" />
			</td>
		</tr>
</table>
</html:form>
</body>