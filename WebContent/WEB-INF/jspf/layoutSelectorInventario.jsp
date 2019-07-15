<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<html>
	<head>
		<TITLE>
			<tiles:getAsString name="title"/>
		</TITLE>
		<link rel="stylesheet" type="text/css" media="screen" href="css/estilo.css" />
		<link rel="stylesheet" type="text/css" media="print" href="css/estilo-print.css" />
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
		<META HTTP-EQUIV="EXPIRES" CONTENT="-1" />
		<meta http-equiv="Page-Exit" content="blendTrans(Duration=0.2)">	
		<script type="text/javascript" src="js/libreria.js"/>
		<script type="text/javascript" src="js/prototype.js"/>
		<script type="text/javascript" src="js/popUpWindow.js"></script>	
	</head>
	<body> 
		<div>
			<h3><tiles:getAsString name="tituloSeleccion"/></h3>
			<tiles:insert attribute="body" />
			<a href="javascript:window.close()">Cerrar</a>
		</div>
	</body>
</html>