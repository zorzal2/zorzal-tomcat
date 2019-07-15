<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-logic-el" prefix="logic-el" %>

<%@ attribute name="id" required="true"%>

<c:set var="visible" value="display:none;"/> 

<html:messages id="error">
    <c:set var="visible" value="display:block;"/>
</html:messages>

   <c:set var="tipoEmpresa" value="none"/>
   <c:set var="tipoNoEmpresa" value="none"/>


<button onclick="javascript:switchLayer('<c:out value="${id}"/>');">...</button>
<table class="formulario" id="<c:out value='${id}'/>" border="0" width="100%" type="circle" style="<c:out value="${visible}"/>">
	<tr>
		<td>
			<html-el:radio property="${id}.tipo" value="EMPRESA" 
			onclick="javascript:switchLayerBeneficiaria('empresa','noEmpresa',this);"> 
				<bean:message bundle="labels" key="app.label.empresa"/>
			</html-el:radio>
			&nbsp;&nbsp;&nbsp;
			<html-el:radio property="${id}.tipo" value="NO_EMPRESA" 
			onclick="javascript:switchLayerBeneficiaria('noEmpresa','empresa',this);"> 
				<bean:message bundle="labels" key="app.label.noEmpresa"/>
			</html-el:radio>
		</td>
	</tr>
	<table class="formulario" id="empresa" border="0" width="100%" type="circle" style="display:none;">
	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.subTipo" /></td>
		<td colspan="4">
		<html:select property="tipoEmpresa">
			<html:options collection="tipoEmp"
				property="value" labelProperty="label" />
		</html:select></td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaInicioActividad"/>
		</td>		
		<td colspan="5">
	   		<html-el:text property="${id}.fechaInicioActividad" maxlength="50" size="60%"/>	
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.numeroConstitucion"/>
		</td>		
		<td colspan="5">
	   		<html-el:text property="${id}.numeroConstitucion" maxlength="50" size="60%"/>	
		</td>
	</tr>
	   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.ciiu"/></td>
      	<td>
	      	<html-el:select property="${id}.idCiiu">
				<c:if test="${not empty ciius}"><!-- Esto se aplica para cuando se carga la pagina sin jurisdicciones -->
 					<html:options collection="ciius" property="value" labelProperty="label" />
				</c:if>
			</html-el:select>
		</td>
    </tr>    
	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.categorizacion" /></td>
		<td colspan="4">
		<html:select property="codigoCategorizacion">
			<html:options collection="tipoCat"
				property="value" labelProperty="label" />
		</html:select></td>
	</tr>
	<tr>
		<td class="obligatorio" valign="top">
			<bean:message bundle="labels" key="app.label.empleoPermanente"/>
		</td>		
		<td colspan="1">
			<pragmatags:empleoPermanente id="empleoPermanente"/>
		</td>
	</tr>
	<tr>
		<td class="obligatorio" valign="top">
			<bean:message bundle="labels" key="app.label.localizacion"/>
		</td>		
		<td colspan="1">
			<pragmatags:localizacion id="localizacionEconomica"/>
		</td>
	</tr>
	</table>
	<table class="formulario" id="noEmpresa" border="0" width="100%" type="circle" style="display:none;">
	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.subTipo" /></td>
		<td colspan="4">
		<html:select property="tipoNoEmpresa">
			<html:options collection="tipoNoEmp"
				property="value" labelProperty="label" />
		</html:select></td>
	</tr>
	<tr>
		<td class="obligatorio" valign="top">
			<bean:message bundle="labels" key="app.label.localizacion"/>
		</td>		
		<td colspan="1">
			<pragmatags:localizacion id="localizacionEconomica"/>
		</td>
	</tr>
	</table>
</table>