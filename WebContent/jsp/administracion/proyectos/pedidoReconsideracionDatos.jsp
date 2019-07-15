<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<html:form action="/RegistrarPedidoReconsideracionGuardar">
<html:hidden property="idProyecto"/>
<table class="formulario">
	<tr>
		<td class="obligatorio" width="20%">
			<bean:message bundle="labels" key="app.label.fechaReconsideracion"/>
		</td>
		<td colspan="2">
			<pragmatags:calendar propertyName="fecha" top="0" left="0" />
			<pragmatags:error property="fecha" />
		</td>
	</tr>	
	<tr>	
		<table class="formulario">
			<tr>	
				<table class="inventario">
					<tr>	
						<th class="titulo"><bean:message bundle="headers" key="app.header.evaluaciones"/></th>
						<th class="titulo"><bean:message bundle="headers" key="app.header.resultadoEvaluacion"/></th>
					</tr>	
					<c:if test="${!empty evaluadores}">
					<c:forEach items="${evaluadores}" var="evaluador">
					<tr>
						<td><c:out value="${evaluador.evaluaciones}"/></td>
						<td><c:out value="${evaluador.resultado}"/>
						<!-- a href="VisualizarEvaluacion.do?id=<c:out value='${evaluador.idEvaluacion}'/>" > Ver Evaluacion </a-->
						</td>
					</tr>	
					</c:forEach>
					</c:if>
				</table>
			</tr>
		</table>
	</tr>
</table>
<br>
<table class="formulario">		
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</th>
	</tr>
	<tr>
		<td>
	   		<html:textarea property="observacion" rows="4" cols="100" />
			<pragmatags:error property="observacion" />
   		</td>
	</tr>	
</table>
</html:form>