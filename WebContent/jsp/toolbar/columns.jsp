<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<head>
	<link  href="css/estilo.css" type=text/css rel=stylesheet />
	<title>
		<bean:message key="toolbar.titles.selectColumns"/>		
	</title>	
</head>	

<html-el:form action="/ToolbarColumnSetAll">
	<table cellpadding="3" cellspacing="0" class="List" width="100%">
		<thead>
			<tr>
				<th class="titulo" width="50%">
					<bean-el:message key="app.titles.visibility"/>
				</th>
				<th class="titulo" width="50%">
					<bean-el:message key="app.titles.property"/>					
				</th>				
			</tr>
		</thead>
	</table>
	<div style="overflow: auto;height: 140px; width: 100%;">
		<table cellpadding="3" cellspacing="0" class="List" width="100%">
			<c:forEach items="${ToolbarPersistentColumnsForm.columns}" var="column" varStatus="loopStatus">
				<tr>
					<td width="50%">
						    <input type="checkbox" name="foo_<c:out value="${loopStatus.index}"/>"
						      onclick="javascript:elements['visible[<c:out value="${loopStatus.index}"/>]'].value=this.checked;"
								<c:if test="${column.visible}">
									<c:out value="checked"/>
								</c:if>
						    >
					    <html-el:hidden property="visible[${loopStatus.index}]"/>
					</td>
					<td class="opcional">
						<bean-el:message key="${column.title}" bundle="headers"/>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	
	<pragmatags:btnOkCancel okJavascript="document.ToolbarPersistentColumnsForm.submit();" cancelJavascript="window.close()"/>
		
</html-el:form>



