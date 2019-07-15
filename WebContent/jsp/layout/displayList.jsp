<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>


<html>
<head>
	<title>Listado</title>
	<script language="javascript">
		function printForm(){
			div_botones.style.visibility='hidden'
			div_botones.style.display='none'
			window.print();
			div_botones.style.visibility='visible';
			div_botones.style.display='';
		}
	</script>
</head>

<body>
	<table class="formulario">
		<tr>
			<td align="left" nowrap>
				<div id="div_botones" align=right nowrap>
					<input type="button" onclick="javascript:window.close();" value="Cerrar">
					<input type="button" onclick="printForm();" value="Imprimir">
					<input name=Filtro id=Filtro  type=hidden value="">
					<input name=IDLista id=IDLista type=hidden value="TblCliente">
				</div>
			</td>
		</tr>
	</table>
	<h3>Listado</h3>

<%-- width="100%" --%>	

<display-el:table id="data" class="inventario" 
				partialList="false"
				style="inventario" 
				name="${param.collection}"
				requestURI="${param.requestURI}" 
				decorator="${param.decorator}">

	<display-el:setProperty name="basic.show.header" value="true">
		<c:set var="media" value="all"/>
		<c:forEach items="${param.columns}" var="column">
				<c:set var="myKey" value="app.label.${column}" />
				<bean:define id="title" >
					<fmt:bundle basename="resources.FieldLabels">
						<fmt:message key="${myKey}" />
					</fmt:bundle>
				</bean:define>
						
		<%-- nowrap="true" valign="middle" align="left" --%>
				<display-el:column sortable="true" title="${title}" media="${media}" property="${column}" />
		</c:forEach>
	</display-el:setProperty>

</display-el:table>


</body>
</html>

