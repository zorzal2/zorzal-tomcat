<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<script>
	
	function onDownload(idAdjuntoContenido,nombre,tipoContenido){
		form = document.AdjuntoUploadForm;
		form.action = "SeguimientoBeanDownloadAdjuntoAction.do?id=" + idAdjuntoContenido + "&filename=" + nombre + "&contentType="+ tipoContenido;
	   	form.submit();
	}
	
	function onDelete(idAdjunto){
		if (confirm("<bean:message bundle='informationals' key='app.msj.confirmaEliminacion'/>")){
			form = document.AdjuntoUploadForm;
			form.idAdjunto.value=idAdjunto;
			document.AdjuntoUploadForm.idAdjunto.value=idAdjunto;
			form.action = "SeguimientoTabEliminarAdjuntoAction.do";
		   	form.submit();
		}
	}
</script>

<html:form action="/EliminarAdjuntoAction" >

<html:hidden property="id"/>	
<html:hidden property="idAdjunto"/>	
		
		<c:if test="${!anulado}">
			<pragmatags:btnAgregar action="/SeguimientoTabCargarAdjuntoAction" />
		</c:if>
		
		<display:table name="adjuntos" class="inventario" decorator="com.fontar.web.decorator.instrumentos.adjuntos.AdjuntoDownloadWrapper">
			<display:column property="nombre" />   
			<display:column property="descripcion" /> 
			<display:column property="fecha" />             
			<display:column property="cantidadLongitud"  />             
			<display:column property="tipoContenido" />             
			<display:column property="actions"/>

			<c:if test="${!anulado}">
				<display:column property="borrarAdjunto"/>
			</c:if>

		</display:table>
		<br/>

</html:form >



