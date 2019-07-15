<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<h3>
	<bean:message bundle="titles" key="app.title.pac"/>
</h3>
<html:form action="/ProyectoPACInventarioFiltrar">
	<c:if test="${!empty presupuesto}">
		<table class="filtros">
			<tr>
				<td>
				<rapidFilters:comboFilter property="codigoEstado" 
										  title="app.label.estado" 
										  filterType="fontar.filter.type.object.sin_anulados" 
										  classNameType="java.lang.String" 
										  options="estados" 
										  labelProperty="label" 
										  valueProperty="value"/>
				</td>
			
				<td>
				<rapidFilters:comboFilter property="rubro.nombre" 
										  title="app.label.rubro" 
										  filterType="filter.type.string.contains" 
										  classNameType="java.lang.String" 
										  options="rubros" 
										  labelProperty="label" 
										  valueProperty="value"/>
				</td>
				<td>                                  
				<rapidFilters:comboFilter property="tipoAdquisicion.descripcion" 
										  title="app.label.adquisicion" 
										  filterType="filter.type.string.equal" 
										  classNameType="java.lang.String" 
										  options="adquisiciones" 
										  labelProperty="label" 
										  valueProperty="value"/>
				</td>

				<td>
					<rapidFilters:applyFilters />
				</td>
				<td>
					<rapidFilters:clearFilters />
				</td>
			</tr>
		</table>
		<!-- Mensaje informatio -->
		<c:if test="${!empty message}">
			<pragma:infoDisplay showBody="true"><bean:write name="message"/></pragma:infoDisplay>
		</c:if>
		<!-- Fin de Mensaje informatio -->
		<br>
			<html:link action="ProyectoPACAgregar.do">
				<bean:message bundle="titles" key="app.title.agregar"/>	
			</html:link>
			<c:if test="${permitirCargaDesdeExcel}">
				<html:link action="PacExcelIngresar.do">
					<bean:message bundle="titles" key="app.title.pac.cargarDesdeExcel"/>	
				</html:link>
			</c:if>
		<toolbar:table model="list" requestURI="/ProyectoPACInventarioFiltrar.do" />	
		
		<br>
			<br>
			<table border="0" class="inventario"  style="width:250px">
			<thead>
				<tr>
					<th nowrap ><bean:message bundle="labels" key="app.label.item"/></th>
					<th nowrap align="center"><bean:message bundle="labels" key="app.label.montoParte" /></th>
					<th nowrap align="center"><bean:message bundle="labels" key="app.label.montoContraparte" /></th>
				</tr>
			</thead>
			<tbody>
				<tr class="odd">
					<td><bean:message bundle="labels" key="app.label.montoPendiente"/></td>
					<td align="right"><bean:write name="pac" property="montoPendiente"/></td>
					<td align="center">-</td>
				</tr>
				<tr class="even">
					<td nowrap><bean:message bundle="labels" key="app.label.montoProceso"/></td>
					<td align="right"><bean:write name="pac" property="montoProceso"/></td>
					<td align="center">-</td>
				</tr>
				<tr class="odd">
					<td nowrap><bean:message bundle="labels" key="app.label.montoAdjudicacionDesembolso"/></td>
					<td align="right"><bean:write name="pac" property="montoAdjudicacionDesembolso"/></td>
					<td align="center">-</td>
				</tr>
				<tr class="even">
					<td nowrap><bean:message bundle="labels" key="app.label.desembolsado"/></td>
					<td align="right"><bean:write name="pac" property="montoDesembolsado"/></td>
					<td align="center">-</td>
				</tr>
				<tr class="odd">
					<td><bean:message bundle="labels" key="app.label.montoAnticipio"/></td>
					<td align="right"><bean:write name="pac" property="montoAnticipio"/></td>
					<td align="center">-</td>
				</tr>
				<tr class="even">
					<td><bean:message bundle="labels" key="app.label.total"/></td>
					<td align="right"><bean:write name="pac" property="montoFontarTotal" /></td>
					<td align="right"><bean:write name="pac" property="montoCPTotal"/></td>
				</tr>
				<tr class="odd">
					<td><bean:message bundle="labels" key="app.label.montoFinanciamiento"/></td>
					<td align="right"><bean:write name="pac" property="montoFontarFinanciamiento"/></td>
					<td align="right"><bean:write name="pac" property="montoCPFinanciamiento"/></td>
				</tr>
				<tr class="even">
					<td><bean:message bundle="labels" key="app.label.montoDiferencia"/></td>
					<td align="right"><bean:write name="pac" property="montoFontarDif"/></td>
					<td align="right"><bean:write name="pac" property="montoCPDif"/></td>
				</tr>
			</tbody>
			</table>
	</c:if>
	<br>
	<c:if test="${!empty display}">
		<h2>
			<bean:message key="${display}" bundle="${bundle}"/>
		</h2>
	</c:if>
	<c:if test="${empty display}">
		<c:if test="${empty presupuesto}">
			<span class="error">
				<bean:message bundle="errors" key="app.proyecto.presupuesto.empty"/>
				<br>
			</span>
		</c:if>
		<c:if test="${!empty esDistintoZero}">
			<span class="error">
				<bean:message bundle="errors" key="app.proyecto.pac.warning"/>
			</span>
		</c:if>
	</c:if>
	<c:if test="${!empty monedasSinCotizacionError}">
		<span class="error">
			<bean:write name="monedasSinCotizacionError"/>
			<br>
		</span>
	</c:if>
</html:form>
