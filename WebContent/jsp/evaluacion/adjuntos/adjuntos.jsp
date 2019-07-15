<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<h3>
	<bean:message bundle="titles" key="app.title.adjuntos.evaluacionBean"/>
		&nbsp;-&nbsp;
	<bean:message bundle="titles" key="app.title.adjuntos.agregar"/>
</h3>

<%-- Header Proyecto + Evaluacion --%>
<jsp:include page="cabecera${evaluacion.proyecto.class.simpleName}.jsp" flush="true"/>

<%-- paso como parámetro el nombre del bean al cual voy a adjuntar --%>
<jsp:include page="/jsp/misc/adjuntos/adjuntos.jsp" flush="true">
	<jsp:param name="entityBeanName" value="EvaluacionBean" />
</jsp:include>