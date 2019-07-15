<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<html:form action="/RendicionCuentasGestionPagoInventario">
	<toolbar:table model="list" requestURI="/RendicionCuentasGestionPagoInventario.do" />	
</html:form>




