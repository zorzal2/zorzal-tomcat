<%@ taglib uri="/tags/struts-menu-el" prefix="menu-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<menu-el:useMenuDisplayer name="TabbedMenu" bundle="org.apache.struts.action.MESSAGE">
    <menu-el:displayMenu name="VentanillaPermenenteDatosGenerales"/>
    <menu-el:displayMenu name="VentanillaPermenenteDistribucionFinanciamiento"/>
    <menu-el:displayMenu name="VentanillaPermenenteDistribucionTipoProyecto"/>    
</menu-el:useMenuDisplayer> 
