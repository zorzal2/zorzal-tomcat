<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<script>
	function aceptar() {
		form = AdjuntoUploadForm;
		form.action = "<c:out value='${param.entityBeanName}'/>CargarAdjuntoAction.do?id=" + form.id.value;
		form.submit();
	}
	
	function onDownload(idAdjuntoContenido,nombre,tipoContenido){
		form = document.AdjuntoUploadForm;
		form.action = "<c:out value='${param.entityBeanName}'/>DownloadAdjuntoAction.do?id=" + idAdjuntoContenido + "&filename=" + nombre + "&contentType="+ tipoContenido;
	   	form.submit();
	}
	
	function onDelete(idAdjunto){
		if (confirm("<bean:message bundle='informationals' key='app.msj.confirmaEliminacion'/>")){
			form = document.AdjuntoUploadForm;
			form.idAdjunto.value=idAdjunto;
			document.AdjuntoUploadForm.idAdjunto.value=idAdjunto;
			form.action = "<c:out value='${param.entityBeanName}'/>EliminarAdjuntoAction.do";
		   	form.submit();
		}
	}
</script>

<html:form action="/EliminarAdjuntoAction" >

<% 

Object permiso = pageContext.getExpressionEvaluator().evaluate("${param.permisoParaAgregar}", java.lang.String.class, pageContext.getVariableResolver(), null);

%>


<html:hidden property="id"/>	
<html:hidden property="idAdjunto"/>	
		<pragmatags:btnAgregar javascript="aceptar()" permissions="<%=permiso.toString()%>" />	
		<display-el:table name="adjuntos" class="inventario" decorator="com.fontar.web.decorator.instrumentos.adjuntos.AdjuntoDownloadWrapper">		
  		    <display-el:setProperty name="basic.show.header" value="false">
				<display-el:caption>
					<tr>
						<th><bean-el:message bundle="headers" key="app.adjunto.nombre" /></th>	    
						<th><bean-el:message bundle="headers" key="app.adjunto.descripcion" /></th>	    
					    <th><bean-el:message bundle="headers" key="app.adjunto.fecha" /></th>	    
						<th><bean-el:message bundle="headers" key="app.adjunto.cantidadLongitud" /></th>
						<th><bean-el:message bundle="headers" key="app.adjunto.tipoContenido" /></th>
						<th colspan=2><bean-el:message bundle="headers" key="app.header.acciones" /></th>
				    </tr> 
				</display-el:caption> 
	            <display-el:column property="nombre" />   
	            <display-el:column property="descripcion" /> 
	            <display-el:column property="fecha" />             
	            <display-el:column property="cantidadLongitud" />             
	            <display-el:column property="tipoContenido" />             
	            <display-el:column property="actions"/>
				<display-el:column property="borrarAdjunto"/>
            </display-el:setProperty>
        </display-el:table>
		<br/>
</html:form >



