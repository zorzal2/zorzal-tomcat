<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<c:set var="headerKey" value="app.title.visualizacionRegion" />
<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>

<html:form action="/RegionGuardar">

<table class="formulario">	
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
  	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.identificador"/>
		</td>		
		<td class="opcional">
	  			<c:out value="${param.id}"/>
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.nombre"/>
		</td>		
		<td class="opcional">
	   			<c:out value="${nombre}"/>
		</td>
	</tr>
</table>

 <table>
	<tr align="left">
		<td>
			<html:link action="/RegionInventario">
				<bean:message bundle="labels" key="app.label.cancelar"/>
			</html:link>
		</td>
	</tr>
</table>
</html:form>
