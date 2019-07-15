<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.juntaEvaluacion"/></h3> 

	<%-- Header Evaluar por Junta a Idea Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraIdeaProyecto.jsp" flush="true"/>

	<c:if test="${empty evaluacionesAbiertas}">
		<!--  Cuerpo Evaluar por Junta a Idea Proyecto -->
		<jsp:include page="evaluarPorJuntaDatos.jsp" flush="true"/>
	
		<!-- Volver al inventario -->
		<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>	
	</c:if>
	<!-- Hay evaluaciones abiertas -->
	<c:if test="${!empty evaluacionesAbiertas}">
		<span class="error">
			<bean:message bundle="errors" key="app.proyecto.noEvJuntaIPSiguientesEvalsAbiertas"/>
			<br/>
			<br/>
			<table class="inventario" style="width:20%">
				<c:forEach items="${evaluacionesAbiertas}" var="evaluacionAbierta">
					<tr>
						<td>
							<span style="margin-bottom: 100px"><bean:write name="evaluacionAbierta" /></span>
						</td>
						<td style="width:17px">
							<img onclick="window.location.href='VisualizarEvaluacion.do?id=<bean:write name="evaluacionAbierta" />'" class="imageButton" src="images/visualizar.gif" title="<bean:message key="app.title.visualizarEvaluacion" bundle="titles" />"/>
						</td>
					</tr>
				</c:forEach>
			</table>
		</span>
		<br/>
		<pragmatags:btnCerrar/>	
	</c:if>
</div>
