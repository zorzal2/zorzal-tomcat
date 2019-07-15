<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<td>
	<span>Fecha </span>
</td>

<td>
	
	<html:select property="filter(presentacionResolucion)">        
		<html:options collection="comboPresentacionResolucion" property="value" labelProperty="label"/>
	</html:select>
	
	<span>desde </span><pragmatags:calendar propertyName="filter(before)" top="0" left="0"/>
	
	<span>hasta </span><pragmatags:calendar propertyName="filter(after)" top="0" left="0"/>
	
</td>