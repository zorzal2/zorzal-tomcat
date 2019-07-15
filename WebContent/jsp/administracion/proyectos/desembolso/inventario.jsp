<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/displaytag-el" prefix="display-el"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags"%>
<%@ taglib uri="/tags/displaytag-12" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>

<c:if test="${!empty presupuesto}">
	<jsp:include page="cronogramaDeDesembolsos.part.jsp" flush="true">
		<jsp:param name="cronograma" value="cronograma" />
		<jsp:param name="return-url" value="/ProyectoDesembolsoInventario.do" />
	</jsp:include>
</c:if>

<c:if test="${empty presupuesto}">
	<span class="error"><br>
	<br>
	<bean:message bundle="errors" key="app.proyecto.presupuesto.empty" /> <br>
	</span>
</c:if>
