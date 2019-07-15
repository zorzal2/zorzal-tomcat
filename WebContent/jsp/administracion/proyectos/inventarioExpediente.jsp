<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c"%>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters"	prefix="rapidFilters"%>

<script type="text/javascript">
function getId() {
	return '<c:out value="${param.id}" />';
}

</script>

<html:form action="/ExpedienteProyectoGuardar">
<table>
	<table class="formulario">
		<tr>	
			<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.expedienteCuerpo" /></th>
		</tr>	
			<tr>
				<td colspan="2">
				<html-el:link action="ExpedienteProyectoEditar">
					<bean:message bundle="labels" key="app.label.editar"/>		
				</html-el:link><br><br>
				<table id="expedienteTabla" border="0" width="100%" class="inventario">
					<tr>
						<th>Cuerpo</th>
						<th>Folio Desde</th>
						<th>Folio Hasta</th>
	 				 </tr>
	 				 <c:if test="${!empty expedientes}">
					<c:forEach items="${expedientes}" var="expedientes">
					<tr>
						<input id="cuerpo" name="cuerpo" value="${expedientes[0]}" type="hidden"/>
						<input id="folioDesde" name="folioDesde" value="${expedientes[1]}" type="hidden"/>
						<input id="folioHasta" name="folioHasta" value="${expedientes[2]}" type="hidden"/>
						<td><c:out value="${expedientes[0]}"/></td>
						<td><c:out value="${expedientes[1]}"/></td>
						<td><c:out value="${expedientes[2]}"/></td>
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
	<table class="formulario">
		<tr>	
			<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.expedienteMovimiento" /></th>
		</tr>	
		<tr>
			<td colspan="2">
				<c:if test="${empty movimiento}">
					<html-el:link action="ExpedienteMovimiento">
					<bean:message bundle="labels" key="app.label.tomar"/>		
					</html-el:link>				
				</c:if>
				<c:if test="${!empty movimiento}">
					<html-el:link action="ExpedienteMovimientoDevolver">
					<bean:message bundle="labels" key="app.label.devolver"/>		
					</html-el:link>				
				</c:if><br><br>
				<table id="movimientoTabla" border="0" width="100%" class="inventario">
					<tr>
						<th><bean:message bundle="headers" key="app.header.ubicacion" /></th>
						<th>Responsable</th>
						<th>Fecha Retirada</th>
	 				 </tr>
	 				 <c:if test="${!empty movimiento}">
					<c:forEach items="${movimiento}" var="movimiento">
					<tr>
						<input id="idEntidades" name="idEntidades" value="${movimiento[0]}" type="hidden"/>
						<input id="cuits" name="cuits" value="${movimiento[1]}" type="hidden"/>
						<input id="denominaciones" name="denominaciones" value="${movimiento[2]}" type="hidden"/>
						<td><c:out value="${movimiento[0]}"/></td>
						<td><c:out value="${movimiento[1]}"/></td>
						<td><c:out value="${movimiento[2]}"/></td>
					</tr>
					</c:forEach>
					</c:if>
				</table>			
			</td>
			</tr>	
	</table>
</html:form>
