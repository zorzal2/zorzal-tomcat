<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>

<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu-el" %>


<link rel="stylesheet" type="text/css" media="screen" href="css/global.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/coolmenu.css" />
<link rel="stylesheet" type="text/css" media="print" href="css/global.css" />
<link rel="stylesheet" type="text/css" media="print" href="css/coolmenu.css" />
  
<script type="text/javascript" src="js/coolmenus4.js"></script>
<script type="text/javascript" src="js/cm_addins.js"></script>
<script type="text/javascript" src="js/coolmenu4-config.js"></script>

<table height="3%" class="coolMenu">
	<tr>
		<td>
			<menu-el:useMenuDisplayer name="CoolMenu4" bundle="org.apache.struts.action.MESSAGE" permissions="menuPermissions">
				  	<menu-el:displayMenu name="MenuInstrumentos"/>
				  	<menu-el:displayMenu name="MenuAdministracion"/>
				  	<menu-el:displayMenu name="MenuConfiguracion"/>
				  	<menu-el:displayMenu name="MenuConsultasYReportes"/>
				  	<menu-el:displayMenu name="MenuWorkflow"/>
			</menu-el:useMenuDisplayer>
		</td>
	</tr> 
</table>
