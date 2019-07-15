<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<!--  Header de Instrumento solito -->
<div>	
	<table class="recuadro">
	<colgroup>
		<col width="10">
		<col width="100%">
	</colgroup>	
		<tr>
			<!-- Instrumento -->
			<td class="label">
				<bean:message bundle="labels" key="app.label.instrumento"/>:&nbsp;
			</td>
			<td>
				<span><bean:write name="instrumento" property="identificador" /></span>
			</td>
		</tr>
	</table>
	<br>
</div>