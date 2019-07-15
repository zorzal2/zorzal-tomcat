<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/consultas" prefix="consultas" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>

<html:form action="/EjecutarConsulta" >
	<input type="hidden" id="id" name="id" value="${consulta.nombre}"/>
	<input type="hidden" id="selectionEvent" name="selectionEvent"/>
	
	<table class="filtros">
		<tr>
			<td>Instrumento</td>
			
			<td>
				<html:select property="filter(instrumento)">        
					<html:options collection="instrumentos" property="value" labelProperty="label"/>
				</html:select>
			
				Entidad Beneficiaria
			
				<pragmatags:selectorFilter width="60" entidad="EntidadBeneficiaria" />
			</td>
		</tr>
		
		<tr>
			<consultas:resolucionPresentacionFilter/>
			
			<td>
				<rapidFilters:applyFilters />
			</td>
			
			<td>
				<consultas:clearFilters />
			</td>
		</tr>
	</table>
	
	<display-el:table export="true" name="result" requestURI="EjecutarConsulta.do" class="inventario" pagesize="15" decorator="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper">
		<display-el:setProperty name="export.excel.decorator" value="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper"/>
		<display-el:setProperty name="export.pdf.decorator" value="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper"/>

		<display-el:setProperty name="basic.show.header" value="true">

			<display-el:caption style="display:none"><bean:write name="consulta" property="titulo"/></display-el:caption>			

			<display-el:column property="codigo" title="Código" />
			<display-el:column property="proyectoDatos.titulo" title="Título" />
			<display-el:column property="proyectoDatos.entidadBeneficiaria.denominacion" title="Entidad Nombre"/>
			<display-el:column property="proyectoDatos.entidadBeneficiaria.cuit" title="Entidad CUIT"/>
			<display-el:column property="proyectoDatos.localizacion.localidad" title="Localidad Proyecto" />
			<display-el:column property="proyectoDatos.localizacion.jurisdiccion.descripcion" title="Provincia Proyecto" />
			
			<display-el:column property="proyectoDatos.personaRepresentante.nombre" title="Representante Proyecto" />
			<display-el:column property="proyectoDatos.entidadBeneficiaria.localizacionEconomica.direccion" title="Entidad Dirección"/>
			<display-el:column property="proyectoDatos.entidadBeneficiaria.localizacionEconomica.localidad" title="Entidad Localidad"/>
			<display-el:column property="proyectoDatos.entidadBeneficiaria.localizacionEconomica.codigoPostal" title="Entidad C.Postal"/>
			<display-el:column property="proyectoDatos.entidadBeneficiaria.localizacionEconomica.jurisdiccion.descripcion" title="Entidad Provincia"/>
			<display-el:column property="proyectoDatos.entidadBeneficiaria.localizacionEconomica.telefono" title="Entidad Teléfono"/>
			<display-el:column property="proyectoDatos.entidadBeneficiaria.localizacionEconomica.email" title="Entidad Email"/>
			
		</display-el:setProperty>
		
	</display-el:table>
	
</html:form>

