<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<body onload="switchLayerLocalizacion('<c:out value="localizacion"/>','1');">
<html-el:form action="/ProyectoGuardarEdicion">

<input type="hidden" id="selectionEvent" name="selectionEvent"/>

<html:hidden property="id"/>
<html:hidden property="idPresentacion"/>
<html:hidden property="tipoProyecto"/>
<html:hidden property="accion"/>

<table class="formulario">		

	<!------------General------------>
	<tr>		
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.general" />
		</th>	
	</tr>
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.nroProyecto"/>
		</td>
		
		<td>
			<html:text property="codigo" maxlength="20"/>
			<pragmatags:error property="codigo" />
		</td>
	</tr>

	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.emerix"/>
		</td>
		
		<td>
			<html:text property="emerix" maxlength="20"/>
			<pragmatags:error property="emerix" />
		</td>
	</tr>

	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.entidadBeneficiaria"/>
		</td>		
		<!-- TODO: SS-es modificable solo si esta agregando uno nuevo -->
		<td>
			<c:choose> 
				<c:when test="${accion == 'altaProyecto'}"> 
					<pragmatags:selector entidad="EntidadBeneficiaria"/>					
				</c:when> 
		        <c:otherwise> 
					<html:hidden property="idEntidadBeneficiaria"/>
					<html-el:text property="txtEntidadBeneficiaria" maxlength="50" readonly="true"/>
				</c:otherwise> 
			</c:choose> 			
			<pragmatags:error property="idEntidadBeneficiaria" />
		</td>
	</tr>
	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.titulo"/></td>
		<td>
			<html:textarea property="titulo" rows="3" cols="100"/>	
			<pragmatags:error property="titulo" />
		</td>
	</tr>
	<%--
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.proyectoPitecRelacionado"/>
		</td>
		
		<td>
			<pragmatags:selector entidad="ProyectoPitec" />
		</td>
	</tr>
	--%>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.proyectoPitecRelacionado"/>
		</td>		
		<td>
	   		<html:text property="proyectoPitec" maxlength="20"/>	
			<pragmatags:error property="proyectoPitec" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.objetivo"/>
		</td>
		<td>
			<html:textarea property="objetivo" rows="3" cols="100"/>	
			<pragmatags:error property="objetivo" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.responsableLegal"/>
		</td>		
		<td>
			<pragmatags:selectorInventario entidad="PersonaLegal"/>
		</td>
	</tr>
	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.directorProyecto"/>
		</td>		
		<td>
			<pragmatags:selectorInventario entidad="PersonaDirector"/>
		</td>
	</tr>
	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.representante"/>
		</td>		
		<td>
			<pragmatags:selectorInventario entidad="PersonaRepresentante"/>
		</td>
	</tr>	
	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.ciiu"/>
		</td>		
		<td>
			<pragmatags:selectorInventario width="75" entidad="Ciiu"/>
		</td>
	</tr>
		
	<tr>
		<td class="opcional"><bean:message bundle="labels" key="app.label.resumen"/></td>		
		<td>
	   		<html:textarea property="resumen" rows="4" cols="100"/>	
			<pragmatags:error property="resumen"/>
		</td>
	</tr>	

	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.palabrasClave"/>
		</td>		
		<td>
	   		<html:textarea property="palabraClave" rows="3" cols="100" />	
			<pragmatags:error property="palabraClave"/>
		</td>
	</tr>
	
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.duracionMeses"/>
		</td>		
		<td>
	   		<html:text property="duracion" maxlength="30" />	
			<pragmatags:error property="duracion" />
		</td>	
	</tr>
	
	<tr>
		<td class="obligatorio" valign="top">
			<bean:message bundle="labels" key="app.label.localizacionProyecto"/>
		</td>		
		<td colspan="1">

			<pragmatags:localizacion id="localizacion"/>
		</td>	
	</tr>
		
	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.tipoProyecto"/>
		</td>		
		<td>
			<html:select property="idTipoProyecto">
				<html:options collection="tiposProyectos" property="value" labelProperty="label"/>
			</html:select>
			<pragmatags:error property="idTipoProyecto" />
		</td>
	</tr>		

	<tr>
		<td class="opcional" valign="top">
			<bean:message bundle="labels" key="app.label.empleoPermanente"/>
		</td>		
		<td>
			<pragmatags:empleoPermanente id="empleo"/>
		</td>	
	</tr>
	<%-- Recomendacon --%>
	<c:if test="${MOSTRAR_NO_PUEDE_EDITAR_RECOMENDACION}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.recomendacion"/>
			</td>		
			<td class="opcional">
				<bean:message key="app.msj.datoCripto" bundle="informationals"/>
			</td>
		</tr>
	</c:if>
	<c:if test="${MOSTRAR_COMBO_RECOMENDACION}">
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.recomendacion"/>
			</td>		
			<td>
				<html:select property="recomendacion">
					<html:options collection="recomendaciones" property="value" labelProperty="label"/>
				</html:select>
				<pragmatags:error property="recomendacion" />
			</td>
		</tr>
	</c:if>
	<%-- Fin Recomendacon --%>

	<!------------Datos Económicos------------>

	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>		
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.datosEconomicos" />
		</th>	
	</tr>
	
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.tir"/>
		</td>
		<td>
	   		<html:text property="tir" maxlength="6" style="text-align:right"/>	
			<pragmatags:error property="tir" />
		</td>
	</tr>
	<tr>
		<td class="opcional">
			<bean:message bundle="labels" key="app.label.van"/>
		</td>
		<td>
	   		<html:text property="van" maxlength="10" style="text-align:right"/>	
			<pragmatags:error property="van" />
		</td>
	</tr>
	
	<!------------Entidad Bancaria------------>
	<c:if test="${permiteFinanciamientoBancario}">
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr>		
			<th colspan="2" class="titulo">
				<bean:message bundle="headers" key="app.header.entidadBancaria" />
			</th>	
		</tr>
		<tr>
			<td class="obligatorio">
				<bean:message bundle="labels" key="app.label.nombre"/>
			</td>
			
			<td>
				<html:select property="idEntidadBancaria">
					<html:options collection="entidadesBancarias" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.description"/>
			</td>
			
			<td>
		   		<html:text property="descripcionEntidadBancaria" maxlength="30" />	
			</td>
		</tr>
		<tr>
			<td class="opcional">
				<bean:message bundle="labels" key="app.label.tasaInteres"/>
			</td>
			<td>
		   		<html:text property="porcentajeTasaInteres" maxlength="6" style="text-align:right"/>	
		   		<pragmatags:error property="porcentajeTasaInteres" />
			</td>
		</tr>
	</c:if>
	
	
	<!------------Entidades Intervinientes------------>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<th colspan="2" class="titulo">
			<bean:message bundle="labels" key="app.label.entidadesIntervinientes" />
		</th>
	</tr>
	<tr>
		<td colspan="2">
			<pragmatags:btnAgregar javascript="popUpEntidadIntervinientes('tablaEntidad');"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<table id="tablaEntidad" border="0" width="100%" class="inventario">
			<tr>
				<th><bean:message bundle="labels" key="app.label.entidad"/></th>
				<th><bean:message bundle="labels" key="app.label.tipoEntidad" /></th>
				<th><bean:message bundle="labels" key="app.label.funcion" /></th>
				<th><bean:message bundle="labels" key="app.label.relacion"/></th>
				<th>Acciones</th>
 			 </tr>
 			 <c:if test="${!empty intervinientes}">
			<c:forEach items="${intervinientes}" var="intervinientes">
			<tr>
				<input id="idEntidad" name="idEntidad" value="${intervinientes[4]}" type="hidden"/>
				<input id="tipoEntidad" name="tipoEntidad" value="${intervinientes[5]}" type="hidden"/>
				<input id="relacion" name="relacion" value="${intervinientes[3]}" type="hidden"/>										
				<input id="funcion" name="funcion" value="${intervinientes[2]}" type="hidden"/>										
				<td><c:out value="${intervinientes[0]}"/></td>
				<td><c:out value="${intervinientes[1]}"/></td>
				<td><c:out value="${intervinientes[3]}"/></td>
				<td><c:out value="${intervinientes[2]}"/></td>
				<td><button name="Delete" onclick="deleteRow(this , 'tablaEntidad')"><img src="images/eliminar.gif"/></button></td>					
			</tr>
			</c:forEach>
			</c:if>
		</table>			
		
		</td>
	</tr>

	
	<!------------Adjuntos------------
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>		
		<th>
			<bean:message bundle="headers" key="app.header.adjuntos" />
		</th>	
		<th>
		    <html:image page="/images/agregar.gif" alt="Agregar Archivo Adjunto" border="0"/>
		</th>	
	</tr>
	
	<tr>
		<td>
			TODOS
		</td>
	</tr>
	
	------------Observaciones------------>
	
	<!------------Observaciones------------>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>	
	<tr>	
		<th colspan="2" class="titulo">
			<bean:message bundle="headers" key="app.header.observaciones"/>
		</th>		
	</tr>
	<tr>
		<td colspan="2">
			<html:textarea property="observacion" rows="4" cols="100" />
			<pragmatags:error property="observacion" />
		</td>
	</tr>
</table>
</html-el:form>
</body>