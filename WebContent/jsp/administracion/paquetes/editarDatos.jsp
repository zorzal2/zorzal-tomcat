<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body>
<html:form action="/ModificarPaqueteGuardar">

<html:hidden property="id"/>
<html:hidden property="tipoPaquete"/>
<html:hidden property="instrumento"/>
<html:hidden property="tratamiento"/>

<c:if test="${!empty id}">
	<table>
		<tr>
			<td width="11%" align="right">
				<pragmatags:btnDynaLabel javascript="javascript:checkAll('proyectoArray')" label="app.label.seleccionarTodos"/>
				&nbsp;
			</td>	
		</tr>
	</table>
</c:if>

<pragmatags:tabla 
	toolbar="no" 
	pagesize="1000"
	toolbaroptions="" 
	columns="codigo,entidadBeneficiaria,titulo,recomendacion" 
	actions="selectorProyecto"
	decorator="com.fontar.web.decorator.administracion.paquete.ProyectoFilaModificacionPaqueteDTOWrapper"
	requestURI="PaqueteMostrarProyectos.do" 
	collection="proyectosList" 
	entity=""/>

</html:form>
</body>
