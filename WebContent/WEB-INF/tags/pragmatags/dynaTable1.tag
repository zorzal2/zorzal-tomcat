<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<%@ attribute name="id" required="true"%>
<%@ attribute name="titles" required="false"%>
<%@ attribute name="columns" required="false"%>

	<table id="<c:out value="${id}" />" border="0" width="100%">
		<!-- Titulos -->
		<c:if test="${!empty titles}">
			<tr>
				<c:forEach items="${titles}" var="title">
					<th><c:out value="${title}" /></th>
		 	 	</c:forEach>
		 	 	<th>Acciones</th>
		 	 </tr>
		</c:if>
		<!--  
			Nombre
			Text
			Value
		-->
		<logic:present name="collection" scope="request"> 	
			<c:forEach var="item" items="${collection}">
		   	<tr>
		   		<c:forEach items="${columns}" var="column">
		      	<td>
			      	<c:out value="${item + '.' + column}"/>
		      		<html-el:hidden name="listDetail" property="${column}" indexed="true"/>	      		
		      	</td>
				</c:forEach>
		      	<td>
		      		<button onclick="deleteRow(this, '<c:out value="${id}" />');"><img src="images/eliminar.gif"/></button>
		      	</td>
			</tr>
		   </c:forEach>
	   </logic:present>
	</table>
