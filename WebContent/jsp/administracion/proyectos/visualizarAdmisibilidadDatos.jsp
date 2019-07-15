<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %>
<script type="text/javascript" src="js/libreria.js"></script>
<script type="text/javascript" src="js/popUpWindow.js"></script>

<!--  Detalle -->
<html:form action="/VisualizarAdmisibilidadProyecto.do">
<html:hidden property="id" />
<table class="formulario">		
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	

	<!------------General------------>
	<tr>		
		<th colspan="5" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>	
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaAdmision"/>
		</td>		
		<td class="opcional">
			<bean:write name="admisibilidad" property="fechaAdmision"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.nroDisposicion"/>
		</td>		
		<td class="opcional">
			<bean:write name="admisibilidad" property="nroDisposicion"/>
		</td>

	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fundamentacion"/>
		</td>		
		<td class="opcional">
			<bean:write name="admisibilidad" property="fundamentacion"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.observaciones"/>
		</td>		
		<td class="opcional">
			<bean:write name="admisibilidad" property="observaciones"/>
		</td>
	</tr>
</table>

<%-- <button onclick="popUpPrintAdmisibilidad(<c:out value='${param.id}'/>);">PRINT
</button>--%>
</html:form>