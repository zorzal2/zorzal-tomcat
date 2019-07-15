<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<html:form action="/RendicionCuentasANRInventario">

	<c:if test="${permiteAgregarOQuitarRendiciones}">
		<table>
			<tr valign="top">
				<td><pragmatags:btnAgregar action="/RendicionCuentasANRAgregar" /></td>
				<td>
					<html:link action="RendicionesExcelIngresar.do?seguimientoId=${seguimientoId}">
						<bean:message bundle="titles" key="app.title.rendiciones.cargarDesdeExcelAbreviado"/>	
					</html:link>
				</td>
			</tr>
		</table>
	</c:if>
	<toolbar:table model="list" requestURI="/RendicionCuentasANRInventario.do" />	
	
</html:form>





