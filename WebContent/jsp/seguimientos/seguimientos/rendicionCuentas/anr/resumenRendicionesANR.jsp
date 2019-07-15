<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<br>
<br>

<c:choose>
	<c:when test="${!empty resumenRendicionesList}">
		<table class="inventario">
			<logic:present name="resumenRendicionesList" scope="request"> 	 
				<tr>
					<th>
						<bean:message bundle="headers" key="app.header.rubros" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.costoTotalSolicitado" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.montoParteSolicitado" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.montoContraparteSolicitado" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.costoTotalAprobadoOGestionado" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.montoParteAprobadoOGestionado" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.montoContraparteAprobadoOGestionado" />
					</th>
				</tr>
				<logic:iterate name="resumenRendicionesList" id="resumenRendicion" indexId="index">
				   	<tr>
				      	<td>
				      		<bean:write name="resumenRendicion" property="nombreRubro"/>
				      	</td>
						<%-- Solicitado --%>
				      	<td align="right">
							<bean:write name="resumenRendicion" property="solicitado.wrappedCostoTotal"/>
				      	</td>
				      	<td align="right">
					      	<bean:write name="resumenRendicion" property="solicitado.wrappedMontoParte"/>
				      	</td>
				      	<td align="right">
					      	<bean:write name="resumenRendicion" property="solicitado.wrappedMontoContraparte"/>
				      	</td>
						<%-- Aprobados --%>
				      	<td align="right">
							<bean:write name="resumenRendicion" property="aprobado.wrappedCostoTotal"/>
				      	</td>
				      	<td align="right">
					      	<bean:write name="resumenRendicion" property="aprobado.wrappedMontoParte"/>
				      	</td>
				      	<td align="right">
					      	<bean:write name="resumenRendicion" property="aprobado.wrappedMontoContraparte"/>
				      	</td>
				      </tr>
				</logic:iterate>
				<tr>
					<th>
						<bean:message bundle="headers" key="app.header.total" />
					</th>
					<%-- Solicitado --%>
					<th style="align:right">
						<div align="right" >
							<c:out value="${costoSolicitadoTotalAcumulado}"/>
						</div>
					</th>
					<th align="right">
						<div align="right">
							<c:out value="${montoSolicitadoParteAcumulado}"/>
						</div>
					</th>
					<th align="right">
						<div align="right">
							<c:out value="${montoSolicitadoContraparteAcumulado}"/>
						</div>
					</th>
					<%-- Aprobado --%>
					<th style="align:right">
						<div align="right" >
							<c:out value="${costoAprobadoTotalAcumulado}"/>
						</div>
					</th>
					<th align="right">
						<div align="right">
							<c:out value="${montoAprobadoParteAcumulado}"/>
						</div>
					</th>
					<th align="right">
						<div align="right">
							<c:out value="${montoAprobadoContraparteAcumulado}"/>
						</div>
					</th>
				</tr>
			</logic:present>
		</table>
	</c:when>		
	<c:otherwise>
		<h2><bean:message key="app.msj.noHayRendiciones" bundle="informationals"/></h2>
	</c:otherwise>
</c:choose>

<br>
<br>

