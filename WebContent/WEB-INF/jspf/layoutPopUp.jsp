<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<html>
	<head>
		<TITLE>
			<tiles:getAsString name="title"/>
		</TITLE>
	
		<link rel="stylesheet" type="text/css" media="screen" href="css/estilo.css" />
		<link rel="stylesheet" type="text/css" media="print" href="css/estilo-print.css" />
		<link rel="stylesheet" type="application/pdf" media="all" href="css/estilo-pdf.css" />
		<link rel="stylesheet" type="text/css" href="css/calendario/calendar.css">
	
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
		<%-- include body --%>
			<table width="100%" height="250" border="0">
			<tr>
			<td>
				<pragma:errorDisplay></pragma:errorDisplay>
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