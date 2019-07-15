<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib tagdir="/WEB-INF/tags/consultas" prefix="consultas" %>

<pragmatags:error property="before"/>
<pragmatags:error property="after"/>

<html:form action="/EjecutarConsulta" >
	<input type="hidden" id="id" name="id" value="${consulta.nombre}"/>
	
	<table class="filtros">
		<tr>
			<td>
				<span> Región
					<html:select property="filter(region)">        
						<html:options collection="regiones" property="value" labelProperty="label"/>
					</html:select>
				</span>
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
	
	<br/>	
	<display-el:table export="true"  name="result" requestURI="EjecutarConsulta.do" class="inventario" pagesize="15" decorator="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper">
		<display-el:caption style="display:none"><bean:write name="consulta" property="titulo"/></display-el:caption>
		<display-el:setProperty name="export.excel.decorator" value="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper"/>
		<display-el:setProperty name="export.pdf.decorator" value="com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper"/>
		<display-el:setProperty name="basic.show.header" value="true">
			<display-el:column  property="proyectoJurisdiccion.jurisdiccion.region.nombre" title="Región"/>						
			<display-el:column  property="codigo" title="Código"/>
			<display-el:column  property="instrumento.identificador" title="Instrumento"/>
			<display-el:column  property="proyectoDatos.entidadBeneficiaria.denominacion" title="Entidad Beneficiaria"/>
			<display-el:column  property="proyectoDatos.titulo" title="Título Proyecto"/>						
			<display-el:column  property="proyectoJurisdiccion.jurisdiccion.descripcion" title="Jurisdicción"/>						
			<display-el:column  property="proyectoPresupuesto.montoPresupuestoTotal"  decorator="com.fontar.seguridad.cripto.EncryptedMoneyWrapper" title="Presupuesto Total ($)" style="text-align:right"/>						
			<display-el:column  property="proyectoPresupuesto.montoPresupuestoSolicitado" decorator="com.fontar.seguridad.cripto.EncryptedMoneyWrapper" title="Presupuesto Fontar ($)" style="text-align:right"/>
			<display-el:column  property="proyectoDatos.ciiu.codigo" title="CIIU"/>									
		</display-el:setProperty>
	</display-el:table>
	
</html:form>
	
	