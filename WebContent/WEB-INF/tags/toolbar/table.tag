<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>

<%@tag body-content="tagdependent" description="Tabla Principal de la Toolbar" pageEncoding="UTF-8" dynamic-attributes="attrMap"%>

<%@ attribute name="excludeParams" required="false" %>
<%@ attribute name="model" required="true" %>
<%@ attribute name="requestURI" required="true" %>

<script language="javascript">
	var ToolbarCommands	= {
		//public
		openFilters: function() {
			window.open('about:blank', 'filtersPopup', "menubar=0,resizable=0,width=450,height=450");
			this.request(
				"ToolbarFilterGetAll.do",
				{
					toolbarId: this.toolbarId,
					userId: this.userId
				},
				"filtersPopup"
			);
		},
		
		openColumns: function() {
			window.open('about:blank', 'columnsPopup', "menubar=0,resizable=0,width=230,height=220");
			this.request(
				"ToolbarColumnGetAll.do",
				{
					toolbarId: this.toolbarId,
					userId: this.userId
				},
				"columnsPopup"
			);
		},
	    openSorting: function() {
			window.open('about:blank', 'sortCriteriaPopup', "menubar=0,resizable=0,width=450,height=300");
			this.request(
				"ToolbarSortCriterionGetAll.do",
				{
					toolbarId: this.toolbarId,
					userId: this.userId
				},
				"sortCriteriaPopup"
			);
		},
		openPageSize: function() {
			window.open('about:blank', 'pageSizePopup', "menubar=0,resizable=0,width=250,height=150");
			this.request(
				"ToolbarPageSizeGetAll.do",
				{
					toolbarId: this.toolbarId,
					userId: this.userId
				},
				"pageSizePopup"
			);
		},		
				
		//private
		request: function(action, parameters, target) {
			var requestForm = document.createElement('form');
			document.body.appendChild(requestForm);
			requestForm.method='post';
			requestForm.action=action;
			if(target) requestForm.target=target;
			if(parameters!=null) {
				var formContent = '';
				for(paramName in parameters) {
					formContent+='<'+'input type="hidden" name="'+paramName+'" value="'+parameters[paramName]+'" />';
				}
				requestForm.innerHTML=formContent;
			}
			requestForm.submit();
		},
		toolbarId: '<bean:write name="toolbarId"/>',
		userId: '<bean:write name="userId"/>'
	}
</script>


    <c:choose>
        <c:when test="${excludeParams == true}">
			<c:set var="_excludeParams" value="*"/>
        </c:when>
        <c:when test="${excludeParams == false}">
		    <c:set var="_excludeParams" value=""/>
        </c:when>
        <c:otherwise>
            <c:set var="_excludeParams" value="*"/>
        </c:otherwise>
    </c:choose>

<!--  c:set var="headerBundle" value="com.pragma.resource.ApplicationResource" /  -->
<c:set var="headerBundle" value="resources.TableHeader" />

<c:choose>
	<c:when test="${requestScope.list.hasPersistentFilters}">
		<c:set var="filterImage" value="images/toolbar/listaFiltrarActiva.gif"/>
	</c:when>
    <c:otherwise>
		<c:set var="filterImage" value="images/toolbar/listaFiltrarInactiva.gif"/>
    </c:otherwise>
</c:choose>
			
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr align="right">
		<td>
	    	<img src="images/toolbar/listaMostrar.gif" style="cursor: hand;" title="Seleccionar columnas" onclick="ToolbarCommands.openColumns()">
	       	<img src='<c:out value="${filterImage}"/>' style="cursor: hand;" title="Filtrar" onclick="ToolbarCommands.openFilters()">
			<img src="images/toolbar/listaOrdenar.gif" style="cursor: hand;" title="Ordenar" onclick="ToolbarCommands.openSorting()">
			<img src="images/toolbar/listaListado.gif" style="cursor: hand;" title="Tamaño de Página" onclick="ToolbarCommands.openPageSize()">
		</td>
	</tr>
</table>

<% try{ %>

<display-el:table export="true" excludedParams="${_excludeParams}" name="${model}" requestURI="${requestURI}" decorator="${requestScope.view.decoratorClass}" class="${requestScope.view.styleClass}">
		<display-el:setProperty name="export.excel.decorator" value="${requestScope.view.decoratorClass}"/>
		<display-el:setProperty name="export.pdf.decorator" value="${requestScope.view.decoratorClass}"/>		
        <c:forEach var="column" items="${requestScope.view.columns}">
            <c:if test="${column.visible}">
     
                 <c:choose>
			        <c:when test="${(empty column.decorator)}">
				        <c:set var="escapeXml" value="true"/>
				        <c:if test="${(not empty column.escapeHtml)}">
							 <c:if test="${(column.escapeHtml == 'false')}">
							 	<c:set var="escapeXml" value="false"/>
							 </c:if>
						 </c:if>
			        </c:when>
			        <c:otherwise>
			            <c:set var="escapeXml" value="false"/>
			        </c:otherwise>
	    		</c:choose>
	    		
	    		
	 	    		
				
	    		
				<!-- nuevo -->
                <fmt:bundle basename="${headerBundle}">
                	<fmt:message var="myKey" key="${column.title}"/>
				</fmt:bundle>
				<!-- nuevo -->
												
				<display-el:setProperty name="basic.show.header" value="true" /> 
                <display-el:column decorator="${column.decorator}" property="${column.showProperty}" media="all" escapeXml="${escapeXml}" style="text-align:${column.align};" title="${myKey}"/>
	            	            
            </c:if>
        </c:forEach>

		<%--
		<display-el:caption media="excel html pdf"> 
            <tr>
                <c:forEach items="${requestScope.view.columns}" var="column">
		            <c:if test="${column.visible}">
		            	<th>
		                	<fmt:bundle basename="${headerBundle}">
		                    	<fmt:message key="${column.title}"/>
							</fmt:bundle>		                  
						</th>
	                </c:if>
                </c:forEach>								
                
                <th colspan='<c:out value="${fn:length(requestScope.view.actions)}"/>'>                                        
                    <fmt:bundle basename="${headerBundle}">
                        <fmt:message key="${requestScope.view.actionsTitle}"/>
                    </fmt:bundle>
                </th>
            </tr>    
	</display-el:caption>	

	--%>

    <c:forEach var="action" items="${requestScope.view.actions}">
		<display-el:column property="${action.name}" media="html" style="text-align:center;" escapeXml="false" title="" headerClass="accionesInventario"/>
    </c:forEach>



</display-el:table>

	<%
	} catch(Exception e){
		com.fontar.util.Util.debug(e);
		org.apache.commons.logging.LogFactory.getLog(this.getClass()).fatal("La consulta no pudo ser ejecutada.", e);
		System.out.print(e);
		e.printStackTrace();
	%>
		<span class="error" align="center">La consulta no pudo ser ejecutada.</span>
	<%}%>
