<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<h3>
	<bean:message bundle="titles" key="app.title.procedimientos.ingresarPedidoAutorizacion"/>
</h3>

<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

<html:form action="/PedirAutorizacionGuardar">
<html:hidden property="id"/>

<table class="formulario">	
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.tipoAdquisicion"/>
		</td>
		<td>
			<html:select property="idTipoAdquisicion" onchange="javascript:changeFormAction('PedirAutorizacion')">
				<html:options collection="tiposAdquisicion" property="value" labelProperty="label" />
			</html:select>
			<pragmatags:error property="idTipoAdquisicion" />
		</td>			
	</tr>

	<c:if test="${!empty param.idTipoAdquisicion}">
	<tr></tr>
	
	<table>
		<tr>
			<td width="11%" align="right">
				<pragmatags:btnDynaLabel javascript="javascript:checkAll('itemsArray')" label="app.label.seleccionarTodos"/>
				&nbsp;
			</td>	
		</tr>
	</table>

	<pragmatags:tabla 
		toolbar="no" 
		pagesize="1000"
		toolbaroptions="" 
		columns="codigo,descripcion,etapa,rubro.nombre,montoEstimadoFontarDTO,fechaEstimadaCompraDTO,estado.descripcion" 
		align="left,left,left,left,right,center,center"
		actions="selectorItem"
		decorator="com.fontar.web.decorator.seguimientos.controlAdquisicion.ItmesPedirAutorizacionWrapper"
		requestURI="PedirAutorizacion.do" 
		collection="listaItems" 
		entity=""/>

	<tr></tr>
	
	<table class="formulario">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.nroProcedimiento"/>
			</td>
			<td>
				<html:text property="codigo" maxlength="100" size="10%" />
				<pragmatags:error property="codigo" />
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
	</c:if>
</table>
	
<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/ProyectoInventario"/>

</html:form>


