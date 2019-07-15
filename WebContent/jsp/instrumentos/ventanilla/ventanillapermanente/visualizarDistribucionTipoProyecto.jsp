<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<body>
<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.distribucionTipoProyecto" />
		</th>
	</tr>

	<tr></tr>
	<tr></tr>
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td class="opcional">
						<bean:message bundle="labels" key="app.label.montoTotalInstrumento"/>
						<bean:write name="instrumento" property="montoFinanciamientoTotal"  format="###,##0.00"/>
					</td>
				</tr>
			</table>		
		</td>
	</tr>

	<tr>
		<td>
			<table id="tipoProyectoTable" class="inventario" style="width:80%">
				<tr>
					<th>
						<bean:message bundle="headers" key="app.header.tipoProyecto" />
					</th>
					<th class="opcional">
						<bean:message bundle="headers" key="app.header.montoTotalAsignado" />
					</th>
					<th class="opcional">
						<bean:message bundle="headers" key="app.header.limiteMaximoProyecto" />
					</th>
					<th class="obligatorio">
						<bean:message bundle="headers" key="app.header.plazoEjecucion" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.criterios" />
					</th>
				</tr>
				
			<logic:present name="tipoProyectoList" scope="request"> 	
				<logic:iterate name="tipoProyectoList" id="tipoProyecto">
			   	<tr>
			      	<td>
			      		<bean:write name="tipoProyecto" property="tipoProyecto"/>
			      	</td>
			      	<td align="right">
			      		<bean:write name="tipoProyecto" property="montoTotalAsignado" format="###,##0.00"/>
			      	</td>
			      	<td align="right">
			      		<bean:write name="tipoProyecto" property="limiteMaximoProyecto" format="###,##0.00"/>
			      	</td>
			      	<td align="right">
			      		<bean:write name="tipoProyecto" property="plazoEjecucion" format="###,##0.00"/>
			      	</td>
			      	<td align="right">
			      		<bean:write name="tipoProyecto" property="nombreMatrizCriterio"/>
			      	</td>
				</tr>
			   </logic:iterate>
		   	</logic:present>

				<tr>
					<th width="35%">
						<bean:message bundle="headers" key="app.header.montoAcumulado" />
					</th>
					<th style="text-align:right">
						<c:out value="${montoTotalAcumulado}"/>
					</th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
		   </table>
   		</td>
   </tr>
</table>

</body>
