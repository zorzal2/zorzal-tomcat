<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/consultas" prefix="consultas" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>

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
	
	<display-el:table export="true" name="result" requestURI="EjecutarConsulta.do" class="inventario" 
					  pagesize="15" decorator="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper">
		<display-el:setProperty name="export.excel.decorator" value="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper"/>
		<display-el:setProperty name="export.pdf.decorator" value="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper"/>
		<display-el:setProperty name="basic.show.header" value="true">
		
			<display-el:caption style="display:none"><bean:write name="consulta" property="titulo"/></display-el:caption>			

			<display-el:column property="instrumento.identificador" title="Instrumento"/>
			<display-el:column property="codigo" title="Identificador" />			
			<display-el:column property="proyectoDatos.entidadBeneficiaria.denominacion" title="Entidad Beneficiaria"/>
			<display-el:column property="proyectoJurisdiccion.jurisdiccion.descripcion" title="Jurisdicción" />
			<display-el:column property="proyectoDatos.titulo" title="Título" />
			<display-el:column property="proyectoDatos.personaRepresentante.nombre" title="Representante" />
			<display-el:column property="proyectoDatos.personaRepresentante.cuit" title="Cuit Representante" />
			<display-el:column property="proyectoDatos.personaDirector.nombre" title="Director" />
			<display-el:column property="proyectoDatos.personaDirector.cuit" title="Cuit Director" />	
			<display-el:column property="entidadesIntervinientes" title="Entidades Intervinientes" decorator="com.fontar.web.decorator.miscelaneos.StringMaxLengthWrapper"/>	
			
		</display-el:setProperty>
		
	</display-el:table>
		
</html:form>
