<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<c:if test="${not empty resumenDePresupuesto}">
	<table class="formulario">
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="titles" key="app.title.presupuesto" />
			</th>
		</tr>
		<c:if test="${resumenDePresupuesto.requiereContextoDeEncriptacion}">
			<tr>
				<td colspan="2"><h2><bean:message key="app.msj.datoCripto" bundle="informationals"/></h2></td>
			</tr>
		</c:if>
		
		<c:if test="${not resumenDePresupuesto.requiereContextoDeEncriptacion}">
			<tr>
				<td colspan="2">
					<!--  Montos -->
					<table class="inventario" style="width:70%">
						<tr>
							<c:if test="${resumenDePresupuesto.desplegarEnParteYContraparte}">
								<c:if test="${not empty resumenDePresupuesto.montoFontarSolicitado }">
								<th><bean:message bundle="headers" key="app.header.montoParteSolicitado"/></th>
								</c:if>
								<c:if test="${not empty resumenDePresupuesto.montoContraparteSolicitado }">
								<th><bean:message bundle="headers" key="app.header.montoContraparteSolicitado"/></th>
								</c:if>
							</c:if>
							<c:if test="${not empty resumenDePresupuesto.montoTotalSolicitado }">
								<th><bean:message bundle="headers" key="app.header.montoTotalSolicitado"/></th>
							</c:if>
							<c:if test="${resumenDePresupuesto.desplegarEnParteYContraparte}">
								<c:if test="${not empty resumenDePresupuesto.montoFontarAprobado }">
									<th><bean:message bundle="headers" key="app.header.montoParteAprobado"/></th>
								</c:if>
								<c:if test="${not empty resumenDePresupuesto.montoContraparteAprobado }">
									<th><bean:message bundle="headers" key="app.header.montoContraparteAprobado"/></th>
								</c:if>
							</c:if>
							<c:if test="${not empty resumenDePresupuesto.montoTotalAprobado}">
								<th><bean:message bundle="headers" key="app.header.montoTotalAprobado"/></th>
							</c:if>
						</tr>
						<tr>
							<c:if test="${resumenDePresupuesto.desplegarEnParteYContraparte}">
								<c:if test="${not empty resumenDePresupuesto.montoFontarSolicitado }">
									<td class="money">
										<pragmatags:monto numero="${resumenDePresupuesto.montoFontarSolicitado}"/>
									</td>
								</c:if>
								<c:if test="${not empty resumenDePresupuesto.montoContraparteSolicitado }">
									<td class="money">
										<pragmatags:monto numero="${resumenDePresupuesto.montoContraparteSolicitado}"/>
									</td>
								</c:if>
							</c:if>
							<c:if test="${not empty resumenDePresupuesto.montoTotalSolicitado }">
								<td class="money">
									<pragmatags:monto numero="${resumenDePresupuesto.montoTotalSolicitado}"/>
								</td>
							</c:if>
							<c:if test="${resumenDePresupuesto.desplegarEnParteYContraparte}">
								<c:if test="${not empty resumenDePresupuesto.montoFontarAprobado }">
									<td class="money">
										<pragmatags:monto numero="${resumenDePresupuesto.montoFontarAprobado}"/>
									</td>
								</c:if>
								<c:if test="${not empty resumenDePresupuesto.montoContraparteAprobado }">
									<td class="money">
										<pragmatags:monto numero="${resumenDePresupuesto.montoContraparteAprobado}"/>
									</td>
								</c:if>
							</c:if>
							<c:if test="${not empty resumenDePresupuesto.montoTotalAprobado}">
								<td class="money">
									<pragmatags:monto numero="${resumenDePresupuesto.montoTotalAprobado}"/>
								</td>
							</c:if>
						</tr>
					</table> 
				</td>
			</tr>
		</c:if>
	</table>
</c:if>