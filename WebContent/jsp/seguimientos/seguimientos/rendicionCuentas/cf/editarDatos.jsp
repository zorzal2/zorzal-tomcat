<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<tr>		
	<td class="obligatorio">
		<bean:message bundle="labels" key="app.label.nroFactura"/>
	</td>
	<td colspan="5">
		<html:text property="nroFactura" maxlength="14"/>
		<pragmatags:error property="nroFactura" />
	</td>
</tr>
<tr>
	<td class="opcional">
		<bean:message bundle="labels" key="app.label.nroRecibo"/>
	</td>
	<td colspan="5">
		<html:textarea property="nroRecibo" rows="2" cols="100"/>
		<pragmatags:error property="nroRecibo" />
	</td>
</tr>
<c:if test="${!rubro.esPersona and !rubro.esCanonInstitucional}">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.descripcion"/>
		</td>
		<td colspan="5">
			<html:text size="50" property="descripcion" maxlength="100" />
			<pragmatags:error property="descripcion" />
		</td>
	</tr>
</c:if>
<c:if test="${rubro.esGeneral or rubro.esConsultor}">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.proveedor"/>
		</td>
		<td colspan="5">
			<html:text property="proveedor" maxlength="60" />
			<pragmatags:error property="proveedor" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.tieneCertificadoProveedor"/>
		</td>
		<td colspan="5">
			<html:radio property="tieneCertificadoProveedor" value="true">
				<bean:message bundle="labels" key="app.label.si" />
			</html:radio> 
			<html:radio property="tieneCertificadoProveedor" value="false">
				<bean:message bundle="labels" key="app.label.no" />
			</html:radio>
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.paisProveedor"/>
		</td>
		<td colspan="5">
			<html:text property="paisProveedor" maxlength="60" />
		</td>
	</tr>
</c:if>
<c:if test="${rubro.esPersona}">
	<tr>		
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.persona"/>
		</td>
		<td colspan="5">
			<pragmatags:selectorInventario entidad="PersonaRendicion"/>
		</td>
	</tr>
	<c:if test="${!rubro.esDirectorExperto}">
		<tr>		
		<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.profesion"/>
			</td>
			<td colspan="5">
				<html:text size="50" property="profesion" maxlength="100" />
				<pragmatags:error property="profesion" />
			</td>
		</tr>
	</c:if>
</c:if>
<c:if test="${rubro.esRecursoHumanoPropio or rubro.esRecursoHumanoAdicional or rubro.esConsejeroTecnologico}">
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.funcion"/>
		</td>
		<td colspan="5">
			<html:text size="50" property="funcion" maxlength="100" />
			<pragmatags:error property="funcion" />
		</td>
	</tr>
</c:if>
<c:if test="${rubro.esRecursoHumanoPropio}">
	<tr>		
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.sueldoMensual"/>
		</td>
		<td colspan="5">
			<html:text property="sueldoMensual" maxlength="60" style="text-align:right"/>
			<pragmatags:error property="sueldoMensual" />
		</td>
	</tr>
</c:if>
<c:if test="${rubro.esPersona}">
	<tr>		
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.porcentajeDedicacion"/>
		</td>
		<td colspan="5">
			<html:text property="porcentajeDedicacion" maxlength="60" style="text-align:right"/>
			<pragmatags:error property="porcentajeDedicacion" />
		</td>
	</tr>			
</c:if>	
<c:if test="${rubro.esPersona or rubro.esConsultor}">
	<tr>		
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.mesesParticipacion"/>
		</td>
		<td colspan="5">
			<html:text property="mesesParticipacion" maxlength="60" style="text-align:right"/>
			<pragmatags:error property="mesesParticipacion" />
		</td>
	</tr>	
</c:if>
<c:if test="${rubro.esRecursoHumanoAdicional or rubro.esConsultor or rubro.esDirectorExperto or rubro.esConsejeroTecnologico}">	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.costoTotalMensual"/>
		</td>
		<td colspan="5">
			<html:text property="costoTotalMensual" maxlength="60" style="text-align:right"/>
			<pragmatags:error property="costoTotalMensual" />
		</td>
	</tr>
</c:if>
<tr>
	<td class="obligatorio">
		<bean:message bundle="labels" key="app.label.montoTotal"/>
	</td>
	<td colspan="5">
		<html:text property="montoTotal" maxlength="60" style="text-align:right"/>
		<pragmatags:error property="montoTotal" />
	</td>
</tr>	