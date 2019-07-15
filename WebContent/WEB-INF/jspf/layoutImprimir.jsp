<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<%-- defino la key del bundle como id --%>
<bean:define id="keyTitle">
	<tiles:getAsString name="title"/>
</bean:define>

<bean:define id="keySubTitle">
	<tiles:getAsString name="subTitle"/>
</bean:define>
				
<html>
<head>
	<TITLE>
			<fmt:bundle basename="resources.Titles">
				<fmt:message key="${keyTitle}" />
			</fmt:bundle>
			
			<c:if test="${!empty keySubTitle}">
				&nbsp;
				-
				&nbsp;
				<fmt:bundle basename="resources.Titles">
					<fmt:message key="${keySubTitle}" />
				</fmt:bundle>
			</c:if>
	</TITLE>
		    
	<link rel="stylesheet" type="text/css" media="screen" href="css/estilo.css" />
	<link rel="stylesheet" type="text/css" media="print" href="css/estilo-print.css" />
	<link type="text/css" rel="stylesheet" href="css/calendario/calendar.css">
    <link rel="stylesheet" type="text/css" media="screen" href="css/tabs.css" />    
    <link rel="stylesheet" type="text/css" media="print" href="css/tabs.css" />   
     
	<script type="text/javascript" src="js/libreria.js"></script>
	<script type="text/javascript" src="js/popUpWindow.js"></script>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript" src="js/calendario/yahoo.js"></script>
	<script type="text/javascript" src="js/calendario/event.js" ></script>
	<script type="text/javascript" src="js/calendario/dom.js" ></script>
	<script type="text/javascript" src="js/calendario/calendar.js"></script>
	<script type="text/javascript" src="js/calendario/pragma-calendar.js"></script>			
    <script type="text/javascript" src="js/tabs.js"></script>	
	    
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
	<META HTTP-EQUIV="EXPIRES" CONTENT="-1" />
	
	<meta http-equiv="Page-Exit" content="blendTrans(Duration=0.2)">
</head>

<body onload="window.print();">
	<table border="0" width="100%">
	<tr>
		<td valign="top">
			<pragma:errorDisplay/>
			<pragma:infoDisplay/>
		</td>
	</tr>
	<tr>
		<td valign="top">
		<h3>
			<fmt:bundle basename="resources.Titles">
				<fmt:message key="${keyTitle}" />
			</fmt:bundle>

			<c:if test="${!empty keySubTitle}">
				&nbsp;
				-
				&nbsp;
				<fmt:bundle basename="resources.Titles">
					<fmt:message key="${keySubTitle}" />
				</fmt:bundle>
			</c:if>
		</h3>
		</td>	
	</tr>
	<tr>
		<td valign="top">
			<tiles:insert attribute="cabecera"/>
		</td>
	</tr>
	<tr>
		<td valign="top">
    		<tiles:insert attribute="body" />
		</td>
	</tr>
	<tr>
		<td valign="top">
    		<tiles:insert attribute="linkInventario" />
		</td>
	</tr>
	</table>
  </body>
</html>
