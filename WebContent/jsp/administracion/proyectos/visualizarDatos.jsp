<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<!--  Detalle Proyecto -->
<table class="formulario">		
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	

	<!------------General------------>
	<tr>		
		<th colspan="5" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>	
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.nroProyecto"/>
		</td>		
		<td class="opcional">
			<bean:write name="proyecto" property="codigo"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.entidadBeneficiaria"/>
		</td>		
		<td class="opcional">
			<bean:write name="proyecto" property="txtEntidadBeneficiaria"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.titulo"/>
		</td>		
		<td class="opcional">
			<bean:write name="proyecto" property="titulo"/>
		</td>
	</tr>
	<%--
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.proyectoPitecRelacionado"/>
		</td>
		
		<td>
			<pragmatags:selector entidad="ProyectoPitec" />
		</td>
	</tr>
	--%>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.proyectoPitecRelacionado"/>
		</td>		
		<td class="opcional">
	   		<bean:write name="proyecto" property="proyectoPitec"/>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.objetivo"/>
		</td>
		
		<td class="opcional">
			<bean:write name="proyecto" property="objetivo"/>
		</td>
	</tr>

	<tr>
		<td valign="top" style="padding-top: 6px">
			<bean:message bundle="labels" key="app.label.responsableLegal"/>
		</td>		
		<td class="opcional" valign="top">
			<pragmatags:persona bean="proyecto.personaLegal" />
		</td>
	</tr>
	
	<tr>
		<td valign="top" style="padding-top: 6px">
			<bean:message bundle="labels" key="app.label.directorProyecto"/>
		</td>		
		<td class="opcional">
			<pragmatags:persona bean="proyecto.personaDirector" />
		</td>
	</tr>
	
	<tr>
		<td valign="top" style="padding-top: 6px">
			<bean:message bundle="labels" key="app.label.representante"/>
		</td>		
		<td class="opcional">
			<pragmatags:persona bean="proyecto.personaRepresentante" />
		</td>
	</tr>	
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.ciiu"/>
		</td>		
		<td class="opcional">
			<bean:write name="proyecto" property="codigoCiiu"/>
			-
			<bean:write name="proyecto" property="txtCiiu"/>
		</td>
	</tr>
		
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.resumen"/>
		</td>		
		<td class="opcional">
			<bean:write name="proyecto" property="resumen"/>
		</td>
	</tr>	

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.palabrasClave"/>
		</td>		
		<td class="opcional">
			<bean:write name="proyecto" property="palabraClave"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.duracionMeses"/>
		</td>		
		<td class="opcional">
			<bean:write name="proyecto" property="duracion"/>
		</td>
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.montoBeneficioAprobado"/>
		</td>		
		<td>
			<bean:write name="proyecto" property="montoBeneficioFONTARAprobado"/>
		</td>
	</tr>		

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.montoBeneficioSolicitado"/>
		</td>		
		<td>
			<bean:write name="proyecto" property="montoBeneficioFONTARSolicitado"/>
		</td>
	</tr>		
	<c:if test="${proyecto.aplicaCargaAlicuotaCF}">	
		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.proyectoHistorico.alicuotaSolicitada"/>
			</td>		
			<td>
				<bean:write name="proyecto" property="porcentajeAlicuotaSolicitada"/>
			</td>
		</tr>
	
		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.proyectoHistorico.alicuotaAdjudicada"/>
			</td>		
			<td>
				<bean:write name="proyecto" property="porcentajeAlicuotaAdjudicada"/>
				<c:if test="${proyecto.porcentajeAlicuotaAdjudicada != 'N/A'}">	
					<pragmatags:popUpMonto entidad="Proyecto" propiedad="porcentajeAlicuotaAdjudicada" id="proyecto.idProyecto" montoActual="proyecto.porcentajeAlicuotaAdjudicada"/>
				</c:if>
			</td>
		</tr>
	
	</c:if>
	
	<tr>
		<td class="obligatorio" valign="top">
			<bean:message bundle="labels" key="app.label.localizacionProyecto"/>
		</td>		
		<td colspan="1">
			<pragmatags:localizacion id="localizacion" esVisualizacion="true" nombreForm="proyecto" />
		</td>	 
	</tr>  
		
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.tipoProyecto"/>
		</td>		
		<td>
			<bean:write name="proyecto" property="txtTipoProyecto"/>
		</td>
	</tr>		
	<tr>
		<td valign="top">
			<bean:message bundle="labels" key="app.label.empleoPermanente"/>
		</td>		
		<td>
			<pragmatags:empleoPermanente id="empleo" esVisualizacion="true" nombreForm="proyecto" />
		</td>	
	</tr>
	

	<%-- ------------Datos Económicos------------ --%>

	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr>		
		<th colspan="5" class="titulo">
			<bean:message bundle="headers" key="app.header.datosEconomicos" />
		</th>	
	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.tir"/>
		</td>		
		<td class="opcional">
			<bean:write name="proyecto" property="tir"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.van"/>
		</td>
		<td class="opcional">
			<bean:write name="proyecto" property="van"/>
		</td>
		
	</tr>
	
	<!------------Entidad Bancaria------------>
	<c:if test="${permiteFinanciamientoBancario}">
		<tr>
			<td colspan="5">&nbsp;</td>
		</tr>
		<tr>		
			<th colspan="5" class="titulo">
				<bean:message bundle="headers" key="app.header.entidadBancaria" />
			</th>	
		</tr>
		
		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.nombre"/>
			</td>
			
			<td>
				<bean:write name="proyecto" property="txtEntidadBancaria"/>
			</td>
		</tr>
	
		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.description"/>
			</td>			
			<td class="opcional">
				<bean:write name="proyecto" property="descripcionEntidadBancaria"/>
			</td>
		</tr>

		<tr>
			<td>
				<bean:message bundle="labels" key="app.label.tasaInteres"/>
			</td>
			
			<td class="opcional">
				<logic:notEmpty name="proyecto" property="porcentajeTasaInteres">
					<bean:write name="proyecto" property="porcentajeTasaInteres"/>
					<bean:message bundle="labels" key="app.label.%"/>
				</logic:notEmpty>
			</td>
		</tr>
	</c:if>
	
	
	<!------------Entidades Intervinientes------------>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr>
		<th colspan="5" class="titulo">
			<bean:message bundle="labels" key="app.label.entidadesIntervinientes" />
		</th>
	</tr>
	<tr>
		<td colspan="5">
		<table id="tablaEntidad" border="0" width="100%" class="inventario">
			<tr>
				<th><bean:message bundle="labels" key="app.label.entidad"/></th>
				<th><bean:message bundle="labels" key="app.label.tipoEntidad" /></th>
				<th><bean:message bundle="labels" key="app.label.funcion" /></th>
				<th><bean:message bundle="labels" key="app.label.relacion"/></th>
			 </tr>
 			 <c:if test="${!empty intervinientes}">
			<c:forEach items="${intervinientes}" var="intervinientes">
			<tr>
				<td><c:out value="${intervinientes[0]}"/></td>
				<td><c:out value="${intervinientes[1]}"/></td>
				<td><c:out value="${intervinientes[3]}"/></td>
				<td><c:out value="${intervinientes[2]}"/></td>
			</tr>
			</c:forEach>
			</c:if>
		</table>			
		
		</td>
	</tr>

	
	<!------------Adjuntos------------
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>		
		<th>
			<bean:message bundle="headers" key="app.header.adjuntos" />
		</th>	
		<th>
		    <html:image page="/images/agregar.gif" alt="Agregar Archivo Adjunto" border="0"/>
		</th>	
	</tr>
	
	<tr>
		<td>
			TODOS
		</td>
	</tr>
	
	------------Observaciones------------>
	
	<!------------Observaciones------------>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>	
	<tr>	
		<th colspan="5" class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones"/>
		</th>		
	</tr>
	
	<tr>
		<td colspan="5" class="opcional">
			<bean:write name="proyecto" property="observacion" />
		</td>
	</tr>
</table>