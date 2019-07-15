<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<c:if test="${not empty rendiciones}">

	<table class="formulario">
		<tr>
			<th class="titulo">
				<bean:message bundle="headers" key="app.header.totales" />
			</th>
		</tr>
		<c:if test="${rendiciones.noHayContextoDeEncriptacion}">
			<tr>
				<td colspan="3"><h2><bean:message key="app.msj.datoCripto" bundle="informationals"/></h2></td>
			</tr>
		</c:if>
	</table>
	<c:if test="${not empty rendiciones.totalMontoTotalSolicitado}">
		<table class="resumen">
			<c:if test="${not empty rendiciones.totalMontoParteSolicitado}">
				<tr>
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoFontarSolicitado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoParteSolicitado"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty rendiciones.totalMontoContraparteSolicitado}">
				<tr>
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoContraparteSolicitado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoContraparteSolicitado"/>
					</td>
				</tr>
				<tr class="total">
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoSolicitado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoTotalSolicitado"/>
					</td>
				</tr>
			</c:if>
			<!-- Cambia la visualizacion si solo hay totales -->
			<c:if test="${empty rendiciones.totalMontoContraparteSolicitado}">
				<tr>
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoSolicitado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoTotalSolicitado"/>
					</td>
				</tr>
			</c:if>
		</table>
	</c:if>
	<c:if test="${not empty rendiciones.totalMontoTotalAprobado}">
		<table class="resumen">
			<c:if test="${not empty rendiciones.totalMontoParteAprobado}">
				<tr>
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoFontarAprobado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoParteAprobado"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty rendiciones.totalMontoContraparteAprobado}">
				<tr>
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoContraparteAprobado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoContraparteAprobado"/>
					</td>
				</tr>
				<tr class="total">
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoAprobado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoTotalAprobado"/>
					</td>
				</tr>
			</c:if>
			<!-- Cambia la visualizacion si solo hay totales -->
			<c:if test="${empty rendiciones.totalMontoContraparteAprobado}">
				<tr>
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoAprobado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoTotalAprobado"/>
					</td>
				</tr>
			</c:if>
		</table>
	</c:if>
	<c:if test="${not empty rendiciones.totalMontoTotalGestionado}">
		<table class="resumen">
			<c:if test="${not empty rendiciones.totalMontoParteGestionado}">
				<tr>
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoFontarGestionado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoParteGestionado"/>
					</td>
				</tr>
			</c:if>
			<c:if test="${not empty rendiciones.totalMontoContraparteGestionado}">
				<tr>
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoContraparteGestionado"/>:
					</th>
					<td class="money odd">
						<bean:write name="rendiciones" property="totalMontoContraparteGestionado"/>
					</td>
				</tr>
				<tr class="total">
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoGestionado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoTotalGestionado"/>
					</td>
				</tr>
			</c:if>
			<!-- Cambia la visualizacion si solo hay totales -->
			<c:if test="${empty rendiciones.totalMontoContraparteGestionado}">
				<tr>
					<th>
						<bean:message bundle="labels" key="app.label.totalMontoGestionado"/>:
					</th>
					<td class="money">
						<bean:write name="rendiciones" property="totalMontoTotalGestionado"/>
					</td>
				</tr>
			</c:if>
		</table>
	</c:if>
</c:if>