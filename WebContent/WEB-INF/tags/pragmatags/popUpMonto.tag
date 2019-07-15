<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 

<%@ attribute name="entidad" required="true"%>
<%@ attribute name="propiedad" required="true"%>
<%@ attribute name="montoActual" required="true"%>
<%@ attribute name="id" required="true"%>



<pragma:authorize permissions="SUPERUSUARIO">

<%
   	String id = String.valueOf(jspContext.getExpressionEvaluator().evaluate("${" + String.valueOf(jspContext.findAttribute("id"))+ "}", java.lang.String.class, jspContext.getVariableResolver(), null));

	String montoActual = String.valueOf(jspContext.getExpressionEvaluator().evaluate("${" + String.valueOf(jspContext.findAttribute("montoActual"))+ "}", java.lang.String.class, jspContext.getVariableResolver(), null));
	montoActual = montoActual.replaceAll("%","");
	montoActual = montoActual.replaceAll(",",".");
%>
	
	<!--  aceptar action   -->
	<button onclick="javascript:AbrirPopup('CargarEdicionMonto.do?entidad=<c:out value='${entidad}'/>&propiedad=<c:out value='${propiedad}'/>&id=<%=id%>&montoActual=<%=montoActual%>','400','200');">
		<img src="images/edicion.gif">
	</button>
</pragma:authorize>
