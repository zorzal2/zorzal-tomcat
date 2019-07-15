<%@ taglib uri="/tags/struts-menu-el" prefix="menu-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<menu-el:useMenuDisplayer name="TabbedMenu" bundle="org.apache.struts.action.MESSAGE">
    <menu-el:displayMenu name="EvaluacionSeguimientoDatosGenerales"/>

	<c:if test="${sessionScope['ES_TECNICA']}">
	    <menu-el:displayMenu name="EvaluacionSeguimientoPlanProyecto"/>
	</c:if>	

	<c:if test="${(not sessionScope['ES_CF']) and (not sessionScope['ES_CF_CONSEJERIAS'])}">
	    <menu-el:displayMenu name="EvaluacionSeguimientoRendicionCuentasANR"/>
	</c:if>
	<c:if test="${sessionScope['ES_CF'] or sessionScope['ES_CF_CONSEJERIAS']}">
	    <menu-el:displayMenu name="EvaluacionSeguimientoRendicionCuentasCF"/>
	</c:if>
	<menu-el:displayMenu name="SeguimientoAnalisisGastosEvaluacion"/>

</menu-el:useMenuDisplayer>