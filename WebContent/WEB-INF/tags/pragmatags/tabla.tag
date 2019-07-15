<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>


<%@ tag body-content="empty" %>

<%@ attribute name="toolbar" required="true"%>
<%@ attribute name="pagesize" required="false"%>
<%@ attribute name="toolbaroptions" required="false"%>

<%@ attribute name="collection" required="true"%>
<%@ attribute name="columns" required="true"%>
<%@ attribute name="align" required="false"%>
<%@ attribute name="actions" required="false"%>

<%@ attribute name="decorator" required="true"%>
<%@ attribute name="requestURI" required="true"%>

<%@ attribute name="entity" required="false"%>

<%@ attribute name="selector" required="false"%>

<c:if test="${!empty pagesize}">
	<c:set var="pagesize" value="${pagesize}"/>
</c:if>
<c:if test="${empty pagesize}">
	<c:set var="pagesize" value="15"/>		
</c:if>

<c:set var="aligns" value="${align}"/>	

<c:set var="selectedColumns" value="${columns}"/>	

<c:if test="${!empty param.mycolumns}">
	<c:set var="selectedColumns" value="${param.mycolumns}"/>
</c:if>

<c:set var="export" value="false" />

<c:if test="${toolbar == 'yes'}">
	<table>
		<tr>
			<td align=right>			
				<c:forEach items="${toolbaroptions}" var="option">
					<c:if test="${option == 'excel'}">
						<c:set var="export" value="true" />
						<!--  
						img title='Exportar Excel' border=0 hspace=0 src='images/listaExcel.gif' width=17 height=17>
						-->
					</c:if>	

					<%-- /GB: TODO: se quita provisoriamente para muestra del prototipo funcional --%>
					<%--
					<c:if test="${option == 'list'}">
						<a href="javascript:AbrirPopupLista('<c:out value="${entity}Listar.do?columns=${columns}&decorator=${decorator}&requestURI=${requestURI}&collection=${collection}"/>','650','530');">
							<img title='Listar' border=0 hspace=0 src='images/listaListado.gif' width=17 height=17>
						</a>
					</c:if>
					--%>

					<c:if test="${option == 'selcolumns'}">
						<a href="javascript:AbrirPopupLista('jsp/layout/DisplayColumns.jsp<c:out value="?columns=${columns}"/>','350','280');">
							<img title='Seleccionar Columnas' border=0 hspace=0 src='images/listaMostrar.gif' width=17 height=17>
						</a>
					</c:if>
					
					<%-- /GB: TODO: se quita provisoriamente para muestra del prototipo funcional --%>
					<%--
					<c:if test="${option == 'filter'}">
						<img title='Filtrar' border=0 hspace=0 src='images/listaFiltrar.gif' width=17 height=17>
					</c:if>					
					
					<c:if test="${option == 'sort'}">		
						<a href="javascript:AbrirPopupLista('jsp/layout/OrderList.jsp<c:out value="?columns=${columns}"/>','450','300');">						
							<img title='Ordenar' border=0 hspace=0 src='images/listaOrdenar.gif' width=17 height=17>
						</a>
					</c:if>
					--%>
					
					<c:if test="${option == 'page'}">
						<a href="javascript:AbrirPopup('jsp/layout/PaginList.jsp','450','160');">
							<img title='Paginar Lista' border=0 hspace=0 src='images/listaPaginar.gif' width=17 height=17>
						</a>
					</c:if>
				</c:forEach>
			</td>
		</tr>
	</table>	
</c:if>

<display-el:table id="data" class="inventario" export="${export}" pagesize="${pagesize}" 
				name="${collection}"
				requestURI="${requestURI}" 
				decorator="${decorator}">
    
	<%-- Encabezados --%>	
	<display-el:caption>	
		<tr class="inventario">
			<c:forEach items="${selectedColumns}" var="column">
				<th>
					<c:set var="myKey" value="app.header.${column}" />
					<fmt:bundle basename="resources.TableHeader">
						<fmt:message key="${myKey}" />
					</fmt:bundle>
				</th>
			</c:forEach>						

			<%-- GB/ TODO: tratar de cambiar este 15 por la cantidad exacta de acciones --%>
			<%-- <c:out value='${fn:length(actions)+1}' /> ${acciones.size+1}--%>
			<c:if test="${!empty actions}">
				<th class="accionesInventario" colspan="15">
					<bean:message bundle="headers" key="app.header.acciones" />
				</th>
			</c:if>
		</tr>    
	</display-el:caption>
	<%-- Fin Encabezados --%>
	
	<display-el:setProperty name="basic.show.header" value="false">
		<c:if test="${!empty align}">
			<c:forEach items="${selectedColumns}" var="column" varStatus="status">		
				<c:forEach items="${aligns}" var="currentAlign" begin="${status.index}" end="${status.index}">
					<display-el:column media="all" property="${column}" style="text-align:${currentAlign};" />
				</c:forEach>
			</c:forEach>
		</c:if>
		<c:if test="${empty align}">
			<c:forEach items="${selectedColumns}" var="column" varStatus="status">		
					<display-el:column media="all" property="${column}"  />
			</c:forEach>
		</c:if>

		<c:forEach items="${actions}" var="action">		
				<display-el:column media="html" class="accionesInventario" property="${action}" />
		</c:forEach>		
	</display-el:setProperty>
</display-el:table>





