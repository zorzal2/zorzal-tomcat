<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<title>
	<bean:message bundle="titles" key="app.title.localizacion"/>
</title>

<script type="text/javascript">
function updateTxt(idJur) {
	var element = opener.document.getElementById('<c:out value="${param.idElement}" />');
//	alert(element.innerHTML);
	element.innerHTML = "Jurisdiccion: "+document.getElementsByTagName("option")[idJur].text;
  	submitForm();
}
</script>

<body onload="checkVariableClose('windowClose');">

<h3>
	<bean:message bundle="titles" key="app.title.localizacion"/>
</h3>

<html:form action="/LocalizacionGuardar">

<html:hidden property="windowClose" />
<html:hidden property="id" />

<table class="formulario">
	<tr>
		<th colspan="2" class="titulo">General</th>
	</tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.direccion"/></td>
      	<td><html:text property="direccion" /><pragmatags:error property="direccion" /></td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.localidad"/></td>
      	<td><html:text property="localidad" /><pragmatags:error property="localidad" /></td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.departamento"/></td>
      	<td><html:text property="departamento" /><pragmatags:error property="departamento" /></td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.codigoPostal"/></td>
      	<td><html:text property="codigoPostal" /><pragmatags:error property="codigoPostal" /></td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.idJurisdiccion"/></td>
      	<td>
	      	<html:select property="idJurisdiccion">
				<c:if test="${not empty jurisdicciones}"><!-- Esto se aplica para cuando se carga la pagina sin jurisdicciones -->
					<html:options collection="jurisdicciones" property="value" labelProperty="label" />
				</c:if>
			</html:select>
			<pragmatags:error property="idJurisdiccion" />
		</td>
    </tr>    
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.pais"/></td>
      	<td><html:text property="pais" /><pragmatags:error property="pais" /></td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.telefono"/></td>
      	<td><html:text property="telefono" /><pragmatags:error property="telefono" /></td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.fax"/></td>
      	<td><html:text property="fax" /><pragmatags:error property="fax" /></td>
    </tr>      
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.email"/></td>
      	<td><html:text property="email" /><pragmatags:error property="email" /></td>
    </tr>  
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.paginaWeb"/></td>
      	<td><html:text property="paginaWeb" /><pragmatags:error property="paginaWeb" /></td>
    </tr>  
                             
</table>

<pragmatags:btnOkCancel okJavascript="updateTxt(idJurisdiccion.value)" cancelJavascript="cerrarPopUp()"/>
</html:form>
</body>

