<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<html>
<head>
	<TITLE>
		<tiles:getAsString name="title"/>
	</TITLE>
	
	<link rel="stylesheet" type="text/css" media="print" href="css/estilo-print.css" />
	<link rel="stylesheet" type="text/css" media="screen" href="css/estilo.css" />
	<link type="text/css" rel="stylesheet" href="css/calendario/calendar.css">
	
	<script type="text/javascript" src="js/libreria.js"></script>
	<script type="text/javascript" src="js/popUpWindow.js"></script>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript" src="js/calendario/yahoo.js"></script>
	<script type="text/javascript" src="js/calendario/event.js" ></script>
	<script type="text/javascript" src="js/calendario/dom.js" ></script>
	<script type="text/javascript" src="js/calendario/calendar.js"></script>
	<script type="text/javascript" src="js/calendario/pragma-calendar.js"></script>			
	
	
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
	<META HTTP-EQUIV="EXPIRES" CONTENT="-1" />
	
	<meta http-equiv="Page-Exit" content="blendTrans(Duration=0.2)">
</head>

<body>
    <%-- include header --%>
    <tiles:insert attribute="header"/>
    <%-- include menu --%>
    <tiles:insert attribute="menu"/>
    <%-- include body --%>
    <br>
	<table width="100%" border="0">
	<tr>
		<td valign="top">
			<pragma:errorDisplay/>
			<pragma:infoDisplay/>
		</td>
	</tr>
	<tr>
		<td valign="top">
    		<tiles:insert attribute="body" />
		</td>
	</tr>
	</table>
  </body>
</html>
