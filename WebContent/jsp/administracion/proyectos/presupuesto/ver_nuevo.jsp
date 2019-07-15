<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<!-- Titulo -->
<h3><bean:message bundle="titles" key="app.title.nuevoPresupuesto"/></h3> 

<c:if test="${showProjectHeader}">
	<jsp:include page="/jsp/cabecera/cabeceraProyectoRaiz.jsp" flush="true"/>
</c:if>

<tiles:insert beanName="paginaDetalle"/>
<br/>
<html-el:form action="${guardarAction}">
	<table class="formulario">
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.fundamentacion" />
			</td>
			<td>
				<textarea name="fundamentacion" onkeyup="if(this.value.length>3500) this.value = this.value.substr(0, 3500)" cols="100" rows="2"></textarea>	
			</td>
		</tr>
	</table>
<br/>
</html-el:form>
<!-- Volver al inventario -->
<pragmatags:btnDynaLabel
		javascript="submitForm()"
		label="app.label.guardar" />
<pragmatags:btnCancelar action="${cancelarAction}" />