<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<html-el:form action="/IdeaProyectoEvaluarPorJuntaGuardar">

<html-el:hidden property="idProyecto"/>	
<table class="formulario">
	<!--  Resultado -->
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fechaEvaluacion"/>
		</td>		
		<td align="left">
			<pragmatags:calendar propertyName="fechaEvaluacion" top="255" left="250" />
			<pragmatags:error property="fechaEvaluacion" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.fundamentacion"/>
		</td>		
		<td align="left">
	   		<html-el:textarea property="fundamentacion" rows="4" cols="100" />
			<pragmatags:error property="fundamentacion" />
		</td>
	</tr>		
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.proyectoElegible"/>
		</td>		
		<td align="left">
			<c:forEach items="${ideaProyecto.opcionesDeEvaluacion}" var="opcion">
				<html-el:radio property="aceptaProyecto" value="${opcion.name}"> 
					<bean-el:message bundle="codes" key="${opcion.key}"/>
				</html-el:radio>				
			</c:forEach>
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.recomendacionInstrumento"/>
		</td>		
		<td align="left">
			<html-el:textarea property="recomendacion" rows="4" cols="100" />	
			<pragmatags:error property="recomendacion" />
		</td>
	</tr>		
</table>
</html-el:form>

