<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c"%>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters"	prefix="rapidFilters"%>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<script type="text/javascript">
function editObserv(observ,esEtapa,id,avance) {

//	alert(esEtapa);
//	var nombre = document.getElementsByName('idTipoProyecto')[0].options[document.getElementsByName('idTipoProyecto')[0].selectedIndex].text;
	var url= "ObservacionEditar.do?observaciones="+observ+"&id="+id+"&esEtapa="+esEtapa+"&avance="+avance;
//		AbrirPopupBloqueante(url,'700','580');
 	window.open(url,"ObservacionEditarDynaForm","width=700,height=350,left=100,top=70,resizable=yes,status=yes,help=yes,scrollbars=1");
}

</script>

<html:form action="/GuardarPlanSeguimiento">
<html:hidden property="id"/>	
<table>
	<table class="formulario">
		<tr>	
			<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.planProyecto" /></th>
		</tr>	
			<tr>
				<td colspan="2">
				<table id="expedienteTabla" border="0" width="100%" class="inventario">
					<tr>
						<th>Etapa / Actividad</th>
						<th>Descripción</th>
						<th>Fecha Inicio</th>
						<th>Fecha Fin</th>
						<th>Avance</th>
						<th>Observaciones</th>
						<pragma:actionAuthorize permission="${sessionScope['ACTION_AUTHORIZE']}">
							<th>Acciones</th>
						</pragma:actionAuthorize>
					</tr>
	 				 <c:if test="${!empty etapas}">
					<c:forEach items="${etapas}" var="etapas">
					<tr>
					<c:choose>
						<c:when test="${etapas[6] == 'true'}">
							<td class="negrita"><c:out value="${etapas[0]}"/></td>
							<td class="negrita"><c:out value="${etapas[1]}"/></td>
							<td class="negrita"><c:out value="${etapas[2]}"/></td>
							<td class="negrita"><c:out value="${etapas[3]}"/></td>
							<td class="negrita"><c:out value="${etapas[4]}"/></td>
							<td class="negrita"><c:out value="${etapas[5]}"/></td>
						</c:when>
						<c:otherwise>
							<td><c:out value="${etapas[0]}"/></td>
							<td><c:out value="${etapas[1]}"/></td>
							<td><c:out value="${etapas[2]}"/></td>
							<td><c:out value="${etapas[3]}"/></td>
							<td><c:out value="${etapas[4]}"/></td>
							<td><c:out value="${etapas[5]}"/></td>
						</c:otherwise>
					</c:choose>
					<pragma:actionAuthorize permission="${sessionScope['ACTION_AUTHORIZE']}">
						<td align="center">
							<button name="..." onclick="editObserv('${etapas[5]}','${etapas[6]}','${etapas[7]}','${etapas[4]}');">...
							</button>
						</td>
					</pragma:actionAuthorize>
					</tr>
					</c:forEach>
					</c:if>
				</table>			
				
				</td>
			</tr>	
	</table>
	<table>
	<br>
</table>
</html:form>
