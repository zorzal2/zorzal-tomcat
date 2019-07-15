<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<!--  Header Presentacion Convocatoria -->
<div>	
	<table class="recuadro">
		
		<tr>
			<td class="label">
				<bean:message bundle="labels" key="app.label.codigo"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="presentacion" property="codigo" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.convocatoria"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="presentacion" property="convocatoria" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.jurisdiccion"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="presentacion" property="jurisdiccion" /></span>
			</td>
			<td class="label">
				<bean:message bundle="labels" key="app.label.solicitante"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="presentacion" property="solicitante" /></span>
			</td>
		</tr>			
	</table>
	<br>
</div>
