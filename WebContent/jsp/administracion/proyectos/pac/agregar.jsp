<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<script type="text/javascript" src="js/libreria.js"></script>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.pac"/></h3> 

	<%-- Header Editar Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

<html:form action="/PACAgregarGuardar">
<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<c:if test="${!empty id}">
	<html:hidden property="id" />
</c:if>
<html:hidden property="idProyecto" />
<input type="hidden" value="${item}" name="item" id="item"/>
<table class="formulario">

	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.item"/>
		</td>		
		<td class="obligatorio">
			<c:out value="${item}"/>
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.descripcion"/>
		</td>		
		<td>
	   		<html:text property="descripcion" maxlength="50" size="60%"/>	
			<pragmatags:error property="descripcion" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.etapa" /></td>
		<td colspan="2">
		<html:select property="etapa">
			<html:options collection="etapas" property="value" labelProperty="label" />
			</html:select>
			<pragmatags:error property="etapa" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.rubro" /></td>
		<td colspan="2">
		<html:select property="idRubro">
			<html:options collection="rubros" property="value" labelProperty="label" />
		</html:select>
			<pragmatags:error property="idRubro" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.montoParte"/>
		</td>		
		<td>
	   		<html:text property="montoParte" maxlength="20" style="text-align:right"/>	
			<pragmatags:error property="montoParte" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.adquisicion" />
		</td>
		<td colspan="2">
			<html:select property="idTipoAdquisicion">
				<html:options collection="adquisiciones" property="value" labelProperty="label" />
			</html:select>
			<pragmatags:error property="idTipoAdquisicion" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fecha"/>
		</td>		
		<td colspan="2">
			<pragmatags:calendar propertyName="fecha" top="255" left="250" />
			<pragmatags:error property="fecha" />
		</td>
	</tr>

</table>
</html:form>
	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/ProyectoPACInventario"/>

</div>
