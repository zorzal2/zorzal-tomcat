<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%-- 

Vista del cuadro de analisis de gastos.
Parametros:
cuadro: nombre del atributo que contiene un CuadroDeAnalisisDeGastosDTO
 --%>
<% 
	request.setAttribute("__cuadro", com.pragma.util.WebUtils.getComplexAttribute(request, request.getParameter("cuadro")));
%>
<div class="titulo">
	<bean:message bundle="titles" key="app.title.analisisGastos" />
</div>
<table class="inventario">
	<%-- Fila de Conceptos --%>
	<tr>
		<%-- Casilla de "concepto" --%>
		<c:if test="${__cuadro.desplegarEnParteYContraparte}">
			<th rowspan="2">
				<bean:message bundle="headers" key="app.header.concepto" />
			</th>
		</c:if>
		<c:if test="${not __cuadro.desplegarEnParteYContraparte}">
			<th>
				<bean:message bundle="headers" key="app.header.concepto" />
			</th>
		</c:if>
		
		<%-- Conceptos: Costos totales, Monto Rendido anteriormente, etc --%>
		<c:forEach items="${__cuadro.analisisPorConcepto}" var="porConcepto">
			<c:if test="${__cuadro.desplegarEnParteYContraparte}">
				<th colspan="3">
					<bean:message bundle="codes" key="${porConcepto.concepto.key}"/>
				</th>
			</c:if>
			<c:if test="${not __cuadro.desplegarEnParteYContraparte}">
				<th>
					<bean:message bundle="codes" key="${porConcepto.concepto.key}"/>
				</th>
			</c:if>
		</c:forEach>
		
		<%-- Avance --%>
		<c:if test="${__cuadro.desplegarEnParteYContraparte}">
			<th rowspan="2">
				<bean:message bundle="headers" key="app.header.avance" />
			</th>
		</c:if>
		<c:if test="${not __cuadro.desplegarEnParteYContraparte}">
			<th>
				<bean:message bundle="headers" key="app.header.avance" />
			</th>
		</c:if>
	</tr>

	<%-- Encabezados de Costos Fontar / Contraparte / Totales --%>
	<c:if test="${__cuadro.desplegarEnParteYContraparte}">
		<tr>
			<c:forEach items="${__cuadro.analisisPorConcepto}" var="porConcepto">
				<th>
					<bean:message bundle="headers" key="app.header.rendicionParte" />
				</th>
				<th>
					<bean:message bundle="headers" key="app.header.rendicionContraparte" />
				</th>
				<th>
					<bean:message bundle="headers" key="app.header.rendicionTotal" />
				</th>
			</c:forEach>
		</tr>
	</c:if>
	<%-- Datos por rubro --%>
	<c:forEach begin="0" end="${fn:length(__cuadro.rubros)-1}" var="i">
		<tr>
			<%-- nombre --%>
			<th><c:out value="${__cuadro.rubros[i].nombre}"/></th>
			<%-- Valores --%>
			<c:forEach items="${__cuadro.analisisPorConcepto}" var="porConcepto">
				<c:if test="${__cuadro.desplegarEnParteYContraparte}">
					<td class="money">
						<pragmatags:monto numero="${porConcepto.items[i].montoFontar}" />
					</td>
					<td class="money">
						<pragmatags:monto numero="${porConcepto.items[i].montoContraparte}" />
					</td>
				</c:if>
				<td class="money">
					<pragmatags:monto numero="${porConcepto.items[i].costoTotal}" />
				</td>
			</c:forEach>
			<%-- avance --%>
			<td class="money"><pragmatags:monto numero="${__cuadro.porcentajeDeAvance[i]}" sufijo="%"  /></td>
		</tr>
	</c:forEach>
	<%-- Fila de totales --%>
	<tr>
		<%-- titulo --%>
		<th><bean:message bundle="headers" key="app.header.rendicionTotal" /></th>
		<%-- Valores --%>
		<c:forEach items="${__cuadro.analisisPorConcepto}" var="porConcepto">
			<c:if test="${__cuadro.desplegarEnParteYContraparte}">
				<th class="money">
					<pragmatags:monto numero="${porConcepto.montoFontarTotal}" />
				</th>
				<th class="money">
					<pragmatags:monto numero="${porConcepto.montoContraparteTotal}" />
				</th>
			</c:if>
			<th class="money">
				<pragmatags:monto numero="${porConcepto.montoTotal}" />
			</th>
		</c:forEach>
		<%-- avance --%>
		<th class="money"><pragmatags:monto numero="${__cuadro.porcentajeTotalDeAvance}" sufijo="%"  /></th>
	</tr>
	<%-- Fila de porcentajes --%>
	<c:if test="${__cuadro.desplegarEnParteYContraparte}">
		<tr>
			<%-- titulo --%>
			<th><bean:message bundle="headers" key="app.header.composicionPorcentual" /></th>
			<%-- Valores --%>
			<c:forEach items="${__cuadro.analisisPorConcepto}" var="porConcepto">
				<th class="money">
					<pragmatags:monto numero="${porConcepto.porcentajeFontar}" sufijo="%" />
				</th>
				<th class="money">
					<pragmatags:monto numero="${porConcepto.porcentajeContraparte}" sufijo="%" />
				</th>
				<th class="money">
					<%-- pragmatags:monto numero="${porConcepto.porcentajeTotal}" /--%>
					100%
				</th>
			</c:forEach>
			<%-- dummy --%>
			<td></td>
		</tr>
	</c:if>
	<%-- Consolidados --%>
	<c:if test="${__cuadro.desplegarEnParteYContraparte}">
		<tr>
			<c:set var="colspan" value="${1}"/>
			<c:forEach items="${__cuadro.analisisPorConcepto}" var="porConcepto">
				<c:if test="${empty porConcepto.consolidadoTotal}">
					<c:set var="colspan" value="${colspan+1}"/>
					<c:if test="${__cuadro.desplegarEnParteYContraparte}">
						<c:set var="colspan" value="${colspan+2}"/>
					</c:if>
				</c:if>
			</c:forEach>
			<%-- titulo --%>
			<th style="text-align: right; padding-right: 5px;" colspan="<c:out value="${colspan}"/>"><bean:message bundle="headers" key="app.header.consolidado" /></th>
			<c:forEach items="${__cuadro.analisisPorConcepto}" var="porConcepto">
				<c:if test="${not empty porConcepto.consolidadoTotal}">
					<c:if test="${__cuadro.desplegarEnParteYContraparte}">
						<th class="money">
								<pragmatags:monto numero="${porConcepto.consolidadoFontar}" sufijo="%" />
						</th>
						<th class="money">
								<pragmatags:monto numero="${porConcepto.consolidadoContraparte}" sufijo="%" />
						</th>
					</c:if>
					<th class="money">
							100%
							<!--  pragmatags:monto numero="${porConcepto.consolidadoTotal}" sufijo="%" / -->
					</th>
				</c:if>
			</c:forEach>
			<%-- dummy --%>
			<td></td>
		</tr>
	</c:if>
</table>
<%-- Diferencia Aprobado-gestionado --%>
<c:if test="${not empty __cuadro.porGestionarTotal}">
	<br>
	<table class="inventario" style="width: 40%">
		<tr>
			<th colspan="3">Diferencia Aprobado Fontar - Gestionado UFFA</th>
		</tr>
		<c:if test="${__cuadro.desplegarEnParteYContraparte}">
			<tr>
				<th><bean:message bundle="headers" key="app.header.rendicionParte" /></th>
				<td class="money"><pragmatags:monto numero="${__cuadro.porGestionarFontar}" /></td>
			</tr>
			<tr>
				<th><bean:message bundle="headers" key="app.header.rendicionContraparte" /></th>
				<td class="money"><pragmatags:monto numero="${__cuadro.porGestionarContraparte}" /></td>
			</tr>
		</c:if>	
		<tr>
			<th><bean:message bundle="headers" key="app.header.rendicionTotal" /></th>
			<td class="money"><pragmatags:monto numero="${__cuadro.porGestionarTotal}" /></td>
		</tr>
	</table>
</c:if>