<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<br>
<h3>
	<bean:message bundle="titles" key="app.title.evaluacionSeguimiento.facturasRepetidas"/>
</h3>
<c:choose>
	<c:when test="${!empty facturasRepetidasList}">
		<table class="inventario">
			<logic:present name="facturasRepetidasList" scope="request"> 	 
				<tr>
					<th>
						<bean:message bundle="headers" key="app.header.numeroFactura" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.proveedor" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.descripcion" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.montoTotal" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.seguimiento" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.proyecto" />
					</th>
					<th>
						<bean:message bundle="headers" key="app.header.entidadBeneficiaria" />
					</th>					
				</tr>
			
				<logic:iterate name="facturasRepetidasList" id="facturaRepetida" indexId="index">
				   	<tr>
				      	<td>
				      		<bean:write name="facturaRepetida" property="numeroFactura"/>
				      	</td>
				      	<td align="right">
							<bean:write name="facturaRepetida" property="nombreProveedor"/>
				      	</td>
				      	<td align="right">
					      	<bean:write name="facturaRepetida" property="descripcion"/>
				      	</td>
				      	<td align="right">
					      	<bean:write name="facturaRepetida" property="montoTotal"/>
				      	</td>
				      	<td align="right">
							<bean:write name="facturaRepetida" property="seguimiento"/>
				      	</td>
				      	<td align="right">
					      	<bean:write name="facturaRepetida" property="codigoProyecto"/>
				      	</td>
				      	<td align="right">
					      	<bean:write name="facturaRepetida" property="empresa"/>
				      	</td>
			      </tr>
				</logic:iterate>
			</logic:present>
		</table>
	</c:when>		
	<c:otherwise>
		<bean:message key="app.msj.noHayFacturasRepetidas" bundle="informationals"/>
	</c:otherwise>
</c:choose>
<br>
<pragmatags:btnCerrar javascript="window.close()" />
