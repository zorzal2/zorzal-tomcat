<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<!--  Header de Proyecto solito -->
<h3>
	<bean:message bundle="titles" key="app.title.datosItem"/>
</h3> 
<div>	
		<table border="0" width="100%" class="inventario">
			<tr>
				<th><bean:message bundle="labels" key="app.label.item"/></th>
				<th><bean:message bundle="labels" key="app.label.descripcion" /></th>
				<th><bean:message bundle="labels" key="app.label.etapa" /></th>
				<th><bean:message bundle="labels" key="app.label.rubro" /></th>
				<th><bean:message bundle="labels" key="app.label.adquisicion"/></th>
				<th><bean:message bundle="labels" key="app.label.fechaEstimada" /></th>
				<th><bean:message bundle="labels" key="app.label.montoEstimado" /></th>
				<th><bean:message bundle="labels" key="app.label.montoAFinanciar"/></th>
 				<th><bean:message bundle="labels" key="app.label.totalPedido"/></th>
 				<th><bean:message bundle="labels" key="app.label.montoADesembolsar"/></th>
 			 </tr>
 			<tr>
				<td><bean:write name="pac" property="item" /></td>
				<td><bean:write name="pac" property="descripcion" /></td>
				<td align="center"><bean:write name="pac" property="etapa" /></td>
				<td><bean:write name="pac" property="rubro" /></td>
				<td><bean:write name="pac" property="adquisicion" /></td>
				<td><bean:write name="pac" property="fechaEstimada" /></td>
				<td align="right"><bean:write name="pac" property="montoEstimado" /></td>
				<td align="right"><bean:write name="pac" property="montoAdjudicado" /></td>
				<td align="right"><bean:write name="pac" property="montoTotalPedidoStr" /></td>
				<td align="right"><bean:write name="pac" property="montoADesembolsarStr" /></td>
			</tr>
		</table>			
	<br>
</div>