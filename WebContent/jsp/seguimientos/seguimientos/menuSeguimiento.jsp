<%@ taglib uri="/tags/struts-menu-el" prefix="menu-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<menu-el:useMenuDisplayer name="TabbedMenu" bundle="org.apache.struts.action.MESSAGE">
    <menu-el:displayMenu name="SeguimientoDatosGenerales"/>
    <menu-el:displayMenu name="SeguimientoInformeAvance" />
	
	<c:if test="${ES_FINANCIERO}">
		<c:if test="${!(ES_CF || ES_CF_CONSEJERIAS)}">
			<menu-el:displayMenu name="SeguimientoRendicionCuentasANR"/>
			<menu-el:displayMenu name="SeguimientoResumenRendicionesANR"/>		
		</c:if>
		<c:if test="${ES_CF || ES_CF_CONSEJERIAS}">
			<menu-el:displayMenu name="SeguimientoRendicionCuentasCF"/>
			<menu-el:displayMenu name="SeguimientoResumenRendicionesCF"/>		
		</c:if>
	</c:if>
	<menu-el:displayMenu name="SeguimientoAnalisisGastosVisualizacionSeguimiento"/>
</menu-el:useMenuDisplayer>