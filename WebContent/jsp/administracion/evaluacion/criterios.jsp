<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 

	<table id="criterioTable" border="0" style="width:70%" class="inventario" >
			<tr>
				<th width="80%">
					<bean:message bundle="headers" key="app.header.criterio-categoria" />
				</th>
				<th width="10%">
					<bean:message bundle="headers" key="app.header.puntaje" />
				</th>
			</tr>
			<logic:present name="criterioList" scope="request"> 		
				<logic:iterate name="criterioList" id="criterios" indexId="index">
					<c:set var="claseFila" value="filaCriterio" />
					<c:set var="claseTd" value="obligatorio" />
					
					<c:if test="${criterios.tipoItem == 'categoria'}">
						<c:set var="claseFila" value="filaCategoria" />
						<c:set var="claseTd" value="" />
					</c:if>
					
					
				   	<tr class='<c:out value="${claseFila}" />'>
				      	<td class='<c:out value="${claseTd}" />'><c:out value="${criterios.criterio}"/></td> 
				      	<td>
							<c:out value="${criterios.puntaje}"/>				    
					  	</td>
					</tr>
				</logic:iterate>
		   	</logic:present>
	   </table>	 
