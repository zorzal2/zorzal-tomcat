<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<div>
<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.distribucionZona" />
		</th>
	</tr>

	<tr></tr>
	<tr>
		<td width="25%">
			<bean:message bundle="labels" key="app.label.tipoDistribucionFinanciamiento"/>:&nbsp;
		</td>
		<td class="opcional">
			<html:radio name="instrumento" property="tipoDistribucionFinanciamiento" value="GLOBAL" disabled="true"> 
				<bean:message bundle="labels" key="app.label.global" /> 
			</html:radio>
			<html:radio name="instrumento" property="tipoDistribucionFinanciamiento" value="REGIONAL" disabled="true"> 
				<bean:message bundle="labels" key="app.label.regional" /> 
			</html:radio>					
			<html:radio name="instrumento" property="tipoDistribucionFinanciamiento" value="JURISDICCIONAL" disabled="true"> 
				<bean:message bundle="labels" key="app.label.jurisdiccion" /> 
			</html:radio>			
		</td>
	</tr>
</table>

<br>
<table class="formulario">
	<tr>
		<td width="25%">
			<bean:message bundle="labels" key="app.label.montoTotalInstrumento"/>
		</td>
		<td class="opcional">
			<bean:write name="instrumento" property="montoFinanciamientoTotal" format="###,##0.00"/>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.tipoAsignamientoInformacion"/>
		</td>
		<td class="opcional">	
			<bean:write name="instrumento" property="tipoFinanciamientoAsignacion.descripcion"/>
		</td>
	</tr>	
</table>

<c:if test="${instrumento.tipoFinanciamientoAsignacion.name == 'MONTO'}">
<table class="inventario" style="width:45%;">
	<tr>
		<th>
			<bean:message bundle="headers" key="app.header.nombre"/>
		</th>
		<th>
			<bean:message bundle="headers" key="app.header.monto$"/>
		</th>
	</tr>
	
	<logic:present name="distribucionFinanciamientoList" scope="request"> 	
	   <logic:iterate name="distribucionFinanciamientoList" id="distribucionFinanciamiento">
	   	<tr>
	      	<td>
	      		<bean:write name="distribucionFinanciamiento" property="nombre"/>
	      	</td>
			<td align="right">
				<bean:write name="distribucionFinanciamiento" property="monto" format="###,##0.00"/>
	      	</td>
		</tr>
	   </logic:iterate>
    </logic:present>
    
   	<tr>
		<th>
			<bean:message bundle="headers" key="app.header.total"/>
		</th>
		<th style="text-align:right">
			<bean:write name="instrumento" property="montoFinanciamientoTotal" format="###,##0.00"/>
		</th>
	</tr>
</table>
</c:if>

<c:if test="${instrumento.tipoFinanciamientoAsignacion.name == 'PORCENTAJE'}">
<table class="inventario" style="width:45%;">
	<tr>
		<th>
			<bean:message bundle="headers" key="app.header.nombre"/>
		</th>
		<th>
			<bean:message bundle="headers" key="app.header.porcentaje%"/>
		</th>				
	</tr>
	
	<logic:present name="distribucionFinanciamientoList" scope="request"> 	
	   <logic:iterate name="distribucionFinanciamientoList" id="distribucionFinanciamiento">
	   	<tr>
	      	<td>
	      		<bean:write name="distribucionFinanciamiento" property="nombre"/>
	      	</td>
			<td align="right">
				<bean:write name="distribucionFinanciamiento" property="porcentaje" format="###,##0.00"/>
	      	</td>
		</tr>
	   </logic:iterate>
    </logic:present>
    
   	<tr>
		<th>
			<bean:message bundle="headers" key="app.header.total"/>
		</th>
		<th style="text-align:right">
			<c:out value="100.00"/>
		</th>	
	</tr>
</table>
</c:if>
</div>
