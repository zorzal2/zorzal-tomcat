<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<pragmatags:btnDynaLabel javascript="submitForm()" label="app.label.confirmar"/>&nbsp;
	<pragmatags:btnDynaLabel action="/ProcedimientoInventario" label="app.label.volver"/>&nbsp;
	<pragmatags:btnDynaLabel action="/CircuitoAutorizacionImprimir" label="app.label.imprimir"/>&nbsp;
</div>

