<%@ taglib uri="/tags/struts-menu-el" prefix="menu-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<menu-el:useMenuDisplayer name="TabbedMenu" bundle="org.apache.struts.action.MESSAGE">
    <menu-el:displayMenu name="ProyectoDatos" />
    <menu-el:displayMenu name="ProyectoPresupuesto" />
    <c:if test="${ES_ADQUISICION}">
 	   <menu-el:displayMenu name="ProyectoPAC" />
 	</c:if>
    <c:if test="${not ES_PATENTE}">
	    <menu-el:displayMenu name="ProyectoPlan"/>    
	</c:if>
    <menu-el:displayMenu name="ProyectoBitacora"/>
    <menu-el:displayMenu name="ProyectoEvaluaciones"/>
	<menu-el:displayMenu name="ProyectoSeguimiento"/>
    <menu-el:displayMenu name="ProyectoExpedientes"/>
    <menu-el:displayMenu name="ProyectoAdjuntos"/>
	<c:if test="${tipoInstrumento == 'CREDITO'}">
	    <menu-el:displayMenu name="ProyectoDesembolso"/>
    </c:if>
</menu-el:useMenuDisplayer>
