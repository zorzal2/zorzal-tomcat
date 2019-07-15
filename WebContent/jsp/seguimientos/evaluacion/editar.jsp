<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<h3>
		<bean:message bundle="titles" key="app.title.evaluacionSeguimiento.editarDatosGenerales"/>
	</h3>	

	<jsp:include page="/jsp/cabecera/cabeceraEvaluacionSeguimiento.jsp" flush="true"/>

	<jsp:include page="editarDatos.jsp" flush="true"/>

	<pragmatags:btnOkCancel okJavascript="submitForm()"  cancelAction="/CargarResultadoEvaluacionSeguimiento.do"/>	
</div>
