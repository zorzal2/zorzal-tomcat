<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<h3>
	<c:choose>
		<c:when test="${tipoPaquete == 'COMISION'}">
			<bean:message bundle="titles" key="app.title.proyectosParaComision"/>
		</c:when>
		<c:when test="${tipoPaquete == 'SECRETARIA'}">
			<bean:message bundle="titles" key="app.title.proyectosParaSecretaria"/>
		</c:when>
		<c:when test="${tipoPaquete == 'DIRECTORIO'}">
			<bean:message bundle="titles" key="app.title.proyectosParaDirectorio"/>
		</c:when>
	</c:choose>
</h3> 
<html:form action="/PaqueteAsociarProyectos">

<html:hidden property="id"/>
<html:hidden property="tipoPaquete"/>
<html:hidden property="instrumentoFiltrado"/>
<html:hidden property="tratamientoFiltrado"/>

<pragmatags:error property="proyectoArray" />

<table class="recuadro">
	<tr>
		<td class="label" width="10%">
			&nbsp;<bean:message bundle="labels" key="app.label.instrumento"/>
		</td>
		
		<td width="20%"> 
			<html:select property="instrumento" >
				<html:options collection="instrumentos" property="value" labelProperty="label"/>
			</html:select>		
		</td>
		
		<td class="label" width="10%">
			<bean:message bundle="labels" key="app.label.tratamiento"/>
		</td>
		
		<td width="15%">
			<html:select property="tratamiento">
				<html:options collection="tratamientos" property="value" labelProperty="label"/>
			</html:select>	
		</td>		
		
		<td class="label">
			<pragmatags:btnDynaLabel javascript="javascript:changeFormAction('PaqueteMostrarProyectos')" label="app.label.mostrarProyectos" />	
		</td>
	</tr>	
</table>

<br>
<br>

<c:if test="${!empty proyectosList}">
	<table align="right">
		<tr>
			<td>
				<pragmatags:btnDynaLabel javascript="javascript:checkAll('proyectoArray')" label="app.label.seleccionarTodos"/>
				&nbsp;
			</td>	
		</tr>
	</table>
</c:if>

<br>
<br>

<pragmatags:tabla 
	toolbar="no"
	pagesize="1000" 
	toolbaroptions="" 
	columns="codigo,entidadBeneficiaria,titulo,descripcionRecomendacion" 
	actions="selectorProyecto"
	decorator="com.fontar.web.decorator.administracion.paquete.ProyectoFilaModificacionPaqueteDTOWrapper"
	requestURI="PaqueteMostrarProyectos.do" 
	collection="proyectosList" 
	entity="" />
<br>
<br>
<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="PaqueteInventario"/>
</html:form>
