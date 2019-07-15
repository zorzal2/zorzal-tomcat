<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<table width="100%">
	<tr>
		<th class="titulo"><bean:message bundle="titles" key="app.title.detalleDeRubro"/></th> 
	</tr>
	<tr>
		<td>&nbsp;</td> 
	</tr>
	<tr>
		<td>
			<c:choose>
			 <c:when test="${!empty requestScope.tablas}">
				<c:forEach var="tabla" items="${requestScope.tablas}">
					<h2><c:out value="${tabla.titulo}" /></h2>
						<c:choose>
						 <c:when test="${tabla.isNotEmpty}">
							<table class="inventario">
								<tr>
									<c:forEach var="campo" items="${tabla.campos}">
									    <fmt:bundle basename="resources.TableHeader">
								          	<fmt:message var="myKey" key="${campo.label}"/>
										</fmt:bundle>
										<th><c:out value="${myKey}"/></th>
									</c:forEach>	
								</tr>
								<c:forEach var="item" items="${tabla.items}">
									<tr>
										<c:forEach var="campo" items="${tabla.campos}">
											<td style='<c:out value="${campo.style}"/>'><bean:write name="item" property="${campo.propiedad}"/></td>
										</c:forEach>	
									</tr>
								</c:forEach>
							</table>
						</c:when>
						<c:otherwise>
							<h4>
								<bean:message bundle="titles" key="app.title.noHayItemsEnRubro"/>
							</h4>
						</c:otherwise>
					</c:choose>
					
				</c:forEach>
			</c:when>
			<c:otherwise>
				<h4>
					<bean:message bundle="titles" key="app.title.noHayItemsEnRubro"/>
				</h4>
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
</table>
