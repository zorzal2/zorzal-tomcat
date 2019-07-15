<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<body>
<html:form action="/GuardarEdicionEvaluacionSeguimiento">

<table class="formulario">
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
			<bean:write name="evaluacion" property="clasificacion"/>
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
			<bean:message bundle="labels" key="app.label.fechaEntrega"/>
		</td>
		<td align="left">
			<bean:write name="evaluacion" property="fechaEntregaComprometida"/>
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
	
	<tr>
	</tr>
	
	<tr>
		<td colspan="2" class="obligatorio">
			<bean:message bundle="labels" key="app.label.evaluadores" />
		</td>
	</tr>
	
	<tr>
		<td colspan="2">
			<jsp:include page="/jsp/administracion/evaluacion/evaluadores.jsp" flush="true"/>
		</td>
	</tr>
</table>



<table class="formulario">		
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaEvaluacion"/>
		</td>
		<td>
			<pragmatags:calendar propertyName="fecha" top="255" left="250" />
			<pragmatags:error property="fecha" />
		</td>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.resultado"/>
		</td>
		<td align="left">
			<html:radio property="aceptada" value="true"> 
				<bean:message bundle="labels" key="app.label.aceptaEvalSI"/>
			</html:radio>
			&nbsp;&nbsp;&nbsp;
			<html:radio property="aceptada" value="false"> 
				<bean:message bundle="labels" key="app.label.aceptaEvalNO"/>
			</html:radio>
			<pragmatags:error property="aceptada" />
		</td>
	</tr>

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fundamentacion"/>
		</td>
		<td>
			<html:textarea property="fundamentacion" rows="3" cols="100"/>
			<pragmatags:error property="fundamentacion" />
		</td>
	</tr>
</table>

</html:form>
</body>
