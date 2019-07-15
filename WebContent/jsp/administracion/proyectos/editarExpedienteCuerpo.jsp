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
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
	<tr>
		<td colspan="2">
			<html-el:link href="#" onclick="popUpExpedienteCuerpo(getId());">
			<bean:message bundle="labels" key="app.label.agregar"/>		
			</html-el:link>				
		</td>
	</tr>
</table>
	<table class="formulario">
		<tr>	
			<th colspan="2" class="titulo"><bean:message bundle="headers" key="app.header.expedienteCuerpo" /></th>
		</tr>	
			<tr>
				<td colspan="2">
				<table id="expedienteTabla" border="0" width="100%" class="inventario">
					<tr>
						<th>Cuerpo</th>
						<th>Folio Desde</th>
						<th>Folio Hasta</th>
						<th><bean:message bundle="headers" key="app.header.acciones" /></th>
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
						<td><button name="Delete" onclick="deleteRow(this , 'expedienteTabla')"><img src="images/eliminar.gif"/></button></td>					
					</tr>
					</c:forEach>
					</c:if>
				</table>			
				
				</td>
			</tr>	
	</table>
<pragmatags:btnOk javascript="submitForm()"/>
</html:form>
