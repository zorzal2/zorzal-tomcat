<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/displaytag-el-12" prefix="display-el" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-logic" prefix="logic" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<html>
<head>
	<title>Seleccionar Columnas</title>
	<LINK href="../../css/estilo.css" type=text/css rel=stylesheet>
	<script type="text/javascript" src="../../js/libreria.js"></script>
	<script language="javascript">	
		function ocultarColumnas(cols) {
			var myLocation = window.opener.location.href;
			myLocation = setParamValue("mycolumns", cols, myLocation);
			window.close();
		}
		
		function selectAll(boolean) {
			var i;
			var formOptions = document.form1.elements.opcion;
			var cantOptions = formOptions.length;

			for(i=0; i < cantOptions; i++) {
				type = formOptions[i].type.toLowerCase();
				name = formOptions[i].name.toLowerCase();

				if (type == "checkbox") {
					formOptions[i].checked = boolean;
				}
			}
		}

		function updateAllBox(boolean) {
			if (!boolean) { 
				document.form1.elements.all.checked = false; 
			}
		}	
		
		function validateForm(aForm) {
				var i;
				var formOptions = aForm.elements.opcion;
				var cantOptions = formOptions.length;	
				var bChecked = false;
				
				var cols="";
				
				var valid = false;
				
				for(i=0; i < cantOptions; i++){
					type = formOptions[i].type.toLowerCase();
					name = formOptions[i].name.toLowerCase();
					id = formOptions[i].id.toLowerCase();
					
					if (type == 'checkbox' && formOptions[i].checked){
							if (cols != "") {
								cols += "%2c";
							}													
							cols += formOptions[i].value;
							valid = true;
					}										
				}

				if (!valid) 
					alert("Debe seleccionar por lo menos una columna.\n");
				else {
					ocultarColumnas(cols);
					document.form1.submit();
				}
				
				return valid;
			}
	</script>		   
</head>

<body>
	<form name="form1" method="post">
		<table class="formulario" id="tblColumnas">
			<th> Visualizar	</th>			
			<th> Columna	</th>
			
			<c:forEach items="${param.columns}" var="nombreColumna">
				<tr>
					<td>
						<input type='checkbox' name="opcion" onclick="updateAllBox(this.checked)" value='<c:out value="${nombreColumna}"/>'/>
					</td>
					<td>						
						<fmt:bundle basename="resources.FieldLabels">
							<fmt:message key="app.label.${nombreColumna}"/>
						</fmt:bundle>
					</td>
				</tr>
			</c:forEach>
			
			<th colspan="2">
				<input type="checkbox" name="all" onclick="selectAll(this.checked);"/>					
			</th>

			<tr>
				<td align="right" colspan="2">
					<a href="#" onclick="javascript:validateForm(document.form1);">
						Aceptar
					</a>
					&nbsp;
					<a href="#" onClick="window.close();">Cancelar</a>
				</td>
			</tr>	
		</table>
	</form>	
</body>





