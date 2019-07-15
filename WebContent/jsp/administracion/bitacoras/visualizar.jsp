<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
   
<div>
	<h3>
		<bean:message bundle="titles" key="app.title.bitacoraInventario"/>
	</h3>
	<table border="0" style="width:70%" class="inventario">
	<tr>
		<th>Bitacora Item</th>
		<th>Fecha</th>
		<th>Descripción</th>
	 </tr>
	 <c:if test="${!empty bitacora.items}">
	<c:forEach items="${bitacora.items}" var="item">
	<tr>
		<td><c:out value="${item.descripcion}"/></td>
		<td><c:out value="${item.descripcion}"/></td>
		<td><c:out value="${item.descripcion}"/></td>
	</tr>
	</c:forEach>
	</c:if>
</table>

</div>