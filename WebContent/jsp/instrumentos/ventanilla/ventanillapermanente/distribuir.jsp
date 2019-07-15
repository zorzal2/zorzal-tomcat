<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 


	<title>
		<bean:message bundle="titles" key="app.title.ventanillaDistribucion"/>
	</title>

	<script type="text/javascript" src="js/libreria.js"></script>
	
	<script language="javascript">
		function agregarFila(idCampo, deCampo){
			var i=0;
			var x=0;	
			var oCell;
			var oRow;
			var oTable = document.all.tblOrdenar;
						
			//Antes de agregar verificar que el campo este ingresado previamente
			for (i=0; i < oTable.rows.length; i++) {
				for (j=0; j < oTable.rows(i).cells.length; j++) {
					if (oTable.rows(i).cells(j).innerText == deCampo){
						alert("La columna ya se encuentra ingresada en la lista.")
						return 0
					}
				}
			}
			
			oRow = oTable.insertRow(-1);
			oRow.height = "20";
			oRow.className ="TRData2";
									
			oCell = oRow.insertCell(-1);
			oCell.innerText = deCampo;						

			oCell = oRow.insertCell(-1);
			oCell.innerHTML = "<input type='text' width='13' name='monto' value=''>";
			
			oCell = oRow.insertCell(-1);
			oCell.innerHTML = "<input type='text' width='13' name='limite' value=''>";

			oCell = oRow.insertCell(-1);
			oCell.innerHTML = "<input type='text' width='13' name='plazoEjecucion' value=''>";
			
			oCell = oRow.insertCell(-1);
			oCell.innerHTML = "<img src='images/btnEliminar.gif' Title='Eliminar' onclick='borrarFila();' border=0 hspace=0 onmouseOver=javascript:this.style.cursor='hand'>";
		}
		
		function borrarFila(){
			var curElement = event.srcElement;
			var tr= curElement.parentElement.parentElement;
         	document.all.tblOrdenar.deleteRow( tr.sectionRowIndex);
      	}


		function guardar()
		{
			/*
			var validate = validateVentanillaPermanenteEditarDynaForm(document.VentanillaPermanenteEditarDynaForm);
			if (validate) {
				document.VentanillaPermanenteEditarDynaForm.submit();
			}
			*/
			window.close();
		}
		
	    function validateForm(frmMostrar){
			var oTable = document.all.tblOrdenar;
			var cantRows = oTable.rows.length;
			
			if (cantRows >= 1){
				guardar();
			}
			else {
				alert("Debe agregar por lo menos una columna.");
			}

		}
		
	</script>

<form name="frmMostrar">
	<table class="formulario">	
		<tr>
			<th colspan="5" class="titulo">
				<bean:message bundle="headers" key="app.header.ventanillaDistribucion" />
			</th>
		</tr>
		<tr>
			<td>
				<select name="cboTipoProyecto" ID="combo">
					<option value="1">
						Opción 1
					</option>
					<option value="2">
						Opción 2
					</option>
				</select>
	
			 	<button onclick="javascript:agregarFila(document.frmMostrar.cboTipoProyecto.value,document.frmMostrar.cboTipoProyecto.options[document.frmMostrar.cboTipoProyecto.selectedIndex].text);">
			 		Agregar
			 	</button>
			</td>
		</tr>	
		<tr>
			<th><bean:message bundle="labels" key="app.label.tipoProyecto" /></th>
			<th><bean:message bundle="labels" key="app.label.monto" /></th>
			<th><bean:message bundle="labels" key="app.label.limite" /></th>
			<th><bean:message bundle="labels" key="app.label.plazoEjecucionMeses" /></th>
			<th><bean:message bundle="labels" key="app.label.acciones" /></th>
		</tr>
	
	    <tbody name=tblOrdenar id=tblOrdenar>
		</tbody>
	
		<tr>
			<th><bean:message bundle="labels" key="app.label.montoAcumulado" /></th>
			<th></th>	<th></th> 	<th></th>	<th></th>		
		</tr>
	</table>
	
	<table>
		<tr align="right">
			<td>
				<html:link href="javascript:validateForm(document.frmMostrar)">
					<bean:message bundle="labels" key="app.label.guardar" />
				</html:link>
				&nbsp;&nbsp;&nbsp;
				<a href="javascript:window.close()">
					<bean:message bundle="labels" key="app.label.cancelar"/>
				</a>
			</td>
		</tr>
	</table>
</form>
