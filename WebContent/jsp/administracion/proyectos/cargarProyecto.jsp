<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.altaProyecto"/></h3> 

	<%-- Header Cargar Proyecto desde IdeaProyecto o desde Presentacion --%>
	<jsp:include page="/jsp/cabecera/cabecera${clase}.jsp" flush="true"/>

	<!--  Cuerpo Cargar Proyecto desde IdeaProyecto o desde Presentacion -->
	<jsp:include page="cargarProyectoDatos.jsp" flush="true"/>

	<!-- Volver al inventario -->
	<table>
		<tr align="left">
			<td>
				<pragmatags:btnOk javascript="submitForm()"/>	
			</td>
			<td>
				<html-el:link action="${cancelAction}">
					<c:choose> 
					  <c:when test="${submitAction == '/IdeaProyectoProcesarCargarProyecto'}"> 
						  <bean:message bundle="labels" key="app.label.atras"/>
					  </c:when> 
					  <c:when test="${submitAction == '/IdeaProyectoPitecProcesarCargarProyecto'}"> 
						  <bean:message bundle="labels" key="app.label.atras"/>
					  </c:when>
					  <c:otherwise> 
						  <bean:message bundle="labels" key="app.label.cancelar"/>
					  </c:otherwise> 
					</c:choose> 			
				</html-el:link>
			</td>
		</tr>
	</table>	
	
</div>



