<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<c:set var="headerKey" value="app.title.altaComision" />
<c:if test="${!empty param.id}" >
	<c:set var="headerKey" value="app.title.edicionComision" />
</c:if>
<h3>
	<fmt:bundle basename="resources.Titles">
		<fmt:message key="${headerKey}"/>
	</fmt:bundle>
</h3>
<html:form action="/ComisionGuardar">
<html:hidden property="id"/>
<table class="formulario">	
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.denominacion"/>
		</td>		
		<td>
	   		<html:text property="denominacion" maxlength="60" size="60%"/>	
			<pragmatags:error property="denominacion" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.codResolucion"/>
		</td>		
		<td>
	   		<html:text property="resolucion" maxlength="20" size="20%"/>	
			<pragmatags:error property="resolucion" />
		</td>
	</tr>	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaAlta"/>
		</td>		
		<td>
	   		<pragmatags:calendar propertyName="fechaAlta" top="0" left="0" />
			<pragmatags:error property="fechaAlta" />
		</td>
	</tr>	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.fechaBaja"/>
		</td>		
		<td>
	   		<pragmatags:calendar propertyName="fechaBaja" top="0" left="0" />
			<pragmatags:error property="fechaBaja" />
		</td>
	</tr>	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.descripcion"/>
		</td>	
		<td colspan="6">
	   		<html:textarea property="descripcion" rows="2" cols="100" />	
	   		<pragmatags:error property="descripcion" />
   		</td>
	</tr>	
	<c:if test="${!empty param.id}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.estado"/>
			</td>		
			<td class="opcional">
				<html:select property="activo">
					<html:options collection="estadosEntidad" property="value" labelProperty="label" />
				</html:select> 
				<pragmatags:error property="activo" />
			</td>
		</tr>
	</c:if>		

	<%-- Observaciones --%>
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</th>
	</tr>
	<tr>
		<td colspan="2">
	   		<html:textarea property="observacion" rows="2" cols="100" />	
	   		<pragmatags:error property="observacion" />
   		</td>
	</tr>		
</table>
<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/ComisionInventario"/>
</html:form>
