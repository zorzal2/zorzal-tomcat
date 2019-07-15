<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

	<H1>
		<bean:message bundle="titles" key="app.title.visualizarPersona" />
	</H1>

	<table class="formulario" >	
	<tr>
		<td class="opcional" style="width:20%;font-weigth:bold">
			<bean:message bundle="labels" key="app.label.nombre"/>
		</td>		
		<td>
			<bean:write name="persona" property="nombre"/>
		</td>
	</tr>
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.cuitCuil"/>
		</td>		
		<td>
			<bean:write name="persona" property="cuit"/>
		</td>
	</tr>
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.direccion"/>
		</td>		
		<td>
			<bean:write name="persona" property="localizacion.direccion"/>
		</td>
	</tr>
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.localidad"/>
		</td>		
		<td>
			<bean:write name="persona" property="localizacion.localidad"/>
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.codigoPostal"/>
		</td>
			
		<td>
			<bean:write name="persona" property="localizacion.codigoPostal"/>
		</td>
		
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.jurisdiccion"/>
		</td>
			
		<td>
			<bean:write name="persona" property="localizacion.nombreJurisdiccion"/>
		</td>
		
	</tr>
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.telefono"/>
		</td>		
		<td>
			<bean:write name="persona" property="localizacion.telefono"/>
		</td>
	</tr>
	<tr>	
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.fax"/>
		</td>		
		<td>
			<bean:write name="persona" property="localizacion.fax"/>
		</td>
	</tr>
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.email"/>
		</td>		
		<td>
			<bean:write name="persona" property="localizacion.email"/>
		</td>
	</tr>
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.tituloGrado"/>
		</td>		
		<td>
			<bean:write name="persona" property="tituloGrado"/>
		</td>
	</tr>
    <tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.estado"/>
		</td>
		<td>
		<c:if test="${persona.activo =='true'}" >
			<bean:message bundle="codes" key="app.codes.general.estado.activo"/>
			</c:if>
			<c:if  test="${persona.activo =='false'}" >
			<bean:message bundle="codes" key="app.codes.general.estado.inactivo"/>
			</c:if>
		</td>	
	</tr>
			
	<tr>
		<td class="obligatorio" valign="top"" >
			<bean:message bundle="labels" key="app.label.esEvaluador"/>
		</td>		
		<td>
		   <c:if test="${persona.esEvaluador}">
			<bean:message bundle="codes" key="app.codes.valordeverdad.si"/>
			</c:if>
			<c:if test="${!persona.esEvaluador}">
			<bean:message bundle="codes" key="app.codes.valordeverdad.no"/>
			</c:if>
		</td>
	</tr>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>		    
	<c:if test="${not empty persona.evaluadorDTO}">
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.entDesemp"/>
		</td>		
		<td>
			<bean:write name="persona" property="evaluadorDTO.txtEntidadesDesemp"/>
		</td>
	</tr>
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.fechaIngreso"/>
		</td>		
		<td>
			<bean:write name="persona" property="evaluadorDTO.fechaIngreso"/>
		</td>
	</tr>
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.tituloPosgrado"/>
		</td>		
		<td>
			<bean:write name="persona" property="evaluadorDTO.tituloPosgrado"/>
		</td>
	</tr>
		
	<c:if test="${not empty especialidadEval}"> 
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.primaria"/>
		</td>		
		<td>
			<bean:write name="especialidadEval" property="nombre"/>
		</td>
	</tr>
   </c:if>	
   <c:if test="${not empty especialidadEvalSec}"> 
	<tr>
		<td class="opcional" >
			<bean:message bundle="labels" key="app.label.secundaria"/>
		</td>		
		<td>
			<bean:write name="especialidadEvalSec" property="nombre"/>
		</td>
	</tr>
	</c:if>	
	</c:if>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	</table>
	<pragmatags:btnCerrar/>	