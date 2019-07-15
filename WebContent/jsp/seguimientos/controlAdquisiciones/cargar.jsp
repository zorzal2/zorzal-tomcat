<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>

	<h3>
		<bean:message bundle="titles" key="app.title.procedimientos.cargar${sessionScope['FORWARD_TILES']}"/>
	</h3>	

	<jsp:include page="/jsp/cabecera/cabeceraProcedimiento.jsp" flush="true"/>

	<jsp:include page="cargar${sessionScope['FORWARD_TILES']}.jsp" flush="true"/>
	
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/ProcedimientoInventario"/>	
</div>
