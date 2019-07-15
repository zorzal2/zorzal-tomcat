<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<h3>
	<bean:message bundle="titles" key="app.title.adjuntos.instrumentoDefBean"/>
		&nbsp;-&nbsp;
	<bean:message bundle="titles" key="app.title.adjuntos.administracion"/>
</h3>

<br/>		
<%-- Header Instrumento --%>
<jsp:include page="/jsp/cabecera/cabeceraInstrumento.jsp" flush="true"/>

<%-- paso como parámetro el nombre del bean al cual voy a adjuntar --%>
<jsp:include page="/jsp/misc/adjuntos/adjuntosDownload.jsp" flush="true">
	<jsp:param name="entityBeanName" value="InstrumentoDefBean" />
</jsp:include>
<pragmatags:btnCerrar/>
