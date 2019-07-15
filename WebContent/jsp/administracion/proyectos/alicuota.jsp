<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.alicuota"/></h3>

	<%-- Header Controlar Paquete --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

</div>

<html:form action="/AlicuotaEditarGuardar">
<html:hidden property="idProyecto"/>
	<br>	

	<table class="formulario">
	
		<tr>
			<th colspan="2" class="titulo">
				<bean:message bundle="labels" key="app.label.general" />
			</th>
		</tr>
		<tr>
			<td class="obligatorio" width="20%">
				<bean:message bundle="labels" key="app.label.alicuota"/>
			</td>
			<td align="left">
				<html:text property="alicuota" maxlength="6" size="15" style="text-align:right" />
				<pragmatags:error property="alicuota" />
			</td>
		</tr>
	</table>
	<br/>
	
	<!------------Observaciones------------>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>	
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones"/>
		</th>		
	</tr>
	<tr>
		<td colspan="2">
			<html:textarea property="observaciones" rows="4" cols="100" />
			<pragmatags:error property="observaciones" />
		</td>
	</tr>
	<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/WFCancelarTarea.do"/>
</html:form>
</html>