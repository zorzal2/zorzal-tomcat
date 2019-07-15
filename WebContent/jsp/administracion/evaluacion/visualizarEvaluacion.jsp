<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.visualizarEvaluacion"/></h3> 

	<%-- Header Proyecto + Evaluacion --%>
	<jsp:include page="cabecera${evaluacion.proyecto.class.simpleName}.jsp" flush="true"/>

<table class="formulario">

	<c:if test="${tieneEvaluadores}">
	<tr>
		<td colspan="2">
			<pragmatags:boton action="/EditarEvaluacionProyecto?id=${id}" permissions="EVALUACIONES-EDITAR" label="app.label.editarEvaluacion"/>
		</td>
	</tr>
	</c:if>

	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.general" />
		</th>
	</tr>
	<tr>
		<td width="20%">
			<bean:message bundle="labels" key="app.label.tipo"/>
		</td>
		<td align="left">
			<bean:write name="evaluacion" property="tipoDescripcion"/>
		</td>
	</tr>		
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaInicial"/>
		</td>
		<td align="left">
			<bean:write name="evaluacion" property="fechaInicial"/>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaEvaluacion"/>
		</td>		
		<td align="left">
			<bean:write name="evaluacion" property="fecha"/>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.resultado"/>
		</td>
		<td align="left">
			<bean:write name="evaluacion" property="resultadoDescripcion"/>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fundamentacion"/>
		</td>
		<td align="left">
			<bean:write name="evaluacion" property="fundamentacion"/>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</td>
		<td>
			<bean:write name="evaluacion" property="observacion"/>
		</td>
	</tr>
</table>

	<!-- Volver al inventario -->
	<pragmatags:btnCerrar/>	
	
</div>