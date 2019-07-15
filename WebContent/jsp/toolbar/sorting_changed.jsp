<%@page import="com.pragma.util.WebUtils"%>
<html>
	<body>
		<script>
			var userId=<%=WebUtils.toSafeQuotedJavascriptString(pageContext.findAttribute("userId").toString())%>;
			var toolbarId=<%=WebUtils.toSafeQuotedJavascriptString(pageContext.findAttribute("toolbarId").toString())%>;
			try {
				window.opener.Toolbar.evConfigChanged.toolbarId = toolbarId;
				window.opener.Toolbar.evConfigChanged.userId = userId;
				window.opener.Toolbar.evConfigChanged.fire();
			} catch(e) {}
			window.close()
		</script>
	</body>
</html>