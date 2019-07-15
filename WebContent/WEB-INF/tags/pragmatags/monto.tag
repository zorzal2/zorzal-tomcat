<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>

<%@ tag body-content="empty" %>

<%@ attribute name="numero" required="true"%>
<%@ attribute name="prefijo" required="false"%>
<%@ attribute name="sufijo" required="false"%>

<%
	Object prefijo = jspContext.findAttribute("prefijo");
	if(prefijo==null) prefijo = "";
	else prefijo=prefijo.toString();
	Object sufijo = jspContext.findAttribute("sufijo");
	if(sufijo==null) sufijo = "";
	else sufijo = sufijo.toString();
		
	Object oNumber = jspContext.getExpressionEvaluator().evaluate("${"+jspContext.findAttribute("numero")+"}", java.lang.Object.class, jspContext.getVariableResolver(), null);
	if(oNumber != null && !"".equals(oNumber)) {
		out.write(prefijo.toString());
		if(oNumber instanceof java.lang.Number) {
			out.write(com.pragma.util.StringUtil.formatTwoDecimalForPresentation((java.lang.Number)oNumber));
		} else {
			java.math.BigDecimal x = new java.math.BigDecimal(oNumber.toString());
			out.write(com.pragma.util.StringUtil.formatTwoDecimalForPresentation(x));
		}
		out.write(sufijo.toString());
	}
%>
