<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<html:form action="/CerrarProyectoGuardar">
<html:hidden property="idProyecto"/>
<table class="formulario">		
	<tr>
		<td class="obligatorio" width="30">
			<bean:message bundle="labels" key="app.label.motivo"/>
		</td>		
		<td>
			<html:select property="motivo">
				<html:options collection="motivos" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="motivo" />
		</td>
	</tr>
	<tr>	
		<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.descripcionMotivo"/></th>
	</tr>	
	<tr>
		<td colspan="2"><html:textarea property="observacion" rows="4" cols="80" /></td>
	</tr>
</table>
</html:form>
