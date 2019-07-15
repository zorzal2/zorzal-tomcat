<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<table border="0" width="100%">	
	<tr>
		<td valign="top">
			<jsp:include page="/jsp/cabecera/cabeceraProyectoRaiz.jsp" flush="true"/>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<jsp:include page="visualizar.jsp" flush="true"/>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<!-- Volver al inventario -->
			<pragmatags:btnCerrar/>	
		</td>
	</tr>
</table>
