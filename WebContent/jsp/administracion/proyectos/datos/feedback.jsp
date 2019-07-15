<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<script type="text/javascript">
	var selections = {};
	
	function prepareAndSend() {
		var selects = document.getElementsByTagName('select');
		var str = "";
		for(var i = 0; i<selects.length; i++) {
			str+=selects[i].name;
			str+=">>";
			str+=selects[i].value;
			str+=",";
		}
		str+="a>>b";  //dummy
		document.getElementById('feedback').value = str;
		submitForm();
	}
</script>

<h1>
	<bean:message bundle="titles" key="app.title.feedbackRequerido"/>
</h1> 
<br>

<c:forEach items="${feedback}" var="feedbackRequest">
	<div class="feedback">
		<div class="feedback-message"><c:out value="${feedbackRequest.message}" /></div>
		<html:select property="${feedbackRequest.id}" value="${feedbackRequest.suggestedDefaultValue.value}">
			<html:options collection="feedbackRequest" property="value" labelProperty="label" />
		</html:select>
	</div>
</c:forEach>
<html:form action="/ProyectosExcelReintentar.do">
	<input type="hidden" id="feedback" name="feedback" />
</html:form>
<pragmatags:btnOkCancel okJavascript="prepareAndSend()"	cancelAction="/ProyectosExcelIngresar.do" />