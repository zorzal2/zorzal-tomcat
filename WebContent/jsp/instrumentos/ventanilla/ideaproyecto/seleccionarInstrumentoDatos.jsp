<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body>
<html-el:form action="${action}">

<html:hidden property="id"/>
<html:hidden property="page" value="0"/>
<html:hidden property="accion" value="altaProyecto"/>
<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.ventanillaPermanente"/>
		</td>
		
		<td>
			<html:select property="idInstrumento">
				<html:options collection="ventanillasPermanentes" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="idInstrumento" />
		</td>		
	</tr>	
</table>

</html-el:form>
</body>





