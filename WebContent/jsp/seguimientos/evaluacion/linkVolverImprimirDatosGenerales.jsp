<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Volver al inventario Evaluacion Seguimiento -->
	<pragmatags:btnCerrar action="/WFCancelarTarea"/>
	<pragmatags:btnDynaLabel action="/DatosGeneralesEvaluacionSeguimientoImprimir" label="app.label.imprimir"/>
</div>