<%@ taglib uri="/tags/struts-menu-el" prefix="menu-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<menu-el:useMenuDisplayer name="TabbedMenu" bundle="org.apache.struts.action.MESSAGE">
    <menu-el:displayMenu name="LlamadoConvocatoriaDatosGenerales"/>
    <menu-el:displayMenu name="LlamadoConvocatoriaDistribucionFinanciamiento"/>
    <menu-el:displayMenu name="LlamadoConvocatoriaDistribucionTipoProyecto"/>    
</menu-el:useMenuDisplayer> 
