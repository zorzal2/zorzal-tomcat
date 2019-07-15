<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/consultas" prefix="consultas" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>


<pragmatags:error property="before"/>
<pragmatags:error property="after"/>
<html:form action="/EjecutarConsulta" >
	<input type="hidden" id="id" name="id" value="${consulta.nombre}"/>

	<table class="filtros">
		<tr>
			<td>Instrumento</td>
			
			<td>
				<html:select property="filter(finstrumento)">        
					<html:options collection="instrumentos" property="value" labelProperty="label"/>
				</html:select>
			
			</td>
		    <td><consultas:resolucionPresentacionFilter/></td>
			<td>
				<rapidFilters:applyFilters />
			</td>
			<td>
				<consultas:clearFilters />
			</td>
		</tr>
	</table>
			
	<display-el:table export="true" name="result" requestURI="EjecutarConsulta.do" class="inventario" pagesize="15" decorator="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper">

		<display-el:setProperty name="basic.show.header" value="true">
		<display-el:setProperty name="export.excel.decorator" value="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper"/>
		<display-el:setProperty name="export.pdf.decorator" value="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper"/>
		
			
			<display-el:caption style="display:none"><bean:write name="consulta" property="titulo"/></display-el:caption>

			<display-el:column property="instrumento.denominacion" title="Instrumento"/>
			<display-el:column property="esVentanilla" title="Modalidad"/>
			<display-el:column property="instrumento.instrumentoDef.fuenteFinanciamiento.denominacion" title="Fuente Financiamento"/>
			<display-el:column property="instrumento.modalidad" title="Modalidad del Beneficio"/>

			<display-el:column property="codigo" title="Proyecto" />
			<display-el:column property="proyectoDatos.titulo" title="Título" />

			<display-el:column property="proyectoDatos.entidadBeneficiaria.denominacion" title="Entidad Beneficiaria" />
			<display-el:column property="proyectoDatos.entidadBeneficiaria.entidad.cuit" title="Entidad Beneficiaria Cuit" />
			<display-el:column property="proyectoDatos.entidadBeneficiaria.localizacionEconomica.localidad" title="Entidad Beneficiaria Localidad" />
			<display-el:column property="proyectoDatos.entidadBeneficiaria.localizacionEconomica.jurisdiccion.descripcion" title="Entidad Beneficiaria Provincia" />
			<display-el:column property="proyectoDatos.entidadBeneficiaria.localizacionEconomica.jurisdiccion.region.nombre" title="Entidad Beneficiaria Región" />

			<display-el:column property="proyectoDatos.tipoProyecto.nombre" title="Tipo Proyecto" />
			<display-el:column property="le" title="CIIU Proyecto Le" />
			<display-el:column property="sd" title="CIIU Proyecto 2d" />
			<display-el:column property="cd" title="CIIU Proyecto 5d" />

			<display-el:column property="leBeneficiaria" title="CIIU Beneficiaria Le" />
			<display-el:column property="sdBeneficiaria" title="CIIU Beneficiaria 2d" />
			<display-el:column property="cdBeneficiaria" title="CIIU Beneficiaria 5d" />

			<display-el:column property="proyectoPresupuestoOriginal.montoTotal" style="text-align:right;" title="Monto Total Solicitado ($)" decorator="com.pragma.toolbar.web.decorator.MoneyWrapper" />
			<display-el:column property="proyectoPresupuestoOriginal.montoSolicitado" style="text-align:right;" title="Monto Parte Solicitado ($)" decorator="com.pragma.toolbar.web.decorator.MoneyWrapper" />			
			<display-el:column property="proyectoPresupuestoOriginal.montoEmpresa" style="text-align:right;" title="Monto Contraparte Solicitado ($)" decorator="com.pragma.toolbar.web.decorator.MoneyWrapper" />			
			<display-el:column property="correspondeOriginal" style="text-align:right;" title="% corresponde Solicitado ($)"/>			

			<display-el:column property="proyectoPresupuesto.montoTotal" style="text-align:right;" title="Monto Total Actual ($)" decorator="com.pragma.toolbar.web.decorator.MoneyWrapper" />
			<display-el:column property="proyectoPresupuesto.montoSolicitado" style="text-align:right;" title="Monto Parte Actual ($)" decorator="com.pragma.toolbar.web.decorator.MoneyWrapper" />			
			<display-el:column property="proyectoPresupuesto.montoEmpresa" style="text-align:right;" title="Monto Contraparte Actual ($)" decorator="com.pragma.toolbar.web.decorator.MoneyWrapper" />			
			<display-el:column property="correspondeActual" style="text-align:right;" title="% corresponde Actual ($)"/>			

			<display-el:column property="proyectoDatos.fechaIngreso" decorator="com.pragma.toolbar.web.decorator.ShortDateWrapper" title="Fecha Ingreso" />

			<display-el:column property="nrAdmision" title="Admisión - Número de Disposición" />
			<display-el:column property="fechaNrAdmision" decorator="com.pragma.toolbar.web.decorator.ShortDateWrapper" title="Admisión - Fecha" />
			<display-el:column property="resultadoAdmision" title="Admisión - Resultado" />
			
			<display-el:column property="codigoResolucion" title="Codigo Aprobación/Resolución" />
			<display-el:column property="fechaResolucion" decorator="com.pragma.toolbar.web.decorator.ShortDateWrapper" title="Fecha Aprobación/Resolución" />
			
						<display-el:column property="fechaFirmaDeContrato" decorator="com.pragma.toolbar.web.decorator.ShortDateWrapper" title="Fecha del Contrato" />
			<display-el:column property="fechaCierreFinal" decorator="com.pragma.toolbar.web.decorator.ShortDateWrapper" title="Fecha de Cierre Final" />
			
			<display-el:column property="estado" title="Estado Proyecto" />
			<display-el:column property="proyectoDatos.observacion" title="Observación" />
			
			<display-el:column property="proyectoDatos.personaRepresentante.nombre" title="Nombre Representante" />
			<display-el:column property="proyectoDatos.personaRepresentante.localizacion.direccion" title="Dirección Representante" />
			<display-el:column property="proyectoDatos.personaRepresentante.localizacion.codigoPostal" title="Código Postal Representante" />
			<display-el:column property="proyectoDatos.personaRepresentante.localizacion.localidad" title="Localidad Representante" />
			<display-el:column property="proyectoDatos.personaRepresentante.localizacion.jurisdiccion.codigo" title="Provincia Representante" />
			<display-el:column property="proyectoDatos.personaRepresentante.localizacion.telefono" title="Teléfono Representante" />			
			<display-el:column property="proyectoDatos.personaRepresentante.localizacion.email" title="Mail Representante" />
		
			<display-el:column property="montoBienesCapital" style="text-align:right;" title="Presupuesto Actual para BIENES CAPITAL: MAQUINARIAS, EQUIPOS E INSTRUMENTOS" />
			<display-el:column property="montoBienesCapitalInfra" style="text-align:right;" title="Presupuesto Actual para BIENES CAPITAL: INFRAESTRUCTURA" />
			<display-el:column property="montoBienesCapitalOtro" style="text-align:right;" title="Presupuesto Actual para BIENES CAPITAL: OTROS" />
			<display-el:column property="montoRHProp" style="text-align:right;" title="Presupuesto Actual para RECURSOS HUMANOS. PROPIOS" />
			<display-el:column property="montoRHAd" style="text-align:right;" title="Presupuesto Actual para RECURSOS HUMANOS. ADICIONALES" />
			<display-el:column property="montoConYSer" style="text-align:right;" title="Presupuesto Actual para CONSULTORIAS Y SERVICIOS" />
			<display-el:column property="montoMatEIn" style="text-align:right;" title="Presupuesto Actual para MATERIALES E INSUMOS" />
			<display-el:column property="montoOtros" style="text-align:right;" title="Presupuesto Actual para OTROS COSTOS" />
		
						
		</display-el:setProperty>
		
	</display-el:table>
		
</html:form>
