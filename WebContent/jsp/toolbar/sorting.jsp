<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 

<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.fontar.util.ResourceManager"%>
<%@page import="com.pragma.toolbar.config.user_config.UserColumnConfiguration"%>
<%@page import="com.pragma.util.WebUtils"%>

<%@page import="com.pragma.toolbar.data.ToolbarOrder"%>
<html>
	<head>
		<title><bean:message key="app.titles.ordenarLista"/></title>	
		<link  href="css/estilo.css" type=text/css rel=stylesheet />
		<link  href="css/new.css" type=text/css rel=stylesheet />		
		<link  href="css/non-esthetic.css" type=text/css rel=stylesheet />
		<link  href="css/calendar.css" type=text/css rel=stylesheet />
		<script  src="js/calendario/yahoo.js" language="javascript" type="text/javascript"></script>
		<script  src="js/calendario/calendar.js" language="javascript" type="text/javascript"></script>
		<script  src="js/calendario/dom.js" language="javascript" type="text/javascript"></script>
		<script  src="js/calendario/event.js" language="javascript" type="text/javascript"></script>
		<script  src="js/log.js" language="javascript" type="text/javascript"></script>
		<script  src="js/calendario/pragma-calendar.js" language="javascript" type="text/javascript"></script>

		<script  src="js/toolbar/sorting/classes.js" language="javascript" type="text/javascript"></script>
		<script  src="js/toolbar/utils.js" language="javascript" type="text/javascript"></script>
		<script  src="js/toolbar/sorting/model.js" language="javascript" type="text/javascript"></script>
		<script  src="js/toolbar/sorting/view.js" language="javascript" type="text/javascript"></script>
		<script  src="js/toolbar/sorting/controller.js" language="javascript" type="text/javascript"></script>

		<meta HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
		<meta HTTP-EQUIV="EXPIRES" CONTENT="-1" />
		<meta http-equiv="Page-Exit" content="blendTrans(Duration=0.2)"/>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
		<script>
			var Log=Pragma.log.BlindLog; /* Logueo desactivado */
			//var Log=Pragma.log.Log; /* Logueo activado */
			
			<%
			//Cargo las columnas
			Collection columns = (Collection)pageContext.findAttribute("columns");
			for(Iterator iter = columns.iterator(); iter.hasNext();) {
			    UserColumnConfiguration column = (UserColumnConfiguration)iter.next();
			    if(column.getAllowSorting().booleanValue()) {
			    %>
				    Model.addColumn(
				    	new Column(
				    		'<%=column.getProperty()%>', 
					    	<%=WebUtils.toSafeQuotedJavascriptString(ResourceManager.getHeaderResource(column.getTitle()))%>)
				    );
				    <%
			    }
			}
			
			//Cargo los ordenes actuales
			Collection sortCriteria = (Collection)pageContext.findAttribute("sortCriteria");
			for(Iterator iter = sortCriteria.iterator(); iter.hasNext();) {
			    ToolbarOrder sortCriterion = (ToolbarOrder)iter.next();
			    %>
			    Model.addSortCriterion(
			    	new SortCriterion(
			    		Model.columnMap('<%=sortCriterion.getProperty()%>'), 
			    		'<%=sortCriterion.getOrderType().getName()%>'
					),
					<%=sortCriterion.getOrder().intValue()-1%>
			    );
			    <%
			}
			%>
			//Cargo usuario y toolbar
			Model.setUserId('<%=pageContext.findAttribute("userId")%>');
			Model.setToolbarId('<%=pageContext.findAttribute("toolbarId")%>');
			//Inicializacion
			function Init() {
				Controller.attachView(View);
				Controller.attachModel(Model);
			}
		</script>
	</head>
	<body style="MARGIN: 10px; overflow:auto" onload="Init();">
		<%-- <table cellpadding="5" cellspacing="0">
	    	<tr>
	        	<td><bean:message key="app.titles.sorting"/></td>
	        </tr>
	    </table>--%>
	    <br/>
		
	<center>
	<%//-- Seleccion de columnas --%>
	<table cellspacing="0" cellpadding="0" border="0" width="100%">
		<tr>
			<td>&nbsp;</td>
			<td align="left">
				<table>
					<tr>
						<td class="opcional" ><bean:message key="app.titles.property"/></td>
						<td>
							<select id="columnList">
							</select>
						</td>
						<td>
							<img src="images/btnNuevo.GIF" style="cursor:hand;" title="<bean:message key='app.titles.new'/>" onclick="Controller.newSortCriterion()">							
						</td>
					</tr>
				</table>
			</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<%//!-- Fin Seleccion de columnas --%>
		<br/>
	    <table cellpadding="3" cellspacing="0" class="List" width="100%">
	    	<thead>
				<tr>
					<th class="titulo"><bean-el:message key="app.titles.property"/></th>
					<th class="titulo"><bean-el:message key="app.titles.criteria"/></th>
					<th class="titulo" colspan="2"><bean-el:message key="app.titles.accion"/></th>
				</tr>
			</thead>
			<tbody id="sortCriterionRows">
				<%-- Prototipo de fila --%>
				<tr id="sortCriterionRowPrototype" style="display: none">
					<td class="obligatorio" style="text-align:left;" id="newSortCriterion_column" valign="middle" width="40%"></td>
					<td style="text-align:left;" valign="middle">
						<select
							id="newSortCriterion_sortOptions"
							onchange="Controller.changeSortOption(this.sortCriterionId, this.value)">
							<option value="Ascending"><bean:message key="sorting.ascending"/></option>
							<option value="Descending"><bean:message key="sorting.descending"/></option>
						</select>
					</td>
					<td valign="middle">
						<center>
							<img 
								id="newSortCriterion_pushUp"
								src="images/btnSubir.gif" 
								style="cursor:hand;cursor:pointer;" 
								title="<bean:message key="app.titles.pushUp"/>"
								onclick="Controller.pushUpCriterion(this.sortCriterionId)">&nbsp;
							<img 
								id="newSortCriterion_pullDown"
								src="images/btnBajar.gif" 
								style="cursor:hand;cursor:pointer;" 
								title="<bean:message key="app.titles.pullDown"/>"
								onclick="Controller.pullDownCriterion(this.sortCriterionId)">&nbsp;
						</center>
					</td>
					<td valign="middle">
						<center>
							<img 
								id="newSortCriterion_delete"
								src="images/btnEliminar.gif" 
								style="cursor:hand;cursor:pointer;" 
								title="<bean:message key="app.titles.remove"/>"
								onclick="Controller.removeSortCriterion(this.sortCriterionId)">&nbsp;
						</center>
					</td>
				</tr>
			</tbody>
		</table>
		<br/>
	    <pragmatags:btnOkCancel okJavascript="Controller.submit();" cancelJavascript="window.close()"/>
		</center>
		<!-- Mensajes -->
		<span class="Messages">
			<i id="column_already_selected">La columna ya ha sido seleccionada</i>
		</span>
	</body>
</html>