<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<div>
<table class="formulario">
	<tr>
		<td colspan="3">
			<pragmatags:btnDynaLabel action="/EditarDatosGeneralesGestionPago" label="app.label.finalizarEvaluacion" />		
		</td>
	</tr>
</table>	
<jsp:include page="/jsp/seguimientos/resumenDeRendicion.jsp" flush="true"></jsp:include>
</div>
<br>
<%-- Cronograma de desembolsos --%>
<jsp:include page="/jsp/administracion/proyectos/desembolso/inventario.jsp" flush="true">
	<jsp:param name="cronograma" value="cronograma" />
	<jsp:param name="return-url" value="/EvaluarGestionPagoSeguimiento.do" />
</jsp:include>

