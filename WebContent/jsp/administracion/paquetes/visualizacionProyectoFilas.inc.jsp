<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<!-- tabla con los datos de ProyectoFilaDTO's  -->
<c:if test="${VisualizacionProyectos_noCF}">
	<pragmatags:tabla 
		toolbar="no"
		pagesize="1000"
		toolbaroptions=""
		columns="proyecto,nombreEntidad,titulo,evaluaciones,totalSolicitado,beneficioFONTARSolicitado,totalAprobado,beneficioFONTARAprobado,recomendacion,resultado"
		align="left,left,left,left,right,right,right,right,left,left"
		actions="${param.actions}"
		decorator="com.fontar.web.decorator.proyectos.proyectos.ProyectoFilaDTOWrapper"
		requestURI="${param.uri}" 
		collection="collection"/>
</c:if>
<c:if test="${VisualizacionProyectos_CFSinCargaDeAlicuota}">
	<pragmatags:tabla 
		toolbar="no"
		pagesize="1000"
		toolbaroptions=""
		columns="proyecto,nombreEntidad,titulo,evaluaciones,totalSolicitado,totalAprobado,recomendacion,resultado"
		align="left,left,left,left,right,right,left,left"
		actions="${param.actions}"
		decorator="com.fontar.web.decorator.proyectos.proyectos.ProyectoFilaDTOWrapper"
		requestURI="${param.uri}" 
		collection="collection"/>
</c:if>
<c:if test="${VisualizacionProyectos_CFConCargaDeAlicuota}">
	<pragmatags:tabla 
		toolbar="no"
		pagesize="1000"
		toolbaroptions=""
		columns="proyecto,nombreEntidad,titulo,evaluaciones,totalSolicitado,totalAprobado,alicuotaSolicitada,alicuotaAdjudicada,beneficioFONTARAdjudicado,recomendacion,resultado"
		align="left,left,left,left,right,right,right,right,right,left,left"
		actions="${param.actions}"
		decorator="com.fontar.web.decorator.proyectos.proyectos.ProyectoFilaDTOWrapper"
		requestURI="${param.uri}" 
		collection="collection"/>
</c:if>
<!-- tabla con los datos de ProyectoFilaDTO's  -->