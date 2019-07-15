<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<!-- Titulo -->

<h3>
	<bean:message bundle="titles" key="app.title.modificarPresupuestoProyecto"/>
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

<c:if test="${showProjectHeader}">
	<jsp:include page="/jsp/cabecera/cabeceraProyectoRaiz.jsp" flush="true"/>
</c:if>

<html-el:form action="${cargarAction}" method="post" enctype="multipart/form-data">
	<input type="hidden" name="idProyecto" value='<bean:write name="proyectoRaiz" property="id"/>'/>
	<table width="100%">
		<c:choose>
			<c:when test="${!empty presupuesto.id}">
				<tr>
					<td>
					<tiles:insert beanName="paginaDetalle"/>
					<input type="hidden" name="idPresupuesto" value='<bean:write name="presupuesto" property="id"/>'/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td valign="middle">
						<html-el:link action="${downloadAction}">
							<img 
								src="images/download.gif"
								title="<bean:message bundle="labels" key="app.label.download"/>"
								border="0"/>
						</html-el:link>
						<html-el:link action="${downloadAction}">
							<bean:message bundle="labels" key="app.label.download"/>
						</html-el:link>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" class="obligatorio"><bean:message bundle="labels" key="app.label.fundamentacion" /></td>
				</tr>
				<tr>
					<td colspan="2" class="opcional"><bean:write name="presupuesto" property="fundamentacion" ignore="true" /></td> 
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td>
						<h2>
							<bean:message key="app.msj.noHayPresupuesto" bundle="informationals"/>
						</h2>
					</td>
				</tr>
			</c:otherwise>					
		</c:choose>
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
				<a type="application/zip" href="./download/Carga Presupuesto de Proyecto.zip">Carga Presupuesto de Proyecto.zip</a>
			</td>
		</tr>
	</table>

</html-el:form>
<br/>
<!-- Volver al inventario -->
<%-- pragmatags:btnCancelar action="/WFCancelarTarea.do" /--%>
<html-el:link action="${cerrarAction}">
	<bean:message bundle="labels" key="app.label.cerrar"/>	
</html-el:link>
