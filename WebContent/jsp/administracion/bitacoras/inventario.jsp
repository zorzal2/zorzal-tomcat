<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/core" prefix="c"%>
<%@ taglib uri="/tags/authz" prefix="authz"%>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar" prefix="toolbar"%>
<%@ taglib tagdir="/WEB-INF/tags/toolbar/rapidFilters"	prefix="rapidFilters"%>
 
<html:form action="/BitacoraInventario.do">
	<table class="filtros">
		<tr>
			<td>
				<rapidFilters:comboFilter property="tipo"
				title="app.label.tipo" filterType="filter.type.string.equal"
				classNameType="java.lang.String" options="tiposBitacora"
				labelProperty="label" valueProperty="value" />
			</td>
			
			<td>
				<rapidFilters:textFilter property="idUsuario"
				title="app.label.usuario" filterType="filter.type.string.contains"
				classNameType="java.lang.String"/>
			</td>
			
			<td>
				<rapidFilters:textFilter property="tema"
				title="app.label.tema" filterType="filter.type.string.contains"
				classNameType="java.lang.String"/>
			</td>
			
			<td><rapidFilters:applyFilters /></td>
			<td><rapidFilters:clearFilters /></td>
		</tr>
	</table>
	<!-- param. -->
<c:if test="${claseBitacora == 'IP'}">
	<pragmatags:btnAgregar action="/BitacoraEditar" permissions="IDEASPROYECTO-ADMINISTRAR-BITACORA"/>	
</c:if>
<c:if test="${claseBitacora == 'P'}">
	<pragmatags:btnAgregar action="/BitacoraEditar" permissions="PROYECTOS-ADMINISTRAR-BITACORA"/>	
</c:if>
<!-- Se encuentra en configuracion-inicial.xml / bitacora -->	
	<toolbar:table model="list" requestURI="/BitacoraInventario.do" />
</html:form>
