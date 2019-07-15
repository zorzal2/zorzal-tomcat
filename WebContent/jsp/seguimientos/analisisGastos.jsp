<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>

<br>
<logic:present name="analisisDeGastos" scope="request">
	<c:if test="${not empty analisisDeGastos.cuadroDeAnalisisDeGastos}">
		<br/>
		<jsp:include page="cuadroDeAnalisisDeGastos.part.jsp" flush="true">
			<jsp:param name="cuadro" value="analisisDeGastos.cuadroDeAnalisisDeGastos" />
		</jsp:include>
	</c:if>
	<c:if test="${not empty analisisDeGastos.fundamentacionFontar}">
		<br/>
		<div class="titulo">
			<bean:message bundle="headers" key="app.header.fundamentacionEvaluacion" />
		</div>
		<p><bean:write name="analisisDeGastos" property="fundamentacionFontar" ignore="true" /></p>
	</c:if>
	<c:if test="${not empty analisisDeGastos.fundamentacionUFFA}">
		<br/>
		<div class="titulo">
			<bean:message bundle="headers" key="app.header.fundamentacionUFFA" />
		</div>
		<p><bean:write name="analisisDeGastos" property="fundamentacionUFFA" ignore="true" /></p>
	</c:if>
	<c:if test="${not empty analisisDeGastos.cronogramaDeDesembolsos}" >
		<br/>
		<jsp:include page="/jsp/administracion/proyectos/desembolso/cronogramaDeDesembolsos.part.jsp" flush="true">
			<jsp:param name="cronograma" value="analisisDeGastos.cronogramaDeDesembolsos" />
			<jsp:param name="return-url" value="/SeguimientoAnalisisGastosEvaluarGestionDePago.do" />
		</jsp:include>
	</c:if>

	<c:if test="${not empty analisisDeGastos.calculos}" >
		<br>
		<a name="calculos"></a>
		<table class="formulario" style="width: 50%">
			<c:if test="${not empty analisisDeGastos.calculos.presupuestoSegunInformeDeAvance}" >
				<tr>
					<th><bean:message bundle="titles" key="app.title.seguimientos.informeAvance" /></th>
					<td class="money"><c:out value="${analisisDeGastos.calculos.presupuestoSegunInformeDeAvance}" /></td>
					<td>
						<c:if test="${analisisDeGastos.calculos.permiteModificarPresupuestoSegunInformeDeAvance}">
							<pragma:authorize permissions="SEGUIMIENTOS-EDITAR-MONTOS-ANALISIS-DE-GASTOS">
								<a href="#calculos" onclick="javascript: popUpModificarPresupuestoSegunAvance(<c:out value="${analisisDeGastos.idSeguimiento}" />)"><img class="imageButton" src="images/edicion.gif" border=0></a>
							</pragma:authorize>
						</c:if>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty analisisDeGastos.calculos.montoDesembolsado}" >
				<tr>
					<th class="odd"><bean:message bundle="titles" key="app.title.seguimientos.montoDesembolsado" /></th>
					<td class="money odd"><c:out value="${analisisDeGastos.calculos.montoDesembolsado}" /></td>
					<td></td>
				</tr>
			</c:if>
			<c:if test="${not empty analisisDeGastos.calculos.totalRendidoAprobado}" >
				<tr>
					<th><bean:message bundle="titles" key="app.title.seguimientos.totalRendidoAprobado" /></th>
					<td class="money"><c:out value="${analisisDeGastos.calculos.totalRendidoAprobado}" /></td>
					<td></td>
				</tr>
			</c:if>
			<c:if test="${not empty analisisDeGastos.calculos.totalRendidoGestionado}" >
				<tr>
					<th><bean:message bundle="titles" key="app.title.seguimientos.totalRendidoGestionado" /></th>
					<td class="money"><c:out value="${analisisDeGastos.calculos.totalRendidoGestionado}" /></td>
					<td></td>
				</tr>
			</c:if>
			<c:if test="${not empty analisisDeGastos.calculos.pendienteDeRendicion}" >
				<tr>
					<th class="odd"><bean:message bundle="titles" key="app.title.seguimientos.pendienteDeRendicion" /></th>
					<td class="money odd"><c:out value="${analisisDeGastos.calculos.pendienteDeRendicion}" /></td>
					<td>
						<c:if test="${analisisDeGastos.calculos.permiteModificarPendienteDeRendicion}">
							<pragma:authorize permissions="SEGUIMIENTOS-EDITAR-MONTOS-ANALISIS-DE-GASTOS">
								<a href="#calculos" onclick="javascript: popUpModificarPendienteDeRendicion(<c:out value="${analisisDeGastos.idSeguimiento}" />, '<c:out value="${analisisDeGastos.calculos.pendienteDeRendicion}" />')"><img class="imageButton" src="images/edicion.gif" border=0></a>
							</pragma:authorize>
						</c:if>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty analisisDeGastos.calculos.porPagar}" >
				<tr>
					<th><bean:message bundle="titles" key="app.title.seguimientos.aPagar" /></th>
					<td class="money"><c:out value="${analisisDeGastos.calculos.porPagar}" /></td>
					<td></td>
				</tr>
			</c:if>
		</table>
	</c:if>
	
</logic:present>

<c:if test="${!empty messages}">
	<h2><bean-el:message key="${messages}" bundle="informationals"/></h2>
</c:if>
<br>