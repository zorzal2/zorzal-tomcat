<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<!-- Titulo -->

<h3>
	<bean:message bundle="titles" key="app.title.pac.cargarDesdeExcel"/>
</h3> 

<script>
function doSubmitForm() {
	var noFileError = '<bean:message bundle="errors" key="app.file.fileNotFound" />';
	if(window.document.forms[0].content.value=="" || window.document.forms[0].content.value==null) {
		alert(noFileError);
		return false;
	}
	try {
		submitForm();
	} catch(e) {
		alert(noFileError);
		return false;
	}
}
</script>

<html-el:form action="PacExcelCargar.do" method="post" enctype="multipart/form-data">
	<table>
		<tr>
			<td class="error">
			   <c:if test="${not empty errorMessage}">
				   	<c:forEach items="${errorMessage}" var="msg">
						<c:out value="${msg}" />
						<br/>
				   	</c:forEach>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.archivo"/>
				&nbsp;&nbsp;&nbsp;
				<html:file property="content" size="60"/>
				&nbsp;&nbsp;
				<pragmatags:btnDynaLabel
					javascript="doSubmitForm()"
					label="app.label.cargar" />
				<br/>
			</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.plantilla"/>
				&nbsp;&nbsp;&nbsp;
				<a type="application/zip" href="./download/Carga PAC de Proyecto.zip">Carga PAC de Proyecto.zip</a>
			</td>
		</tr>
	</table>
</html-el:form>
<br/>
<!-- Volver al inventario -->
<html-el:link action="/ProyectoPACInventario.do">
	<bean:message bundle="labels" key="app.label.volver"/>	
</html-el:link>
