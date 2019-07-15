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


<c:choose>
	<c:when test="${esVisualizacion}">
		<button onclick="javascript:switchLayer('<c:out value="${id}"/>');">...</button>
	</c:when>
	<c:otherwise>
		<button onfocus="javascript:paisArgentina();" onclick="javascript:switchLayerLocalizacion('<c:out value="${id}"/>','2');">...</button>
	</c:otherwise>
</c:choose>

<div id='viewLocal'>
</div>
<table class="mini_detail" id="<c:out value='${id}'/>" border="0" width="100%" type="circle" style="<c:out value="${visible}" />">

	<tr>
 		<td class="obligatorio">
 			<bean:message bundle="labels" key="app.label.direccion"/>
 		</td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.direccion"/>
				</c:when>
				<c:otherwise>
		      		<html-el:text property="${id}.direccion" maxlength="500" />
		      		<pragmatags:error property="${id}.direccion" />
				</c:otherwise>
			</c:choose>
		</td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.localidad"/></td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.localidad"/>
				</c:when>
				<c:otherwise>
			      	<html-el:text property="${id}.localidad" maxlength="500" />
		      		<pragmatags:error property="${id}.localidad" />
				</c:otherwise>
			</c:choose>
      	</td>
    </tr>
   	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.departamento"/></td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.departamento"/>
				</c:when>
				<c:otherwise>
		      		<html-el:text property="${id}.departamento" maxlength="500" />
		      		<pragmatags:error property="${id}.departamento" />
				</c:otherwise>
			</c:choose>
      	</td>
    </tr>
   	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.codigoPostal"/></td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.codigoPostal"/>
				</c:when>
				<c:otherwise>
			      	<html-el:text property="${id}.codigoPostal" maxlength="500" />
			      	<pragmatags:error property="${id}.codigoPostal" />
				</c:otherwise>
			</c:choose>
      	</td>
    </tr>
  	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.pais"/></td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.pais"/>
				</c:when>
				<c:otherwise>
		      		<html-el:text property="${id}.pais" maxlength="500" />
			      	<pragmatags:error property="${id}.pais" />
				</c:otherwise>
			</c:choose>
      	</td>
    </tr>
	   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.idJurisdiccion"/></td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.nombreJurisdiccion"/>
				</c:when>
				<c:otherwise>
			      	<html-el:select property="${id}.idJurisdiccion">
						<!-- Esto se aplica para cuando se carga la pagina sin jurisdicciones -->
						<c:if test="${not empty jurisdicciones}">
		 					<html:options collection="jurisdicciones" property="value" labelProperty="label" />
						</c:if>
					</html-el:select>
					<pragmatags:error property="${id}.idJurisdiccion" />
				</c:otherwise>
			</c:choose>
		</td>
    </tr>    
   	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.telefono"/></td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.telefono"/>
				</c:when>
				<c:otherwise>
		 	     	<html-el:text property="${id}.telefono" maxlength="500" />
		      		<pragmatags:error property="${id}.telefono" />
				</c:otherwise>
			</c:choose>
      	</td>
    </tr>
   	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.fax"/></td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.fax"/>
				</c:when>
				<c:otherwise>
			      	<html-el:text property="${id}.fax" maxlength="500" />
		      		<pragmatags:error property="${id}.fax" />
				</c:otherwise>
			</c:choose>
      	</td>
    </tr>      
   	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.email"/></td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.email"/>
				</c:when>
				<c:otherwise>
		      		<html-el:text property="${id}.email" maxlength="500" />
		      		<pragmatags:error property="${id}.email" />
				</c:otherwise>
			</c:choose>
      	</td>
    </tr>  
   	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.paginaWeb"/></td>
      	<td>
			<c:choose>
				<c:when test="${esVisualizacion}">
					<bean:write name="${nombreForm}" property="${id}.paginaWeb"/>
				</c:when>
				<c:otherwise>
			      	<html-el:text property="${id}.paginaWeb" maxlength="500" />
		      		<pragmatags:error property="${id}.paginaWeb" />
				</c:otherwise>
			</c:choose>
      	</td>
    </tr>
</table>