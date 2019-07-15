<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!-- 
<script type="text/javascript" src="js/libreria.js"></script>
<script type="text/javascript" src="js/popUpWindow.js"></script>
 -->

<h3>
	<bean:message bundle="titles" key="app.title.procedimientos.generarNuevoPedidoAutorizacion"/>
</h3>

<jsp:include page="/jsp/cabecera/cabeceraProcedimiento.jsp" flush="true"/>


<html:form action="/GenerarPedidoAutorizacionGuardar">

<table class="formulario">	
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	
	<tr></tr>
	
	<pragmatags:tabla 
		toolbar="no" 
		pagesize="1000"
		toolbaroptions="" 
		columns="id,descripcion,etapa,rubro.nombre,montoEstimadoFontar,fechaEstimadaCompra,codigoEstado.descripcion,resultadoUffaBid" 
		actions=""
		decorator="com.fontar.web.decorator.seguimientos.controlAdquisicion.ItmesPedirAutorizacionWrapper"
		requestURI="GenerarPedidoAutorizacion.do" 
		collection="listaItems" 
		entity=""/>

	<p>

	<table>
		<tr>
			<td class="obligatorio" width="15%">
				<bean:message bundle="labels" key="app.label.nroProcedimiento"/>
			</td>
			<td class="opcional">
				<c:out value="${codigo}"/>
			</td>
		</tr>
		
		<tr>
			<td class="obligatorio" width="15%">
				<bean:message bundle="labels" key="app.label.tipoAdquisicion"/>
			</td>
			<td class="opcional">
				<c:out value="${tipoAdquisicion}"/>
			</td>
		</tr>		

		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.fechaRecepcion"/>
			</td>
			<td>
				<pragmatags:calendar propertyName="fechaRecepcion" top="255" left="250" />
				<pragmatags:error property="fechaRecepcion" />
			</td>
		</tr>

		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.observaciones"/>
			</td>
			<td>
		   		<html:textarea property="descripcion" cols="100" rows="3"/>
		   		<pragmatags:error property="descripcion" />	
			</td>
		</tr>
	</table>
</table>
	
<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/ProcedimientoInventario"/>

</html:form>


