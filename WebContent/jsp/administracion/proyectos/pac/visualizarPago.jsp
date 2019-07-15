<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<h3>
	<bean:message bundle="titles" key="app.title.DesembolsosPedidos"/>
</h3> 
<display-el:table name="desembolsos" class="inventario">
	<display-el:column title="Desembolso Nº" property="ordenPago" />   
	<display-el:column title="Nro Cuota" property="cuota" />   
	<display-el:column title="Fecha del Pedido de Desembolso" property="fechaPedido" decorator="com.pragma.toolbar.web.decorator.ShortDateWrapper"/>   
	<display-el:column style="text-align: right" title="Monto del pedido del Desembolso" property="montoDesembolsado" decorator="com.pragma.toolbar.web.decorator.MoneyWrapper"/>   
	<display-el:column title="Moneda" property="moneda.descripcion" />   
	<display-el:column title="Fecha del Pago" property="fechaPago" decorator="com.pragma.toolbar.web.decorator.ShortDateWrapper"/>   
	<display-el:column style="text-align: right" title="Monto del Pago ($)" property="montoPago" decorator="com.pragma.toolbar.web.decorator.MoneyWrapper"/>   
	<display-el:column title="Aplica Anticipo Financiamiento" property="esAnticipo" decorator="com.pragma.toolbar.web.decorator.BooleanWrapper"/>   
</display-el:table>
<br/>
