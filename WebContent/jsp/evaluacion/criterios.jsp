<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<script type="text/javascript">
	function setId() {
	  var criterioElegido = document.getElementsByName('criterioElegido');
	  var ids = document.getElementsByName('idItem');
	  var elegido = document.getElementsByName('elegido');
		for (var i=0; i< criterioElegido.length; i++) {
			if (criterioElegido[i].value == "true") {
				elegido[i].value = ids[i].value;
			}
		}
		submitForm();
	}
	
	function fillCheckboxes() {
	  var criterioElegido = document.getElementsByName('criterioElegido');
	  var elegido = document.getElementsByName('ids');
	  var ids = document.getElementsByName('idItem');
	  var bla = elegido[0].value;
	  var id;
	  while (bla.length > 0) {
		  id = bla.substring(0,bla.indexOf(','));
		  bla = bla.substring(bla.indexOf(',')+1,bla.length);
		  for (var i=0; i< ids.length; i++) {
//			  alert(ids[i].value);
//			  alert(id);
//			  alert(ids[i].value == id);
	  		  if (ids[i].value == id) {
//	  		  	  alert("si");
	  	  		  criterioElegido[i].checked = "true";
		  	  	  criterioElegido[i].value = "true";
		  	  } 
		  }	  
	  }
	}
	
	function changeValue(elemento, isChecked) {
		if (isChecked) {
			elemento.value = true;
		}
		else {
			elemento.value = false;
		}
	
	}

</script>

	<!-- Titulo -->

<body onload="checkVariableClose('windowClose');fillCheckboxes();">

<h3>
	<bean:message bundle="titles" key="app.title.evaluacionesCriterio"/>
</h3>

<html:form action="/CargarCriteriosResultadoGuardar">
	<html:hidden property="windowClose" />
	<html:hidden property="idEvaluacion"/>	
	<html:hidden property="ids"/>	
	<input id="nombreCriterio" name="nombreCriterio" value="${nombreCriterio}" type="hidden"/>
		
		<table width="80%" id="criterioTable" class="inventario" align="center" >
			<tr>
				<th width="80%">
					<bean:message bundle="headers" key="app.header.criterio-categoria" />
				</th>
				<th width="10%">
					<bean:message bundle="headers" key="app.header.puntaje" />
				</th>
				<th class="accionesInventario" width="10%">
					<bean:message bundle="headers" key="app.header.acciones" />
				</th>
			</tr>
			<logic:present name="criterioList" scope="request"> 		
				<logic:iterate name="criterioList" id="criterios" indexId="index">
					<c:set var="claseFila" value="filaCriterio" />
					<c:set var="claseTd" value="obligatorio" />
					
					<c:if test="${criterios.tipoItem == 'categoria'}">
						<c:set var="claseFila" value="filaCategoria" />
						<c:set var="claseTd" value="" />
					</c:if>
					
					
				   	<tr class='<c:out value="${claseFila}" />'>
				      	<td class='<c:out value="${claseTd}" />'><c:out value="${criterios.criterio}"/></td> 
				      	<td>
							<c:out value="${criterios.puntaje}"/>				    
					  	</td>
				      	<td>
							<input id="idItem" name="idItem" value="${criterios.idItem}" type="hidden"/>
							<input id="tipoItem" name="tipoItem" value="${criterios.tipoItem}" type="hidden"/>
							<input id="criterio" name="criterio" value="${criterios.criterio}" type="hidden"/>
							<input id="elegido" name="elegido" type="hidden"/>

				      		<c:if test="${criterios.tipoItem == 'criterio'}">
								<input id="criterioElegido" type="hidden" name="criterioElegido" value="true" />
				      		</c:if>
				      		<c:if test="${criterios.tipoItem == 'categoria'}">
								<input id="criterioElegido" type="checkbox" name="criterioElegido" value="false" onclick="changeValue(this,this.checked);"/>
				      		</c:if>
				      	</td>			      				      				      	
					</tr>
				</logic:iterate>
		   	</logic:present>
	   </table>	 
			
		<pragmatags:btnOkCancel okJavascript="setId()" cancelJavascript="cerrarPopUp()"/>
	</html:form>
</body>



