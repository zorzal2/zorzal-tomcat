<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<h3>
	<bean:message bundle="titles" key="app.title.versionarVentanillaPermanente" />
	&nbsp;
	<c:out value="${identificador}" />
</h3>

<html:form action="/VentanillaPermanenteVersionarFinish">

<html:hidden property="id"/>
<html:hidden property="page" value="2"/>

<table class="formulario">
	<tr>
		<th colspan="6" class="titulo"><bean:message bundle="headers" key="app.header.versionVentanillaPermanente" /></th>
	</tr>

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.version" />
		</td>
		<td>
			<c:out value="${version}"/>
		</td>
	</tr>

	<br>

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fecha" />
		</td>
		<td>
			<c:out value="${fecha}"/>
		</td>
	</tr>

	<br>

	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.nroResolucion" />
		</td>
		<td>
			<html:text property="codigo" maxlength="20" size="20%" />
			<pragmatags:error property="codigo" />
		</td>
	</tr>

	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.observaciones" />
		</td>
		<td>
			<html:textarea property="descripcion" rows="4" cols="80" />	
			<pragmatags:error property="descripcion" />
		</td>
	</tr>
</table>
<br>
<br>
<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/VentanillaPermanenteEditar"/>	
</html:form>
