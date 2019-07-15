<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<!-- Titulo -->
<h3><bean:message bundle="titles" key="app.title.visualizarReconsideracion"/></h3> 

<!-- Encabezado -->
<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>


<table class="formulario">		
	<colgroup>
		<col width="25%">
		<col width="100%">
	</colgroup>	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaDictamen"/>
		</td>
		<td>
			<bean:write name="reconsideracion" property="fecha" formatKey="app.date.largeFormat"/>
		</td>
	</tr>		

	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.nroDictamen"/>
		</td>
		<td>
			<bean:write name="reconsideracion" property="dictamen"/>
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.reconsiderarProyecto"/>
		</td>		
		<td>
			<c:if test="${reconsideracion.resultado=='APROBADO'}">
				<bean:message bundle="labels" key="app.label.si"/>
			</c:if>
			<c:if test="${reconsideracion.resultado!='APROBADO'}">
				<bean:message bundle="labels" key="app.label.no"/>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.nroResolucion"/>
		</td>
		<td>
			<bean:write name="reconsideracion" property="resolucion"/>
		</td>
	</tr>

	<tr>
		<td>&nbsp;</td>
	</tr>
	
	<tr>
		<td class="obligatorio" colspan="2">
			<bean:message bundle="headers" key="app.header.fundamentacion" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<bean:write name="reconsideracion" property="fundamentacion"/>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>	
	<tr>
		<td class="obligatorio" colspan="2">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<bean:write name="reconsideracion" property="observacion"/>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>

<!-- Volver al inventario -->
<pragmatags:btnCerrar/>
