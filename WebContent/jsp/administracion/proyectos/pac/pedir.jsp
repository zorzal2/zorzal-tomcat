<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<script type="text/javascript" src="js/libreria.js"></script>

<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.pedirDesembolso"/></h3> 

	<%-- Header Editar Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

	<jsp:include page="visualizarItem.jsp" flush="true"/>

	<jsp:include page="visualizarDesembolso.jsp" flush="true"/>

<html:form action="/ProyectoPACPedirGuardar">
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
				<th><bean:message bundle="labels" key="app.label.nroDesembolso" /></th>
				<th><bean:message bundle="labels" key="app.label.cuota"/></th>
				<th><bean:message bundle="labels" key="app.label.fechaPedido" /></th>
				<th><bean:message bundle="labels" key="app.label.moneda"/></th>
				<th><bean:message bundle="labels" key="app.label.montoADesembolsar" /></th>
				<th><bean:message bundle="labels" key="app.label.esUltimo" /></th>
 			 </tr>
 			<tr>
				<td align="center">
					<html:text property="ordenPago" maxlength="50" style="text-align:right" />	
					<pragmatags:error property="ordenPago" />
				</td>
				<td align="center">
					<html:text property="cuota" maxlength="50" style="text-align:right" />	
					<pragmatags:error property="cuota" />
				</td>
				<td align="center">
					<pragmatags:calendar propertyName="fechaPedido" top="255" left="250"/>
					<pragmatags:error property="fechaPedido" />
				</td>
				<td align="center">
					<html:select property="idMoneda">
						<html:options collection="monedas" property="value" labelProperty="label"/>
					</html:select>
					<pragmatags:error property="idMoneda" />
				</td>
				<td align="center">
			   		<html:text property="montoDesembolsado" maxlength="20" style="text-align:right" />	
					<pragmatags:error property="montoDesembolsado" />
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
