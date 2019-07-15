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

<html:form action="/ExpedienteMovimientoDevolverGuardar">
<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<c:if test="${!empty id}">
	<html:hidden property="id" />
</c:if>
<html:hidden property="ubicacion" />
<html:hidden property="fecha" />
<html:hidden property="idPersonaRepresentante" />
<html:hidden property="observaciones" />

<table class="formulario">
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.ubicacion" />
			</td>
			<td class="opcional">
				<c:out value="${ubicacion}"/>
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.fechaInicio"/>
			</td>
			<td class="opcional">
				<c:out value="${fecha}"/>
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.responsable"/>
			</td>		
			<td class="opcional">
				<c:out value="${txtPersonaRepresentante}"/>
			</td>
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.fechaDevolucion"/>
			</td>
			<td>
		   		<pragmatags:calendar propertyName="fechaDevolucion" top="210" left="750" />
				<pragmatags:error property="fechaDevolucion" />
			</td>
		</tr>
		<tr>
			<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.observaciones" /></th>
		</tr>

		<tr>
			<td class="opcional">
				<c:out value="${observaciones}"/>
			</td>
		</tr>
</table>
</html:form>
</body>