<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<title>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}" />
	</fmt:bundle>
</title>
	
<html:form action="IdeaProyectoGuardar">

<input type="hidden" id="selectionEvent" name="selectionEvent"/>

<html:hidden property="id"/>
<table class="formulario">
	<tr>
		<th colspan="5" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	
	<c:choose>
		<c:when test="${codigoIdeaProyecto != null}">
			<tr>
				<td class="obligatorio">
					<bean:message bundle="labels" key="app.label.identificador"/>
				</td>	
				<td class="opcional" >
					<bean:write name="IdeaProyectoEditarDynaForm" property="codigoIdeaProyecto"/>
				</td>	
			</tr>
		</c:when>
		<c:otherwise>
			<html:hidden property="codigoIdeaProyecto"/>
		</c:otherwise>
	</c:choose>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaIngreso"/>
		</td>
		
		<td>
			<pragmatags:calendar propertyName="fechaIngreso" top="255" left="250" />
			<pragmatags:error property="fechaIngreso" />
		</td>
	</tr>	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.entidadBeneficiaria"/>
		</td>
		<td>
 			<pragmatags:selectorInventario entidad="EntidadBeneficiaria" />
		</td>
	</tr>		
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.titulo"/>
		</td>
		
		<td>
			<html:text property="titulo" maxlength="3500" size="60%" />
			<pragmatags:error property="titulo" />
		</td>
	</tr>		
		
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.jurisdiccion"/>
		</td>
		<td>
			<html:select property="idJurisdiccion">
				<html:options collection="jurisdicciones" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="idJurisdiccion" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.responsableLegal"/>
		</td>		
		<td>
			<pragmatags:selectorInventario entidad="personaLegal"/>
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.directorProyecto"/>
		</td>		
		<td>
			<pragmatags:selectorInventario entidad="personaDirector"/>
		</td>
	</tr>
	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.representante"/>
		</td>		
		<td>
			<pragmatags:selectorInventario entidad="personaRepresentante"/>
		</td>
	</tr>		
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.resumen"/>
		</td>
		
		<td>
			<html:textarea property="resumen" cols="100" rows="3" />	
			<pragmatags:error property="resumen" />
		</td>
	</tr>		
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.tipoProyecto"/>
		</td>
		
		<td>
			<html:select property="idTipoProyecto">
				<html:options collection="tiposProyectos" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="idTipoProyecto" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.instrumentoSolicitado"/>
		</td>
		
		<td>
			<html:text property="instrumentoSolicitado" maxlength="50" size="60%" />	
			<pragmatags:error property="instrumentoSolicitado" />
		</td>
	</tr>

	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.montoTotal"/>
		</td>
		<td align="left">
			<html:text property="montoTotal" maxlength="15" size="20%" style="text-align:right"/>	
			<pragmatags:error property="montoTotal" />
		</td>
	</tr>		
		
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.montoSolicitado"/>
		</td>
		<td align="left">
			<html:text property="montoSolicitado" maxlength="10" size="20%" style="text-align:right"/>	
			<pragmatags:error property="montoSolicitado" />
		</td>
	</tr>			
	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.duracionMeses"/>
		</td>
		<td align="left">
			<html:text property="duracion" maxlength="5" size="20%" style="text-align:right"/>	
			<pragmatags:error property="duracion" />
		</td>
	</tr>			
</table>

<br>
<br>

<table class="formulario">
	<tr>
		<th class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</th>
	</tr>
	<tr>
		<td>
	   		<html:textarea property="observaciones" cols="100" rows="3"/>
	   		<pragmatags:error property="observaciones" />	
   		</td>
	</tr>

</table>
</html:form>