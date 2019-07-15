<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<script type="text/javascript">
function switchLayerBeneficiariaOnLoad(obj)
{
	if (obj[0].checked) {
		switchLayerBeneficiaria('empresa','noEmpresa',obj[0]);
	}
	else if (obj[1].checked) {
		switchLayerBeneficiaria('noEmpresa','empresa',obj[1]);
	}
}

function getId() {
	return '<c:out value="${param.id}" />';
}

</script>

<c:set var="tipoEmpresa" value="none"/>
<c:set var="tipoNoEmpresa" value="none"/>

<body onload="checkVariableClose('windowClose');
			javascript:switchLayerBeneficiariaOnLoad(document.getElementsByName('tipo'));
			javascript:toggleVisibility('descEmpresa',document.getElementsByName('tipoEmpresa')[0].options[document.getElementsByName('tipoEmpresa')[0].selectedIndex].text=='Otro');
			">

<h3>
	<bean:message bundle="titles" key="app.title.entidadesBeneficiarias"/>
</h3>

<html:form action="/EntidadBeneficiariaGuardar">

<input type="hidden" name="selectionEvent" id="selectionEvent"/>

<html:hidden property="windowClose" />
<c:if test="${!empty id}">
	<html:hidden property="id" />
</c:if>

<table class="formulario">
	<tr>
		<th colspan="6" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
</table>
<table class="formulario">
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
	<tr>
		<td colspan="2">
			<html-el:radio property="tipo" value="EMPRESA"
			onclick="javascript:switchLayerBeneficiaria('empresa','noEmpresa',this);"> 
				<bean:message bundle="labels" key="app.label.empresa"/>
			</html-el:radio>
			&nbsp;&nbsp;&nbsp;
			<html-el:radio property="tipo" value="NO_EMPRESA" 
			onclick="javascript:switchLayerBeneficiaria('noEmpresa','empresa',this);"> 
				<bean:message bundle="labels" key="app.label.noEmpresa"/>
			</html-el:radio>
			<pragmatags:error property="tipo" />
		</td>
	</tr>
</table>
<table id="empresa" border="0" width="100%" type="circle" style="display:none;">
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
		<tr>
			<td class="obligatorio"><bean:message bundle="labels" key="app.label.subTipo" /></td>
			<td colspan="2">
			<html:select property="tipoEmpresa" onchange="javascript:toggleVisibility('descEmpresa',this.options[this.selectedIndex].text=='Otro');">
				<html:options collection="tipoEmp"
					property="value" labelProperty="label" /> 
					&nbsp;&nbsp;&nbsp;
					<html:text property="descEmpresa" maxlength="50" size="60%" style="visibility:hidden"/>
			</html:select></td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.fechaInicioActividad"/>
			</td>		
			<td colspan="2">
				<pragmatags:calendar propertyName="fechaInicioActividad" top="255" left="250" />
				<pragmatags:error property="fechaInicioActividad" />
			</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.numeroConstitucion"/>
			</td>		
			<td colspan="2">
	   			<html-el:text property="numeroConstitucion" maxlength="50" size="60%"/>	
				<pragmatags:error property="numeroConstitucion" />
			</td>
		</tr>
	   	<tr>
			<td class="opcional"><bean:message bundle="labels" key="app.label.ciiu"/></td>
      		<td>
				<pragmatags:selectorInventario width="75" entidad="Ciiu" />
			</td>
    	</tr>    
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.facturacion"/>
			</td>		
			<td colspan="2">
				<button onclick="popUpFacturacion();">
				...
				</button>
			</td>
		</tr>
		<tr>
			<td class="opcional"><bean:message bundle="labels" key="app.label.categorizacion" /></td>
			<td colspan="2">
			<html:select property="codigoCategorizacion">
				<html:options collection="tipoCat"
					property="value" labelProperty="label" />
			</html:select></td>
		</tr>
		<tr>
			<td class="opcional" valign="top">
				<bean:message bundle="labels" key="app.label.empleoPermanente"/>
			</td>		
			<td colspan="2">
				<pragmatags:empleoPermanente id="empleoPermanente"/>
			</td>
		</tr>
</table>
<table id="noEmpresa" border="0" width="100%" type="circle" style="display:none;">
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
		<tr>
			<td class="obligatorio"><bean:message bundle="labels" key="app.label.subTipo" /></td>
			<td colspan="2">
			<html:select property="tipoNoEmpresa">
				<html:options collection="tipoNoEmp"
					property="value" labelProperty="label" />
			</html:select></td>
		</tr>
</table>
<table class="formulario">
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
		<tr>
			<td class="obligatorio" valign="top">
				<bean:message bundle="labels" key="app.label.domicilioFiscal"/>
			</td>		
			<td colspan="2">
				<pragmatags:localizacion id="localizacion"/>
			</td>
		</tr>
</table>
<table class="formulario">
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.tributaria"/>
		</td>				
		<td class="opcional">
			<html:select property="idTributaria">
				<html:options collection="tributarias" property="value" labelProperty="label" />
			</html:select>
		</td>
	</tr>	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.emerix"/>
		</td>
		
		<td>
			<html:text property="emerix" maxlength="20"/>
			<pragmatags:error property="emerix" />
		</td>
	</tr>
</table>
<table class="formulario">
			<th colspan="6" class="titulo">
				<bean:message bundle="headers" key="app.header.composicion" />
			</th>
</table>
<table>
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	
		<tr>
			<td colspan="2">
				<html-el:link href="#" onclick="popUpEntidadBeneficiariaSelector(getId());">
				<%--<html:image page="/images/agregar.gif" bundle="labels" altKey="app.label.agregar"/>--%>
				<bean:message bundle="labels" key="app.label.agregar"/>		
				</html-el:link>				
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<table id="tablaEntidad" border="0" width="100%" class="inventario">
				<tr>
					<th>Identificador</th>
					<th>CUIT</th>
					<th>Denominación</th>
					<th>Accion</th>
	 			 </tr>
	 			 <c:if test="${!empty entidades}">
				<c:forEach items="${entidades}" var="entidades">
				<tr>
					<input id="idEntidades" name="idEntidades" value="${entidades[0]}" type="hidden"/>
					<input id="cuits" name="cuits" value="${entidades[1]}" type="hidden"/>
					<input id="denominaciones" name="denominaciones" value="${entidades[2]}" type="hidden"/>
					<td><c:out value="${entidades[0]}"/></td>
					<td><c:out value="${entidades[1]}"/></td>
					<td><c:out value="${entidades[2]}"/></td>
					<td><button name="Delete" onclick="deleteRow(this , 'tablaEntidad')"><img src="images/eliminar.gif"/></button></td>					
				</tr>
				</c:forEach>
				</c:if>
			</table>			
			
			</td>
		</tr>	
</table>

<pragmatags:btnOkCancel okJavascript="submitForm()" cancelJavascript="cerrarPopUp()"/>
</html:form>
</body>