<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<html:form action="/CerrarSeguimientoGuardar">
	<html:hidden property="idSeguimiento"/>

	<table class="formulario">		
		<tr>	
			<th colspan="2" class="titulo">
				<bean:message bundle="headers" key="app.header.descripcionMotivo"/>
			</th>
		</tr>
		
		<tr>
			<td colspan="2">
				<html:textarea property="observacion" rows="4" cols="80" />
				<pragmatags:error property="observacion" />
			</td>
		</tr>
	</table>
</html:form>
