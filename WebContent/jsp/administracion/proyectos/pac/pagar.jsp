<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<script type="text/javascript" src="js/libreria.js"></script>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.ingresarPago"/></h3> 

	<%-- Header Editar Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

	<jsp:include page="visualizarItem.jsp" flush="true"/>

	<jsp:include page="visualizarPago.jsp" flush="true"/>

<html:form action="/ProyectoPACPagoGuardar">
<input type="hidden" id="selectionEvent" name="selectionEvent"/>
<html:hidden property="id" />
<table class="formulario">

	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>

</table>
		<table border="0" width="100%" class="inventario">
			<tr>
				<th><bean:message bundle="labels" key="app.label.cuota"/></th>
				<th><bean:message bundle="labels" key="app.label.fechaPago" /></th>
				<th><bean:message bundle="labels" key="app.label.montoPago" /></th>
				<th><bean:message bundle="labels" key="app.label.aplicaAnticipo" /></th>
 			 </tr>
 			<tr>
				<td align="center">
					<html:select property="cuota">
						<html:options collection="cuotas" property="value" labelProperty="label"/>
					</html:select>
					<pragmatags:error property="cuota" />
				</td>
				<td align="center">
					<pragmatags:calendar propertyName="fechaPago" top="255" left="250"/>
					<pragmatags:error property="fechaPago" />
				</td>
				<td align="center">
			   		<html:text property="montoPago" maxlength="20" style="text-align:right" />	
					<pragmatags:error property="montoPago" />
				</td>
				<td align="center">
					<html:checkbox property="esAnticipo"/>
				</td>
			</tr>
		</table>			
</html:form>
	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/ProyectoPACInventario"/>

</div>
