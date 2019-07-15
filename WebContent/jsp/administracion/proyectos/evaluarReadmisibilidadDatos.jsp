<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<html:form action="/EvaluarReadmisibilidadProyectoTerminar">
<html:hidden property="idProyecto"/>
<table class="formulario">
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	

	<!------------General------------>
	<tr>		
		<th colspan="5" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>	
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fechaAdmision"/>
		</td>		
		<td class="opcional">
			<bean:write name="admisibilidad" property="fechaAdmision"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.nroDisposicion"/>
		</td>		
		<td class="opcional">
			<bean:write name="admisibilidad" property="nroDisposicion"/>
		</td>

	</tr>
	
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.fundamentacionAdmision"/>
		</td>		
		<td class="opcional">
			<bean:write name="admisibilidad" property="fundamentacion"/>
		</td>
	</tr>

	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.observacionesAdmision"/>
		</td>		
		<td class="opcional">
			<bean:write name="admisibilidad" property="observaciones"/>
		</td>
	</tr>
</table>

<table class="formulario">
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
	<tr>&nbsp;&nbsp;&nbsp;</tr>
	<tr>
		<td class="obligatorio" width="20%">
			<bean:message bundle="labels" key="app.label.fechaReadmision"/>
		</td>
		<td colspan="2">
			<pragmatags:calendar propertyName="fecha" top="0" left="0" />
			<pragmatags:error property="fecha" />
		</td>
	</tr>		

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.nroResolucion"/>
		</td>

		<td colspan="2">
			<html:text property="resolucion" maxlength="50" size="20%" readonly="false"/>
			<pragmatags:error property="resolucion" />
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

