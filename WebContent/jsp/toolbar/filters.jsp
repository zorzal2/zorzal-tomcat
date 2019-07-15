<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 

<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.pragma.toolbar.properties.FilterType"%>
<%@page import="com.fontar.util.ResourceManager"%>
<%@page import="com.pragma.toolbar.data.filter.PropertyBasedToolbarFilter"%>
<%@page import="com.pragma.toolbar.config.user_config.UserColumnConfiguration"%>
<%@page import="com.pragma.util.WebUtils"%>

<html>
	<head>
		<title><bean:message key="app.titles.filtrarLista"/></title>
		<link  href="css/estilo.css" type=text/css rel=stylesheet />
		<link  href="css/new.css" type=text/css rel=stylesheet />		
		<link  href="css/non-esthetic.css" type=text/css rel=stylesheet />
		<link  href="css/calendario/calendar.css" type=text/css rel=stylesheet />
		<script  src="js/calendario/yahoo.js" language="javascript" type="text/javascript"></script>
		<script  src="js/calendario/calendar.js" language="javascript" type="text/javascript"></script>
		<script  src="js/calendario/dom.js" language="javascript" type="text/javascript"></script>
		<script  src="js/calendario/event.js" language="javascript" type="text/javascript"></script>
		<script  src="js/log.js" language="javascript" type="text/javascript"></script>
		<script  src="js/calendario/pragma-calendar.js" language="javascript" type="text/javascript"></script>

		<script  src="js/toolbar/filters/classes.js" language="javascript" type="text/javascript"></script>
		<script  src="js/toolbar/utils.js" language="javascript" type="text/javascript"></script>
		<script  src="js/toolbar/filters/model.js" language="javascript" type="text/javascript"></script>
		<script  src="js/toolbar/filters/view.js" language="javascript" type="text/javascript"></script>
		<script  src="js/toolbar/filters/controller.js" language="javascript" type="text/javascript"></script>

		<meta HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
		<meta HTTP-EQUIV="EXPIRES" CONTENT="-1" />
		<meta http-equiv="Page-Exit" content="blendTrans(Duration=0.2)"/>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
		<script>
			var Log=Pragma.log.BlindLog; /* Logueo desactivado */
			//var Log=Pragma.log.Log; /* Logueo activado */
			
		    var supportedFilterTypes;
			//Cargo usuario y toolbar
			Model.setUserId('<%=pageContext.findAttribute("userId")%>');
			Model.setToolbarId('<%=pageContext.findAttribute("toolbarId")%>');
			<%
			//Cargo los tipos de filtro
			Collection filterTypes = (Collection)pageContext.findAttribute("filterTypes");
			for(Iterator iter = filterTypes.iterator(); iter.hasNext();) {
			    FilterType filterType = (FilterType)iter.next();
			    %>
			    new FilterType(
			    	'<%=filterType.getCode()%>', 
			    	<%=WebUtils.toSafeQuotedJavascriptString(ResourceManager.getMessageResource(filterType.getName()))%>, 
			    	'<%=filterType.getDataType().getName()%>');
			    <%
			}
			//Cargo las columnas
			Collection columns = (Collection)pageContext.findAttribute("columns");
			for(Iterator iter = columns.iterator(); iter.hasNext();) {
			    UserColumnConfiguration column = (UserColumnConfiguration)iter.next();
			    if(column.getAllowFiltering().booleanValue()) {
				    %>
				    supportedFilterTypes = [];
				    <% 
				    Collection supportedFilterTypes = column.supportedFilterTypes();
				    for(Iterator iterFT = supportedFilterTypes.iterator(); iterFT.hasNext();) {
						FilterType filterType = (FilterType)iterFT.next();
						%>
						supportedFilterTypes.push(FilterType['<%=filterType.getCode()%>']);
						<%
				    }
				    %>
				    Model.addColumn(
				    	new Column(
				    		'<%=column.getProperty()%>', 
					    	<%=WebUtils.toSafeQuotedJavascriptString(ResourceManager.getHeaderResource(column.getTitle()))%>,
					    	supportedFilterTypes)
				    );
				    <%
				}
			}
		    //Cargo los filtros
		    Collection filters = (Collection)pageContext.findAttribute("filters");
		    for(Iterator iterFilters = filters.iterator(); iterFilters.hasNext();) {
		    	PropertyBasedToolbarFilter filter = (PropertyBasedToolbarFilter)iterFilters.next();
				%>
				Model.addFilter(
					new Filter(
						Model.columnMap('<%=filter.getProperty()%>'),
						FilterType['<%=filter.getFilterType().getCode()%>'],
						<%=WebUtils.toJSCode(filter.getValue())%>
					)
				);
				<%
	    	}
			%>



			//Subclasifico function InputCalendar para adecuar el posicionamiento
			var InputCalendar_old = InputCalendar;
			InputCalendar = function(id){
				this._super = InputCalendar_old;
				this._super(id);
				
				//override
				this.setCalendarPosition = function(){
					var container = window.document.getElementById( this.calendarContainerName );
					var input = window.document.getElementById( this.id );
					//Put calendar according current position of input
					var eL=0;var eT=0;
					for(var p=input; p&&p.tagName!='BODY'; p=p.offsetParent){
						eL+=p.offsetLeft;
					    eT+=p.offsetTop;
					}
					var eH = input.offsetHeight;
					var dH = container.style.pixelHeight;
					var sT = document.body.scrollTop;
					container.style.left = eL;
					if(eT-dH >= sT && eT+eH+dH > document.body.clientHeight+sT)
						container.style.top=eT-dH;
					else
			      		container.style.top=eT+eH;
				}
				
			}
			
			//Inicializacion
			function Init() {
				Controller.attachView(View);
				Controller.attachModel(Model);
			}
		</script>
	</head>
	<body style="MARGIN: 10px" onload="Init();">

		<%-- <table cellpadding="5" cellspacing="0">
	    	<tr>
	        	<td><bean:message key="app.titles.filters"/></td>
	        </tr>
	    </table>--%>
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
							<img src="images/btnNuevo.GIF" style="cursor:hand;" title="<bean:message key='app.titles.new'/>" onclick="Controller.newFilter()">
							
						</td>
					</tr>
				</table>
			</td>
			<td>&nbsp;</td>
		</tr>

	</table>
	<%//-- Fin Seleccion de columnas --%>
		<br/>
	    <table cellpadding="3" cellspacing="0" width="100%">
	    	<thead>
				<tr>
					<th class="titulo"><bean-el:message key="app.titles.property"/></th>
					<th class="titulo"><bean-el:message key="app.titles.operador"/></th>
					<th class="titulo"><bean-el:message key="app.titles.condicion"/></th>
					<th class="titulo"><bean-el:message key="app.titles.accion"/></th>
				</tr>
			</thead>
			<tbody id="filterRows">
				<%-- Prototipo de fila --%>
				<tr id="filterRowPrototype" style="display: none">
					<td class="obligatorio" style="text-align:left;" id="newFilter_column"></td>
					<td style="text-align:left;">
						<select
							id="newFilter_filterTypes"
							onchange="Controller.changeFilterType(this.filterId, this.value)">
						</select>
					</td>
					<td valign="middle">
						<input
							type="text" 
							id="newFilter_value"
							maxlength="200"
							onkeyup="Controller.changeFilterValue(this.filterId, this.value)"/>
						<%//-- Boton de Calendario - Solo se muestra para las fechas --%>
						<img 
							src="images/calendario/calendar.gif" 
							onClick="javascript:showCalendar(this.inputId)"
							id="newFilter_CalendarButton"
							style="cursor:hand;cursor:pointer; display: none;"/>
						<div id="newFilter_CalendarContainer" style="display:none;position:absolute"></div>
					</td>
					<td>
						<center>
							<img 
								id="newFilter_delete"
								src="images/btnEliminar.gif" 
								style="cursor:hand;cursor:pointer;" 
								title="<bean:message key="app.titles.remove"/>"
								onclick="Controller.removeFilter(this.filterId)">&nbsp;
						</center>
					</td>
				</tr>
			</tbody>
		</table>
		<br/>
	    <pragmatags:btnOkCancel okJavascript="Controller.submit();" cancelJavascript="window.close()"/>
		
		</center>
		<%//-- Mensajes --%>
		<span style="display:none;">
			<i id="validation_required">Requerido %labelName%</i>
			<i id="validation_date_format">Fecha inválida %labelName%</i>
			<i id="validation_number_format">Numero inválido %labelName%</i>
			<i id="validation_boolean_format">El campo %labelName% debe tener los valores si/no</i>
		</span>
	</body>
</html>