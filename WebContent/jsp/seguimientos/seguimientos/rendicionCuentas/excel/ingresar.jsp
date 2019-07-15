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
	<bean:message bundle="titles" key="app.title.rendiciones.cargarDesdeExcel"/>
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

<html-el:form action="RendicionesExcelCargar.do" method="post" enctype="multipart/form-data">
	<html-el:hidden property="seguimientoId"/>
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
				<a type="application/zip" href="./download/Carga Rendiciones de Seguimiento.zip">Carga Rendiciones de Seguimiento.zip</a>
			</td>
		</tr>
	<c:if test="${tieneRendiciones}">
		<tr>
			<td>
				<table class="InfoMessage">
					<tr>
						<td class="text">
							<b><bean:message key="app.label.rendiciones.excel.borrarExistentesInfo" bundle="labels"/></b>
							<br/>
							<br/>
							<html-el:checkbox property="borrarExistentes">
								<bean:message key="app.label.rendiciones.excel.borrarExistentes" bundle="labels"/>
							</html-el:checkbox>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</c:if>
	</table>
</html-el:form>
<br/>
<!-- Volver al inventario -->
<html-el:link action="/RendicionesExcelCancelar.do">
	<bean:message bundle="labels" key="app.label.volver"/>	
</html-el:link>
