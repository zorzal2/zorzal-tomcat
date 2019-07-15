<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<script type="text/javascript" src="js/libreria.js"></script>


<div>
	<!-- Titulo -->
	<h3><bean:message bundle="titles" key="app.title.anularPac"/></h3> 

	<%-- Header Editar Proyecto --%>
	<jsp:include page="/jsp/cabecera/cabeceraProyecto.jsp" flush="true"/>

	<jsp:include page="visualizarItem.jsp" flush="true"/>

<html:form action="/PACAnularGuardar">
<table class="formulario">
	<tr>
		<th class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones" />
		</th>
	</tr>
	<tr>
		<td>
	   		<html:textarea property="observaciones" cols="100" rows="3"/>
	   		<pragmatags:error property="observaciones" />	
   		</td>
	</tr>
</table>
</html:form>
	<!-- Volver al inventario -->
	<pragmatags:btnOkCancel okJavascript="submitForm();" cancelAction="/ProyectoPACInventario"/>

</div>
