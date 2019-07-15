<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<html>
<head>
	<link  href="css/estilo.css" type=text/css rel=stylesheet />
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="js/libreria.js"></script>
<title>
	<bean:message key="toolbar.titles.pageSize"/>		
</title>
		<script>
		function changePageSize()
		{
			var i=0;
			var size=0;

			while ( i < document.ToolbarPageSizeForm.pageSize.length) {
			  if (document.ToolbarPageSizeForm.pageSize[i].checked) {
			    if (i!=3) {
					document.ToolbarPageSizeForm.submit();
				}
			    else {
			    	size=document.ToolbarPageSizeForm.customValue.value;

			    	if ((size=="") || (!isPositive(size)) || (!IsNum(size))) {
						alert("Debe seleccionar un valor");
			    	}
			    	else {
						document.ToolbarPageSizeForm.submit();
			    	}
			    }
			  }
			  i++;
			}
		}

		</script>
</head>

<body>

<html-el:form action="/ToolbarPageSizeSetAll">
	<table cellpadding="3" cellspacing="0" class="List" width="100%" align="center">
		<c:forEach items="${pageSizeOptions}" var="pageSizeOption">
		<tr>		
			<td>
				<html-el:radio property="pageSize" value="${pageSizeOption.value}" onfocus="document.ToolbarPageSizeForm.customValue.value=''"/>
			</td>
			<td class="opcional">
				<c:out value="${pageSizeOption.label}"/>
			</td>
		</tr>		
		</c:forEach>

		<tr>
			<td>				
				<html-el:radio property="pageSize" value="" onfocus="document.ToolbarPageSizeForm.customValue.focus()"/>
			</td>
			<td class="opcional">
				<bean:message key="toolbar.titles.other"/>&nbsp;
				<html-el:text property="customValue" onfocus="document.ToolbarPageSizeForm.pageSize[3].checked=true" maxlength="3" onkeypress="if(event.keyCode == 13){ event.keyCode = null; }"/>
			</td>
		</tr>
	
	</table>
	
	<pragmatags:btnOkCancel okJavascript="changePageSize();" cancelJavascript="window.close()"/>
</html-el:form>

</body>
</html>