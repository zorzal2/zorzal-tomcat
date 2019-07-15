<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<LINK href="css/estilo.css" type=text/css rel=stylesheet>
	<script type="text/javascript" src="js/libreria.js"></script>
</head>
<body>

<br>
<h3>
	<bean:message bundle="titles" key="app.title.proyectosPitec"/>	
</h3>

<html:form action="ProyectoPitecInventario">

	<pragmatags:tabla 
		toolbar="no"
		toolbaroptions=""
		columns="id,codigo"
		actions="linkSeleccion"
		decorator="com.fontar.web.decorator.instrumentos.pitec.ProyectoPitecSelectorWrapper"				   
		requestURI="ProyectoPitecInventario.do" 
		collection="collection" 
		entity="ProyectoPitec" />

</html:form>

<pragmatags:btnCerrar javascript="window.close();"/>

</body>
</html>



