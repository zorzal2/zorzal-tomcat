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
	<input type="hidden" id="selectionEvent" name="selectionEvent"/>
	
	<table class="filtros">
		<tr>
			<td>							
				Instrumento
				<html:select property="filter(instrumento)">        
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

	<display-el:table export="true" name="result" requestURI="EjecutarConsulta.do" class="inventario" pagesize="15" decorator="com.fontar.web.decorator.evaluacion.EvaluacionWrapper">
		<display-el:setProperty name="basic.show.header" value="true">
		<display-el:setProperty name="export.excel.decorator" value="com.fontar.web.decorator.evaluacion.EvaluacionWrapper"/>
		<display-el:setProperty name="export.pdf.decorator" value="com.fontar.web.decorator.evaluacion.EvaluacionWrapper"/>
		<display-el:caption style="display:none"><bean:write name="consulta" property="titulo"/></display-el:caption>	
			<display-el:column  property="institucionesEvaluadora" title="Institución" decorator="com.fontar.web.decorator.miscelaneos.StringMaxLengthWrapper"/>
			<display-el:column  property="evaluador" title="Evaluador"/>
			<display-el:column  property="fecha" decorator="com.pragma.toolbar.web.decorator.ShortDateWrapper" title="Fecha"/>
			<display-el:column  property="tipoEvaluacion" title="Tipo"/>
			<display-el:column  property="resultado" title="Resultado"/>
			<display-el:column  property="proyecto.proyectoDatos.entidadBeneficiaria.denominacion" title="Entidad Beneficiaria"/>
			<display-el:column  property="proyecto.instrumento.denominacion" title="Instrumento"/>
			<display-el:column  property="proyecto.codigo" title="Proyecto"/>
			<display-el:column  property="proyecto.proyectoDatos.titulo" title="Título"/>
			<display-el:column  property="proyecto.proyectoJurisdiccion.jurisdiccion.descripcion" title="Jurisdicción"/>
			<display-el:column  property="proyecto.proyectoPresupuesto.montoPresupuestoTotal" decorator="com.fontar.seguridad.cripto.EncryptedMoneyWrapper" title="Presupuesto Total ($)" style="text-align:right"/>
			<display-el:column  property="proyecto.proyectoPresupuesto.montoPresupuestoSolicitado" decorator="com.fontar.seguridad.cripto.EncryptedMoneyWrapper" title="Presupuesto Fontar ($)" style="text-align:right"/>
			<display-el:column  property="proyecto.proyectoDatos.ciiu.codigo" title="CIIU"/>
			<display-el:column  property="proyecto.recomendacionProyecto" decorator="com.fontar.web.decorator.evaluacion.EncryptedEnumerableWrapper" title="Recomendación del Proyecto"/>
		</display-el:setProperty>
	</display-el:table>
	
	
</html:form>
