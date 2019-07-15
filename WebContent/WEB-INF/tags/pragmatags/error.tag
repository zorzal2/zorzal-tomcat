<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%@ attribute name="property" required="true"%>

<html-el:messages id="error" property="${property}">
	<span class="error"><bean:write name="error"/></span>
</html-el:messages>	