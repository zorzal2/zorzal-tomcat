<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<script>
function verItems(localizacionPresupuesto, rubroId) {
	var url= "PresupuestoDetalleRubrosANR.do?idRubro="+rubroId+"&localizacionPresupuesto="+localizacionPresupuesto;
 	window.open(url,"PresupuestoDetalleRubros","width=750,height=580,left=100,top=70,resizable=yes,status=yes,help=yes,scrollbars=1");
}
</script>

<c:choose>
	<c:when test="${empty requestScope.proyecto.duracion}">
		<c:set var="rubroPeriodoKey" value="app.title.rubro_periodo" />
	</c:when>
	<c:when test="${requestScope.proyecto.duracion <= 12}">
		<c:set var="rubroPeriodoKey" value="app.title.rubro_mes" />
	</c:when>
	<c:when test="${requestScope.proyecto.duracion <= 24}">
		<c:set var="rubroPeriodoKey" value="app.title.rubro_bimestre" />
	</c:when>
	<c:when test="${requestScope.proyecto.duracion <= 36}">
		<c:set var="rubroPeriodoKey" value="app.title.rubro_trimestre" />
	</c:when>
	<c:when test="${requestScope.proyecto.duracion <= 48}">
		<c:set var="rubroPeriodoKey" value="app.title.rubro_cuatrimestre" />
	</c:when>
	<c:when test="${requestScope.proyecto.duracion > 12 and requestScope.proyecto.duracion <= 24}">
		<c:set var="rubroPeriodoKey" value="app.title.rubro_mes" />
		bimestre
	</c:when>
</c:choose>

<bean:define id="rubroPeriodo" >
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${rubroPeriodoKey}" />
	</fmt:bundle>
</bean:define>
<c:set var="rubroKey" value="app.label.rubro" />
<bean:define id="rubro" >
	<fmt:bundle basename="resources.FieldLabels">
		<fmt:message key="${rubroKey}" />
	</fmt:bundle>
</bean:define>

<c:set var="costoTotalKey" value="app.label.presupuesto.costoTotal" />
<bean:define id="costoTotal" >
	<fmt:bundle basename="resources.FieldLabels">
		<fmt:message key="${costoTotalKey}" />
	</fmt:bundle>
</bean:define>
<c:set var="costoContraparteKey" value="app.label.presupuesto.costoContraparte" />
<bean:define id="costoContraparte" >
	<fmt:bundle basename="resources.FieldLabels">
		<fmt:message key="${costoContraparteKey}" />
	</fmt:bundle>
</bean:define>
<c:set var="costoParteKey" value="app.label.presupuesto.costoParte" />
<bean:define id="costoParte" >
	<fmt:bundle basename="resources.FieldLabels">
		<fmt:message key="${costoParteKey}" />
	</fmt:bundle>
</bean:define>

<c:set var="accionesKey" value="app.header.acciones" />
<bean:define id="acciones" >
	<fmt:bundle basename="resources.TableHeader">
		<fmt:message key="${accionesKey}" />
	</fmt:bundle>
</bean:define>

<table width="100%">
	<tr>
		<th class="titulo"><bean:message bundle="titles" key="app.title.detalleDeGastos"/></th> 
	</tr>
	<tr>
		<td>&nbsp;</td> 
	</tr>
	<tr>
		<td>
			<display-el:table name="presupuesto.presupuestoRubrosRaiz" class="inventario">
				<display-el:column title="${rubro}" property="rubro.nombre" />   
				<%-- display-el:column title="${acciones}" property="detalleLink" /--%>
				<display-el:column style="text-align: center" title=" " property="detalleLink" />
				<display-el:column  style="text-align: right" title="${costoParte}" property="montoParte" />
				<display-el:column style="text-align: right" title="${costoContraparte}" property="montoContraparte"  />
				<display-el:column style="text-align: right" title="${costoTotal}" property="montoTotal"  />

				<display:footer>
					<tr>
				  		<td style="text-align: left" colspan="2" class="title"><bean:message bundle="titles" key="app.title.totales"/></td>
				  		<td style="text-align: right"><bean:write name="presupuesto" property="montoParte" /></td>
				  		<td style="text-align: right"><bean:write name="presupuesto" property="montoContraparte" /></td>
				  		<td style="text-align: right"><bean:write name="presupuesto" property="total" /></td>
					<tr>
					<tr>
				  		<td style="text-align: left" colspan="2" class="title"><bean:message bundle="titles" key="app.title.porcentajes"/></td>
				  		<td style="text-align: right"><bean:write name="presupuesto" property="porcentajeParte" /></td>
				  		<td style="text-align: right"><bean:write name="presupuesto" property="porcentajeContraparte" /></td>
				  		<td style="text-align: right"><bean:write name="presupuesto" property="porcentajeTotal" /></td>
					<tr>
				</display:footer>
			</display-el:table>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td> 
	</tr>
	<c:if test="${!empty presupuesto.flujo}">
		<tr>
			<th class="titulo"><bean:message bundle="titles" key="app.title.flujoDeFondos"/></th> 
		</tr>
		<tr>
			<td class="obligatorio"><bean:message bundle="titles" key="app.title.parte"/></td> 
		</tr>
		<tr>
			<td>
				<display-el:table name="presupuesto.flujo.flujoParte" class="flujo">
					<display-el:column headerClass="rubro"   title="${rubroPeriodo}" property="titulo" />
					<display-el:column headerClass="periodo" style="text-align: right" title="1" property="valores[0]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="2" property="valores[1]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="3" property="valores[2]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="4" property="valores[3]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="5" property="valores[4]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="6" property="valores[5]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="7" property="valores[6]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="8" property="valores[7]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="9" property="valores[8]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="10" property="valores[9]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="11" property="valores[10]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="12" property="valores[11]" />
					<display:footer>
						<tr>
					  		<td style="text-align: left" class="title"><bean:message bundle="titles" key="app.title.totales"/></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[0]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[1]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[2]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[3]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[4]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[5]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[6]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[7]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[8]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[9]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[10]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesParte.valores[11]" /></td>
						<tr>
					</display:footer>
				</display-el:table>
			</td>
		</tr>
		<tr>
			<td class="obligatorio"><bean:message bundle="titles" key="app.title.contraparte"/></td>
		</tr>
		<tr>
			<td>
				<display-el:table name="presupuesto.flujo.flujoContraparte" class="flujo">
					<display-el:column headerClass="rubro"   title="${rubroPeriodo}" property="titulo" />
					<display-el:column headerClass="periodo" style="text-align: right" title="1" property="valores[0]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="2" property="valores[1]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="3" property="valores[2]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="4" property="valores[3]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="5" property="valores[4]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="6" property="valores[5]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="7" property="valores[6]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="8" property="valores[7]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="9" property="valores[8]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="10" property="valores[9]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="11" property="valores[10]" />
					<display-el:column headerClass="periodo" style="text-align: right" title="12" property="valores[11]" />
					<display:footer>
						<tr>
					  		<td style="text-align: left" class="title"><bean:message bundle="titles" key="app.title.totales"/></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[0]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[1]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[2]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[3]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[4]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[5]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[6]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[7]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[8]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[9]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[10]" /></td>
					  		<td style="text-align: right"><bean:write name="presupuesto" property="flujo.totalesContraparte.valores[11]" /></td>
						<tr>
					</display:footer>
				</display-el:table>
			</td>
		</tr>
		<tr>
			<td class="opcional"><bean:message bundle="titles" key="app.title.enPesos"/></th> 
		</tr>
	</c:if>
</table>