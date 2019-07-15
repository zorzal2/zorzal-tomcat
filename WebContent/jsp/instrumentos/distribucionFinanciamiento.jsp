<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html-el" prefix="html-el" %> 

	<title>
		<bean:message bundle="titles" key="app.title.distribucionFinanciamiento"/>
	</title>

	<script>
	
	function validarMontoPorcentaje() {
		myForm = document.forms[0];
		var fieldType;
		var total = parseFloat(0);
		
		var nombre;
		var theleft;
		var valido = true;
		var variable = document.forms[0].asignacion.value == 'MONTO' ? 'monto' : 'porcentaje';
		for (var i = 0; i < myForm.length; ++i) {
			nombre = myForm[i].name;
			theleft = nombre.indexOf('].') + 2;
		    if (nombre.substring(theleft, nombre.length + 1) == variable) {
		        if (!isDouble(myForm[i].value)) {
		        	alert('Los ' + variable + 's ingresados deben ser numéricos');
		        	valido = false;
		        }
		        if (myForm[i].value < 0) {
					alert('Los ' + variable + 's ingresados deben ser positivos');
		        	valido = false;
		        }
		    }
	    }	 
		if (valido) {
			validarTotal();
		}
	}

	function validarTotal() {
		if (document.forms[0].asignacion.value == 'MONTO') {
			if (document.forms[0].montoTotal.value != obtenerTotal('monto')) {
				alert('El total de montos debe ser igual al Monto Total Instrumento');
			} else {
				requerimiento1(document.forms[0]);
//				submitForm();
			}
		} else {
			if (document.forms[0].porcentajeTotal.value != obtenerTotal('porcentaje')) {
				alert('El total de porcentajes debe ser igual al 100%');
			} else {
				requerimiento1(document.forms[0]);
//				submitForm();
			}
		}
	}
	function requerimiento1(formulario) {
		var url = formulario.action;
		var param = Form.serialize(formulario);

		var funcionRet = function() {
						window.close();
					  };
		var myAjax = new Ajax.Request(
			url, 
			{
				method: 'post', 
				parameters: param,
				onComplete: funcionRet
			});
	}

	function obtenerTotal(variable) {
		myForm = document.forms[0];
		var fieldType;
		var total = parseFloat(0);
		
		var nombre;
		var index;
		for (var i = 0; i < myForm.length; ++i) {
			nombre = myForm[i].name;
			index = nombre.indexOf('].') + 2;
		    if (nombre.substring(index, nombre.length + 1) == variable) {
		        if (isDouble(myForm[i].value)) {
		            total = total + parseFloat(myForm[i].value);
		        }
		    }
	    }	 
		if (total > 99.99 && total < 100.01) {
			total = 100.00;
		}
		return total;
	}			
	
	function actualizarPorcentaje(obj) { 
		var porcentajeTotal = document.forms[0].porcentajeTotal.value;
		var montoTotal = document.forms[0].montoTotal.value
		var monto = obj.value;
		var porcentaje = monto * porcentajeTotal / montoTotal;

		var nombre = obj.name;
		theright = nombre.indexOf('].') + 2;
		var nombreDestino= nombre.substring(0, theright) + 'porcentaje';	    
		
		myForm = document.forms[0];
		var fieldType;
		var total = parseFloat(0);
		
		for (var i = 0; i < myForm.length; ++i) {
		    if (myForm[i].name == nombreDestino) {
		    	myForm[i].value = porcentaje;
		    }
	    }				
	}		
	
	function actualizarMonto(obj) { 
		var porcentajeTotal = document.forms[0].porcentajeTotal.value;
		var montoTotal = document.forms[0].montoTotal.value
		var porcentaje = obj.value;
		var monto = porcentaje * montoTotal / porcentajeTotal;

		var nombre = obj.name;
		theright = nombre.indexOf('].') + 2;
		var nombreDestino= nombre.substring(0, theright) + 'monto';	    
		
		myForm = document.forms[0];
		var fieldType;
		var total = parseFloat(0);
		
		for (var i = 0; i < myForm.length; ++i) {
		    if (myForm[i].name == nombreDestino) {
		    	myForm[i].value = monto;
		    }
	    }				
	}				
		
	function actualizarTotal(variable,destino) {
		myForm = document.forms[0];
		var fieldType;
		var total = parseFloat(0);
		
		var nombre;
		var theleft;
		for (var i = 0; i < myForm.length; ++i) {
			nombre = myForm[i].name;
			theleft = nombre.indexOf('].') + 2;
		    if (nombre.substring(theleft, nombre.length + 1) == variable) {
		        if (isDouble(myForm[i].value)) {
		            total = total + parseFloat(myForm[i].value);
		        }
		    }
	     }	 
	
		var objDestino = document.getElementById(destino);
		objDestino.innerHTML = total;
	}		
		
	function actualizarTotales(asignacion) {
		myForm = document.forms[0];
		var fieldType;
		var total = parseFloat(0);
		
		var variable;
		var destino		
		if (asignacion.value == 'MONTO') {
			variable = 'monto';
			destino = 'divMontoTotal';
		} else {
			variable = 'porcentaje';
			destino = 'divPorcentajeTotal';
		}

		var nombre;
		var theleft;
		for (var i = 0; i < myForm.length; ++i) {
			nombre = myForm[i].name;
			theleft = nombre.indexOf('].') + 2;
		    if (nombre.substring(theleft, nombre.length + 1) == variable) {
		        if (isDouble(myForm[i].value)) {
		            total = total + parseFloat(myForm[i].value);
		        }
		    }
	    }
		if (total > 99.99 && total < 100.01) {
			total = 100.00;
		}
		var objDestino = document.getElementById(destino);
		objDestino.innerHTML = total;
	}	
		
	</script>
