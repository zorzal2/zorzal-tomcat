<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters" prefix="rapidFilters" %>
<%@ taglib tagdir="/WEB-INF/tags/consultas" prefix="consultas" %>

<html:form action="/EjecutarConsulta" >
	<input type="hidden" id="id" name="id" value="${consulta.nombre}"/>
	<input type="hidden" id="selectionEvent" name="selectionEvent"/>
	
	<table class="filtros">
		<tr>
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
			<display-el:column  property="proyectoDatos.entidadBeneficiaria.denominacion" title="Denominación Entidad Beneficiaria"/>
			<display-el:column  property="instrumento.denominacion" title="Código Instrumento"/>
			<display-el:column  property="codigo" title="Identificador Proyecto"/>
			<display-el:column  property="proyectoDatos.titulo" title="Titulo Proyecto"/>
			<display-el:column  property="proyectoJurisdiccion.jurisdiccion.descripcion" title="Jurisdicción Proyecto"/>
			<display-el:column  property="proyectoPresupuesto.montoPresupuestoTotal" decorator="com.fontar.seguridad.cripto.EncryptedMoneyWrapper" title="Monto Presupuesto Proyecto Total"/>
			<display-el:column  property="proyectoPresupuesto.montoPresupuestoSolicitado" decorator="com.fontar.seguridad.cripto.EncryptedMoneyWrapper" title="Monto Presupuesto Proyecto FONTAR"/>
			<display-el:column  property="proyectoDatos.ciiu.codigo" title="Código CIIU"/>
		</display-el:setProperty>
	</display-el:table>
	
	
</html:form>
