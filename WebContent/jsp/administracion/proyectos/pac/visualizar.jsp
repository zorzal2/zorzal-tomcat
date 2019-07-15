<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<script type="text/javascript" src="js/libreria.js"></script>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.historia"/></h3> 

	<%-- Header Editar Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

	<jsp:include page="visualizarItem.jsp" flush="true"/>

<html:form action="/HistoriaPACVisualizar">
<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<html:hidden property="id" />

<c:if test="${!empty procedimiento}">
<h3>
	<bean:message bundle="titles" key="app.title.procedimientos"/>
</h3> 
<div>	
<display-el:table name="procedimiento" class="inventario" requestURI="HistoriaPACVisualizar.do" decorator="com.fontar.web.decorator.seguimientos.controlAdquisicion.ProcedimientoWrapper">
	<display-el:column title="Procedimiento" property="codigo" />   
	<display-el:column title="Fecha de Recepción" property="fechaRecepcion" />   
	<display-el:column title="Evaluador" property="evaluador" />   
	<display-el:column title="Remisión" property="remision" />   
	<display-el:column title="Monto Adjudicación" property="montoAdjudicacion" />   
    <display-el:column title="Acción" property="linkVisualizarDTO" />
</display-el:table>
<br>
</div>
</c:if>		
	<!-- Volver al inventario -->
	<pragmatags:btnCerrar/>

</html:form>

</div>
