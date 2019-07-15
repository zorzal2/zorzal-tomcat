<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<h3>
	<bean:message bundle="titles" key="app.title.facturacion"/>
</h3>

<body onload="checkVariableClose('windowClose');">
<html:form action="/FacturacionGuardar">

<html:hidden property="windowClose" />
<c:if test="${!empty id}">
	<html:hidden property="id" />
</c:if>

<table class="formulario">
	<tr>
		<th colspan="2">General</th>
	</tr>
		<tr>
			<td colspan="2">
				<pragmatags:btnAgregar javascript="popUpFacturacionData('tablaFacturacion');"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<table id="tablaFacturacion" border="0" width="100%" class="inventario">
				<tr>
					<th>Año</th>
					<th>Facturación($)</th>
					<th>Acciones</th>
	 			 </tr>
	 			 <c:if test="${!empty facturaciones}">
				<c:forEach items="${facturaciones}" var="facturaciones">
				<tr>
					<input id="numeroPeriodico" name="numeroPeriodico" value="${facturaciones[0]}" type="hidden"/>
					<input id="numeroFacturacion" name="numeroFacturacion" value="${facturaciones[1]}" type="hidden"/>
					<td><c:out value="${facturaciones[0]}"/></td>
					<td><c:out value="${facturaciones[1]}"/></td>
					<td><button name="Delete" onclick="deleteRow(this , 'tablaFacturacion')"><img src="images/eliminar.gif"/></button></td>					
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


