<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<h3>
		<bean:message bundle="titles" key="app.title.finalizarEvaluacionGestionPago"/>
	</h3>	

	<jsp:include page="/jsp/cabecera/cabeceraSeguimiento.jsp" flush="true"/>

	<jsp:include page="editarGestionPagoDatos.jsp" flush="true"/>
	
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/EvaluarGestionPagoSeguimiento"/>
</div>
