<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-logic-el" prefix="logic-el" %>

<%@ attribute name="id" required="true"%>
 
<%@ attribute name="esVisualizacion" required="false" %>
<%@ attribute name="nombreForm" required="false" %>
 

<c:set var="visible" value="display:none;"/> 

<html:messages id="error">
    <c:set var="visible" value="display:block;"/>
</html:messages>


<button onclick="javascript:switchLayer('<c:out value="${id}"/>');">...</button>
<table class="mini_detail" id="<c:out value='${id}'/>" border="0" width="100%" type="circle" style="<c:out value="${visible}" />">

   	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.cantidadProfesionales"/>
		</td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.cantidadProfesionalesStr"/>
				</c:when>
				<c:otherwise>
		      		<html-el:text property="${id}.cantidadProfesionalesStr" maxlength="5" style="text-align:right"/>
			      	<pragmatags:error property="${id}.cantidadProfesionalesStr" />
				</c:otherwise>
			</c:choose>
	    </td>
    </tr>	
   	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.cantidadTecnicos"/>
		</td>
		<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.cantidadTecnicosStr"/>
				</c:when>
				<c:otherwise>
      				<html-el:text property="${id}.cantidadTecnicosStr" maxlength="5" style="text-align:right"/>
		      		<pragmatags:error property="${id}.cantidadTecnicosStr" />
				</c:otherwise>
			</c:choose>
		</td>
    </tr>
   	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.cantidadOperariosCalificados"/>
		</td>
		<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.cantidadOperariosCalificadosStr"/>
				</c:when>
				<c:otherwise>
			      	<html-el:text property="${id}.cantidadOperariosCalificadosStr" maxlength="5" style="text-align:right"/>
      				<pragmatags:error property="${id}.cantidadOperariosCalificadosStr" />
				</c:otherwise>
			</c:choose>
		</td>
    </tr>
   	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.cantidadOperariosNoCalificados"/>
		</td>
		<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.cantidadOperariosNoCalificadosStr"/>
				</c:when>
				<c:otherwise>
			      	<html-el:text property="${id}.cantidadOperariosNoCalificadosStr" maxlength="5" style="text-align:right"/>
	      			<pragmatags:error property="${id}.cantidadOperariosNoCalificadosStr" />
				</c:otherwise>
			</c:choose>
		</td>
    </tr>
</table>