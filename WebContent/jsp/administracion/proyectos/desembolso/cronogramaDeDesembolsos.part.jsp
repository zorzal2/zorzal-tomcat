<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%-- 

Vista del cronograma de desembolso.
Parametros:
cronograma: nombre del atributo que contiene un CronogramaDeDesembolsosDTO
return-url: url de la accion actual

 --%>
<% 
request.setAttribute("__cronograma", com.pragma.util.WebUtils.getComplexAttribute(request, request.getParameter("cronograma")));
%>
<iframe name="inlineActions" height="1" frameborder="0" width="1"></iframe>
<%-- Calculo el colSpan --%>
<c:set var="colSpan" value="0"/>
<c:if test="${__cronograma.permiteEditar}">
	<c:set var="colSpan" value="${colSpan+1}"/>
</c:if>
<c:if test="${__cronograma.permiteAutorizar}">
	<c:set var="colSpan" value="${colSpan+1}"/>
</c:if>
<c:if test="${__cronograma.permitePagarAnticipo}">
	<c:set var="colSpan" value="${colSpan+1}"/>
</c:if>
<c:if test="${__cronograma.permitePagar}">
	<c:set var="colSpan" value="${colSpan+1}"/>
</c:if>
<c:if test="${__cronograma.permiteEliminar}">
	<c:set var="colSpan" value="${colSpan+1}"/>
</c:if>

<%
	/*
	 Guardo la direccion de retorno para las acciones asociadas.
	*/
	String url = (String)request.getParameter("return-url");

	com.pragma.web.NavigationManager.saveCurrentLocationWithName(request, url, "proyecto.desembolso.returnAddress");
%>
<div class="titulo">
	<bean:message bundle="titles" key="app.title.desembolso.inventario" />
</div>
<c:if test="${__cronograma.permiteAgregar}">
	<pragmatags:btnAgregar action="/ProyectoDesembolsoAgregar" permissions="PROYECTOS-CRONOGRAMA-AGREGAR"/>
</c:if>
<display-el:table name="__cronograma.desembolsos" class="inventario" decorator="com.fontar.web.form.administracion.proyecto.desembolso.ProyectoDesembolsoWrapper">
	<display-el:setProperty name="basic.show.header" value="false">
		<display-el:caption>
			<tr>
				<th><bean-el:message bundle="headers" key="app.header.desembolso.concepto" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.desembolso.objetivo" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.desembolso.montoOriginal" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.desembolso.montoAutorizado" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.desembolso.montoDesembolsado" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.desembolso.plazo" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.desembolso.codigoEstado" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.desembolso.fecha" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.desembolso.fechaPago" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.desembolso.esAnticipo" /></th>
				<c:if test="${colSpan ne 0}">
					<th class="accionesInventario" colspan="<c:out value='${colSpan}'/>"><bean-el:message bundle="headers" key="app.header.acciones" /></th>
				</c:if>
		    </tr> 
		</display-el:caption> 
        <display-el:column property="concepto" style="text-align: center"/>   
        <display-el:column property="objetivo" style="text-align: center"/>   
        <display-el:column property="montoOriginal" style="text-align: right" decorator="com.pragma.toolbar.web.decorator.MoneyWrapper"/> 
        <display-el:column property="montoAutorizado" style="text-align: right" decorator="com.pragma.toolbar.web.decorator.EditableMoneyWrapper" escapeXml="false"/> 
        <display-el:column property="montoDesembolsado" style="text-align: right" decorator="com.pragma.toolbar.web.decorator.EditableMoneyWrapper" escapeXml="false" /> 
        <display-el:column property="plazo" style="text-align: right"/> 
        <display-el:column property="codigoEstado" style="text-align: center"/> 
        <display-el:column property="calculateDate" style="text-align: right" decorator="com.pragma.toolbar.web.decorator.ShortDateWrapper"/>   
        <display-el:column property="fechaPagoInventario" style="text-align: right" decorator="com.pragma.toolbar.web.decorator.ShortDateWrapper"/> 
        <display-el:column property="esAnticipo" /> 
		<c:if test="${colSpan ne 0}">
			<c:if test="${__cronograma.permiteEditar}">
		        <display-el:column class="accionesInventario" property="linkEditar" />
			</c:if>
			<c:if test="${__cronograma.permiteAutorizar}">
		        <display-el:column class="accionesInventario" property="linkAutorizar" />
			</c:if>
			<c:if test="${__cronograma.permiteEliminar}">
		        <display-el:column class="accionesInventario" property="linkEliminar" />
			</c:if>
			<c:if test="${__cronograma.permitePagarAnticipo}">
		        <display-el:column class="accionesInventario" property="linkPagarAnticipo" />
			</c:if>
			<c:if test="${__cronograma.permitePagar}">
		        <display-el:column class="accionesInventario" property="linkDesembolsar" />
			</c:if>
		</c:if>
	</display-el:setProperty>
	<display-el:footer>
		<tr>
			<th colspan="2"><bean-el:message bundle="headers" key="app.header.montoTotal" /></th>
			<td class="title money"><c:out value="${__cronograma.montoTotalPrevisto}" /></td>
			<td class="title money"><c:out value="${__cronograma.montoTotalAutorizado}" /></td>
			<td class="title money"><c:out value="${__cronograma.montoTotalDesembolsado}" /></td>
			<td colspan="${colSpan+5}"></td>
		<tr>
	</display-el:footer>
</display-el:table>