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

<html:hidden property="entidadEvaluadora.id"/>	
	   		
<button onclick="javascript:switchLayer('<c:out value="${id}"/>');">...</button>
<table class="formulario" id="<c:out value='${id}'/>" border="0" width="100%" type="circle" style="<c:out value="${visible}"/>">
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.nroCBU"/>
		</td>		
		<td colspan="5">
	   		<html:text  property="entidadEvaluadora.nroCBU" maxlength="22" size="60%"/>	
			<pragmatags:error property="entidadEvaluadora.nroCBU" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.nroCuenta"/>
		</td>		
		<td colspan="5">
	   		<html:text property="entidadEvaluadora.nroCuenta" maxlength="50" size="60%"/>	
			<pragmatags:error property="entidadEvaluadora.nroCuenta" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.nombreBeneficiario"/>
		</td>		
		<td colspan="5">
	   		<html:text property="entidadEvaluadora.nombreBeneficiario" maxlength="50" size="60%"/>	
			<pragmatags:error property="entidadEvaluadora.nombreBeneficiario"/>
		</td>
	</tr>
  	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.entidadBancaria"/>
		</td>	
		<td colspan="5">
			<html:select property="entidadEvaluadora.idEntidadBancaria">
				<html:options collection="entidadesBancarias" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="entidadEvaluadora.idEntidadBancaria"/>
		</td>
	</tr>
</table>