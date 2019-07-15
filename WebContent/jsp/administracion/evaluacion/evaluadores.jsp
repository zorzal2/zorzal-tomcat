<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>


<table id="tablaEvaluador" border="0" style="width:70%" class="inventario">
	<tr>
		<th>Entidad Evaluadora</th>
		<th>Evaluador</th>
		<th>Lugar de evaluación</th>
	 </tr>
	 <c:if test="${!empty evaluacion.evaluadores}">
	<c:forEach items="${evaluacion.evaluadores}" var="evaluador">
	<tr>
		<input id="idEntidadEvaluadora" name="idEntidadEvaluadora" value="${evaluador.idEntidadEvaluadora}" type="hidden"/>
		<input id="idEvaluador" name="idEvaluador" value="${evaluador.idEvaluador}" type="hidden"/>
		<input id="lugar" name="lugar" value="${evaluador.lugar}" type="hidden"/>										
		<td><c:out value="${evaluador.entidadEvaluadora}"/></td>
		<td><c:out value="${evaluador.evaluador}"/></td>
		<td><c:out value="${evaluador.lugar}"/></td>
	</tr>
	</c:forEach>
	</c:if>
</table>	