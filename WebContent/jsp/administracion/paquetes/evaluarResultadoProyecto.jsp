<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body>
<html:form action="/EvaluarResultadoProyectoGuardar">

<html:hidden property="idProyecto"/>
<html:hidden property="idPaquete"/>
<h3><bean:message bundle="titles" key="app.title.evaluarProyecto"/></h3>

<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

<!-- tabla con los datos de EvaluarResultadoProyectoDTO  -->
<pragmatags:tabla 
	toolbar="no"
	pagesize="1000"
	toolbaroptions=""
	columns="idEvaluacion,evaluaciones,resultado" 
	actions="selectorEvaluacion"
	decorator="com.fontar.web.decorator.proyectos.paquetes.ProyectoEvaluarFilaDTOWrapper"
	requestURI="EvaluarResultadoProyecto.do" 
	collection="evaluacionesList"/>
	
<br>
<table class="formulario">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.resultado"/>
		</td>		
		<td align="left">
			<html:select property="resultado">
				<html:options collection="resultados" property="value" labelProperty="label"/>
			</html:select>
		</td>
		<td class="opcional" align="left">
			<html:checkbox property="dictamen"/>&nbsp;Dictámen
		</td>
		
		<c:if test="${!empty montoAprobado}" >
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.montoAprobado"/>
			</td>
			<td align="left"><c:out value="${montoAprobado}" /></td>&nbsp&nbsp&nbsp	
		</c:if>

		<c:if test="${permiteCargaAlicuota}" >
			<td class="obligatorio" width="20%">
				<bean:message bundle="labels" key="app.label.alicuota"/>
			</td>
			<td align="left">
				<html:text property="alicuota" maxlength="6" size="15" style="text-align:right" />
				<pragmatags:error property="alicuota" />
			</td>
		</c:if>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.fundamentacion"/>
		</td>
		<td align="left" colspan="3">
	   		<html:textarea property="fundamentacion" rows="4" cols="100" />
			<pragmatags:error property="fundamentacion" />
		</td>
	</tr>
</table>

<pragmatags:btnOkCancel okJavascript="submitForm()" cancelJavascript="window.close()"/>		

</html:form>
</body>