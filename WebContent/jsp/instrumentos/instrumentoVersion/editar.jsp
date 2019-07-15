<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>


<script type="text/javascript" src="js/popUpWindow.js"></script>

<body><!-- onload="toggleVisibility('divBoxMonto',document.forms[0].tipoFinanciamiento.value=='Por Beneficiario');
			toggleVisibility('selectorComision', document.forms[0].permiteComision.checked);"> --> 
<br>

<%-- TODO: FF / los titulos deben ser tratados de otro manera! --%>
<h3>
	<bean:message bundle="titles" key="app.title.edicionInstrumentoVersion"/>
</h3>


<html-el:form action="${submitAction}">

<html:hidden property="id"/>

<table class="formulario">	
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.version"/>
		</td>		
		<td>
	   		<html:text property="version" maxlength="50" size="20%"/>	
			<pragmatags:error property="version" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fecha"/>
		</td>		
		<td>
	   		<html:text property="fecha" maxlength="50" size="20%"/>	
			<pragmatags:error property="fecha" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.resolucion"/>
		</td>		
		<td>
	   		<html:text property="resolucion" maxlength="50" size="20%"/>	   		
			<pragmatags:error property="resolucion" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.observaciones" />
		</td>
		<td>
	   		<html:textarea property="observaciones" rows="2" cols="100" />	
   		</td>
	</tr>
	

</table>

<pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/InstrumentoVersionInventario"/>

</html-el:form>

</body>