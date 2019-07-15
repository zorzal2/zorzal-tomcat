<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/displaytag-el" prefix="display" %>
<%@ taglib uri="/tags/pragma" prefix="pragma" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 


<html>
	<head>
		<script language="javascript">

		function addCriterionOption(criterionTxt) {
			var comboCriterios = document.getElementById("idCriterioSelect");
			var cantOpciones = comboCriterios.options.length;
			comboCriterios.options[cantOpciones]=new Option(criterionTxt, criterionTxt, false, false);
		}		

		function removeCriterionOption(criterionTxt) {
			var comboCriterios = document.getElementById("idCriterioSelect");
			
			for (i=0; i<comboCriterios.options.length; i++){
				if (comboCriterios.options[i].value == criterionTxt) {
					comboCriterios.options[i] = null;
				}
			}
		}

		function myTrim(str) {
			return str.replace(/^s+|s+$/g, "");
		}
	
		/**
		*	Esta funcion recorre la lista de categorías hasta encontrar la
		*   que se pasa por parámetro, e inserta una fila para categoría 
		*	al final de la misma
		*/
		function insertarFilaCategoria(targetTable, txtCriterio) {
			var rows = targetTable.getElementsByTagName("tr");
			var next = 0;
			txtCriterio = myTrim(txtCriterio);
			var i = 0;
			
			for (i=0; i<rows.length; i++){
				filaActual = rows[i].childNodes[0].firstChild.nodeValue;				
								
				if (next == 1) {
					if (rows[i].className == 'filaCriterio') {
						return targetTable.insertRow(i);
					}
				}
				if (txtCriterio == filaActual) {
					next = 1;
				}
			}
			return targetTable.insertRow(i);
		}

		
				
		/**
		*	criterioCategoria = 'criterio' || 'categoria'
		*
		*/		
		function createRowCriterioCategoria(criterioCategoria) {
			
			var targetTable = 'criterioTable';
			var sourceForm = 'CriterioEditarDynaForm';
		
			var myTable = document.getElementById(targetTable);
			var rows = myTable.getElementsByTagName("tr").length;
			
			// Agrego la fila a la tabla
			var myTr;

			// Creo la celda "puntaje"
			var myTdPuntaje = createCell();
			
			// var identificador = ''; 
			var fieldName = '';
			var cellValue = '';
			var cellText  = '';
			
			// Se está agregando un Criterio
			if (criterioCategoria == 'criterio')  {	
				fieldName = document.getElementById('txtCriterio');
				cellText = fieldName.value;
				
				// Si es vacio no lo agrego
				if (cellText=="") { 				
				    return;
				}
			
				if (existRowCriterio()) {
					alert('Ya existe un item con este Criterio');
					return;
				}
							
				// Agrego el nuevo criterio al combo
				addCriterionOption(cellText);
				
				// Agrego un atributo para distinguir filas de "criterios" de filas de "categorías"
				myTr = myTable.insertRow(rows);
				myTr.className = 'filaCriterio';

				// nuevo
				var inputPuntaje = createHidden('puntaje','0');
				
			}			
			
			// Se está agregando una Categoría 
			else 									
			{									
				var comboCriterios = document.getElementsByName('idCriterioSelect')[0];
				var comboSelectedIndex = comboCriterios.selectedIndex;
				
				// Levanto el nombre de la categoría a ingresar
				var fieldName = document.getElementById("txtCategoria");
				if (fieldName.value.length > 500) {
					alert('Categoria no puede tener mas de 500 caracteres');
					return;
				}
				var cellText  = fieldName.value;
				var cellValue = fieldName.value;
				

				// Chequeo que se haya seleccionado algún criterio
				if ((comboSelectedIndex != 0) && (cellText != "")) {
					
					var inputPuntaje = createText('puntaje','');
					inputPuntaje.style.textAlign = 'right';
					inputPuntaje.maxLength= 10;
					inputPuntaje.value = ''; 						//valor default
					// myTdPuntaje.appendChild(inputPuntaje);
	
					// var txtCriterio    = comboCriterios[comboSelectedIndex].text;
					var txtCriterio    = comboCriterios[comboSelectedIndex].value;
								
					myTr = insertarFilaCategoria(myTable, txtCriterio);

					// Agrego el atributo para distinguir filas de "criterios" de filas de "categorías"
					myTr.className = 'filaCategoria';
				}
				else {
					return;
				}
			}						
			
			myTdPuntaje.appendChild(inputPuntaje);
			
			var myTdButtonDelete = createTdButton(targetTable, criterioCategoria);
		
			// Criterio-Categoría
			var myCell = createCellHiddenText('idCriterio',cellValue,cellText);

			if (criterioCategoria == 'criterio') {
				myCell.className = "obligatorio";
			}

			// Voy armando el request para cuando se haga submit del form
			myCell.appendChild(createHidden('idItem',''));
			myCell.appendChild(createHidden('tipoItem',criterioCategoria));	 
			myCell.appendChild(createHidden('criterio',cellText));
			
			myCell.width="150";
			myTr.appendChild(myCell);
						
			// Puntaje		
			myTr.appendChild(myTdPuntaje);
		
			myTr.appendChild(myTdButtonDelete);

		}

		/**
		*	Elimina una fila de la lista.
		*	criterioCategoria = 'criterio' || 'categoria' 
		*/
		function createTdButton(targetTable, criterioCategoria) {	
			//create new cellValue
			var myTd = document.createElement("td");
			//get the created delete-button from the opener
			var myButton = document.createElement("button");
			myButton.onclick = new Function("deleteRowCriterioCategoria(this, '"+targetTable+"', '"+criterioCategoria+"');");
			
			myButton.name = "Delete";
			var myImage = document.createElement("img");
			myImage.src = "images/eliminar.gif";
			myButton.appendChild(myImage);
			myTd.appendChild(myButton);	
			return myTd;
		}

	   function existRowCriterio() {
	   		var existe = false;
	   		
		  	var fieldName = document.getElementById('txtCriterio');
		  	var cellValue = fieldName.value;
				
		    var myForm = document.forms[0];
			for (var i = 0; i < myForm.length; ++i) {
				if (myForm[i].type == 'hidden' && myForm[i].value == cellValue) { 
					existe = true;
			    }
		    }	    
	   		
	   		return existe;
	   } 	

		/**
		 *	field: botón de "eliminar"
		 *
		*/
		function deleteRowCriterioCategoria(field, targetTable, criterioCategoria) {
			if (criterioCategoria=='criterio') {							
				var o = field.parentNode;
				var r = o.parentNode;				
			
				// Chequeo que el criterio a borrar no tenga categorías asignadas
				if (r.nextSibling != null && r.nextSibling.className == 'filaCategoria') {
					alert("Tiene categorías asignadas");
					return;
				}
				else {				
					// TODO: ver si se puede reemplazar el index=0 por un atributo para acceder a la columna.
					var txtCriterio = r.childNodes[0].firstChild.nodeValue;
					removeCriterionOption(txtCriterio);
					deleteRow(field, targetTable);
				}				
			}
			else {
				deleteRow(field, targetTable);
			}
		}
			
		</script>
	</head>

	<h3>
		<pragmatags:titulo-edicion clase="Criterio" />
	</h3>
	
	<html:form action="/MatrizCriteriosGuardar">
	
		<html:hidden property="id" value="${param.id}"/>
		
		<table border="1" width="100%">	
			<tr>
				<td class="obligatorio" width="15%">
					<bean:message bundle="labels" key="app.label.denominacion"/>
				</td>
				<td class="obligatorio" width="85%">
					<html:text property="denominacion" size="30" maxlength="30"/>
					<pragmatags:error property="denominacion" />
				</td>
			</tr>		
			<c:if test="${!empty param.id}">
				<tr>
					<td class="obligatorio">
						<bean:message bundle="labels" key="app.label.estado"/>
					</td>		
					<td class="opcional">
						<html:select property="activo">
							<html:options collection="estados" property="value" labelProperty="label" />
						</html:select> 
						<pragmatags:error property="activo" />
					</td>
				</tr>
			</c:if>
		</table>
		
		<br>
		
		<table border="1" class="formulario">
			<tr>
				<td valign="top" width="15%">Nuevo&nbsp;Criterio</td>
				<td>
					<input type="text" id="txtCriterio" size="120" maxlength="120"/>
					&nbsp;<button id="btnAgregarCriterio" onClick="createRowCriterioCategoria('criterio');">
						Agregar
					</button>
				</td>
			</tr>
			<tr>
				<td valign="top">Nueva&nbsp;Categoría</td>
				<td>
					<html:textarea property="txtCategoria" rows="3" cols="120"/>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>en criterio&nbsp; 
					<select id="idCriterioSelect">
						<option value="0">-- seleccione --</option>
						
						<logic:present name="criterioList" scope="request">
							<logic:iterate name="criterioList" id="criterio" indexId="index">
								<c:if test="${criterio.tipoItem == 'criterio'}">
									<option value="<c:out value="${criterio.criterio}" />">
										<c:out value="${criterio.criterio}" />
									</option>
								</c:if>
							</logic:iterate>
						</logic:present>
					</select>
					&nbsp;
					<button id="btnAgregarCategoria" onClick="createRowCriterioCategoria('categoria');">
						Agregar
					</button>
				</td>
			</tr>
		</table>

		<br>
		
		<!-- Tabla con los criterios -->
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
		
			<%--  Si está presente el atributo criterioList, entonces se ejecuta el contenido del tag logic --%>			
			<logic:present name="criterioList" scope="request"> 		
				<logic:iterate name="criterioList" id="criterio" indexId="index">
					<c:set var="claseFila" value="filaCriterio" />
					<c:set var="claseTd" value="obligatorio" />
					
					<c:if test="${criterio.tipoItem == 'categoria'}">
						<c:set var="claseFila" value="filaCategoria" />
						<c:set var="claseTd" value="" />
					</c:if>
					
				   	<tr class='<c:out value="${claseFila}" />'>
				      	<td class='<c:out value="${claseTd}" />'><c:out value="${criterio.criterio}"/></td> 
				      	<td>
     						<c:choose>
     							<c:when test="${criterio.tipoItem == 'categoria'}">
	     							<html:text name="criterio" property="puntaje" maxlength="10" style="text-align:right"/>
     							</c:when>
     							<c:otherwise>
	     							<html:hidden name="criterio" property="puntaje" />
     							</c:otherwise>
     						</c:choose>
				      	</td>
				      	<td>
				      		<html:hidden name="criterio" property="idItem" />
							<html:hidden name="criterio" property="tipoItem" />
				      		<html:hidden name="criterio" property="criterio" />

				      		<c:if test="${criterio.tipoItem == 'criterio'}">
						      	<button onclick="deleteRowCriterioCategoria(this,'criterioTable','criterio');" >
				      		</c:if>
				      		<c:if test="${criterio.tipoItem == 'categoria'}">
						      	<button onclick="deleteRowCriterioCategoria(this,'criterioTable','categoria');" >
				      		</c:if>
					      		<img src='images/eliminar.gif' border='0' />
					      	</button>
				      	</td>			      				      				      	
					</tr>
				</logic:iterate>
		   	</logic:present>
		   	
	   </table>	 
	   <pragmatags:btnOkCancel okJavascript="submitForm()" cancelAction="/MatrizCriteriosInventario"/>
	</html:form>
</html>