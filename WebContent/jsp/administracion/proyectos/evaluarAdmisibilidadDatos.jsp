<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<html:form action="/EvaluarAdmisibilidadProyectoTerminar">
<html:hidden property="idProyecto"/>
<table class="formulario">
	<tr>
		<td class="obligatorio" width="20%">
			<bean:message bundle="labels" key="app.label.fechaAdmision"/>
		</td>
		<td colspan="2">
			<pragmatags:calendar propertyName="fecha" top="0" left="0" />
			<pragmatags:error property="fecha" />
		</td>
	</tr>		

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.nroDisposicion"/>
		</td>

		<td colspan="2">
			<html:text property="disposicion" maxlength="50" size="20%" readonly="false"/>
			<pragmatags:error property="disposicion" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.admiteProyecto"/>
		</td>		
		<td align="left" colspan="2">
			<html:radio property="resultado" value="APROBADO"> 
				<bean:message bundle="labels" key="app.label.si"/>
			</html:radio>
			&nbsp;&nbsp;&nbsp;
			<html:radio property="resultado" value="RECHAZADO"> 
				<bean:message bundle="labels" key="app.label.no"/>
			</html:radio>
			<pragmatags:error property="resultado" />
		</td>
	</tr>

	<!--  Fundamentación -->
	<tr>
		<td colspan="3">
			&nbsp;
		</td>
	</tr>		
	<tr>
		<td class="obligatorio" colspan="3">
			<bean:message bundle="headers" key="app.header.fundamentacion" />
		</td>
	</tr>

	<tr>
		<td align="left" colspan="3">
	   		<html:textarea property="fundamentacion" rows="4" cols="100" />
			<pragmatags:error property="fundamentacion" />
		</td>
	</tr>

	<!--  Observaciones -->

	<tr>
		<td class="opcional" colspan="3">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</td>
	</tr>

	<tr>
		<td align="left" colspan="3">
	   		<html:textarea property="observacion" rows="4" cols="100" />
			<pragmatags:error property="observacion" />
		</td>
	</tr>
</table>
</html:form>

