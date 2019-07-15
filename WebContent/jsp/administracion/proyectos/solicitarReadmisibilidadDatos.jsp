<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<html:form action="/SolicitarReadmisibilidadProyectoTerminar">
<html:hidden property="idProyecto"/>
<table class="formulario">
	<tr>
		<td class="obligatorio" width="20%">
			<bean:message bundle="labels" key="app.label.fechaSolicitarReadmision"/>
		</td>
		<td colspan="2">
			<pragmatags:calendar propertyName="fecha" top="0" left="0" />
			<pragmatags:error property="fecha" />
		</td>
	</tr>		

	<!--  Observaciones -->

	<tr>
		<td class="opcional" colspan="3">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</td>
	</tr>

	<tr>
		<td align="left" colspan="3">
	   		<html:textarea property="observacion" rows="4" cols="100" />
			<pragmatags:error property="observacion" />
		</td>
	</tr>
</table>
</html:form>