</head>

<body onload="actualizarTotales(document.forms[0].asignacion);">

<h3>
	<bean:message bundle="titles" key="app.title.distribucionFinanciamiento"/>
</h3>

<html:form action="/DistribucionFinanciamientoGuardar">

<html:hidden property="windowClose" />
<html:hidden property="tipo" />
<html:hidden property="asignacion" />
<html:hidden property="montoTotal" />
<html:hidden property="porcentajeTotal" value="100"/>

<table class="formulario">
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.montoTotalInstrumento"/>
			<bean:write name="DistribucionFinanciamientoForm" property="montoTotal"/>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message bundle="labels" key="app.label.tipoAsignamientoInformacion"/>
			<bean:write name="DistribucionFinanciamientoForm" property="asignacion"/>
		</td>
	</tr>	
</table>

<table class="inventario">
	<tr>
		<th>
			<bean:message bundle="headers" key="app.header.nombre"/>
		</th>
		
      	<c:choose>
      		<c:when test="${asignacion == 'MONTO'}">
				<th>
					<bean:message bundle="headers" key="app.header.monto$"/>
				</th>
			</c:when>			      	
      		<c:when test="${asignacion == 'PORCENTAJE'}">
				<th>
					<bean:message bundle="headers" key="app.header.porcentaje%"/>
				</th>				
			</c:when>			      	
		</c:choose>		
	</tr>
	
	<logic:present name="distribucionFinanciamientoList" scope="request"> 	
	   <logic:iterate name="distribucionFinanciamientoList" id="distribucionFinanciamiento" indexId="index">
	   	<tr>
	      	<td>
	      		<bean:write name="distribucionFinanciamiento" property="nombre"/>
	      		<html:hidden name="distribucionFinanciamiento" property="nombre" indexed="true"/>	      		
	      		<html:hidden name="distribucionFinanciamiento" property="idJurisdiccion" indexed="true"/>
	      		<html:hidden name="distribucionFinanciamiento" property="idRegion" indexed="true"/>	      		
	      		<html:hidden name="distribucionFinanciamiento" property="id" indexed="true"/>
	      	</td>
	      	<c:choose>
	      		<c:when test="${asignacion == 'MONTO'}">
					<td>
				      	<html:text name="distribucionFinanciamiento" property="monto" indexed="true" 
				      			   style="text-align:right" maxlength="15" onblur="javascript:actualizarTotal('monto','divMontoTotal');actualizarPorcentaje(this);"/>
				      	<html:hidden name="distribucionFinanciamiento" property="porcentaje" indexed="true" />
			      	</td>
				</c:when>			      	
	      		<c:when test="${asignacion == 'PORCENTAJE'}">
					<td>
				      	<html:text name="distribucionFinanciamiento" property="porcentaje" indexed="true"
				      			   style="text-align:right" maxlength="6" onblur="javascript:actualizarTotal('porcentaje','divPorcentajeTotal');actualizarMonto(this);"/>
				      	<html:hidden name="distribucionFinanciamiento" property="monto" indexed="true" />
			      	</td>
				</c:when>			      	
	      	</c:choose>
		</tr>
	   </logic:iterate>
    </logic:present>
   	<tr>
		<th>
			<bean:message bundle="headers" key="app.header.total"/>
		</th>
      	<c:choose>
      		<c:when test="${asignacion == 'MONTO'}">
				<th>
					<div id="divMontoTotal">
						<c:if test="${!empty divMontoTotal}"> 
							<c:out value='${divMontoTotal}'/>
						</c:if> 
					</div>
				</th>
			</c:when>			      	
      		<c:when test="${asignacion == 'PORCENTAJE'}">
				<th>
					<div id="divPorcentajeTotal" >
						<c:if test="${!empty divPorcentajeTotal}"> 
							<c:out value='${divPorcentajeTotal}'/>
						</c:if> 
					</div>
				</th>	
			</c:when>			      	
		</c:choose>				
	</tr>
</table>

<pragmatags:btnOkCancel okJavascript="validarMontoPorcentaje()" cancelJavascript="cerrarPopUp()"/>

</html:form>
</body>