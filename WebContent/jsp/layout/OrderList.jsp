<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<html>
<head runat="server">
	<title>Ordenar Lista</title>
	<LINK href="../../css/estilo.css" type=text/css rel=stylesheet>

	<script language="javascript">								
		function agregarFila(idCampo, deCampo, strOrden){
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
			if (strOrden == "ASC") {				
				oCell.innerHTML = "<input type=hidden name=idCampo value='"+idCampo+"'><SELECT name=cboOrden size=1><option value='ASC' selected>ASC</option><option value='DESC'>DESC</option></select>";
			}else{
				oCell.innerHTML = "<input type=hidden name=idCampo value='"+idCampo+"'><SELECT name=cboOrden size=1><option value='ASC'>ASC</option><option value='DESC' selected>DESC</option></select>";
			}
			
			oCell = oRow.insertCell(-1);
			oCell.innerHTML = "&nbsp;<img src='../../images/btnSubir.gif' Title='Subir' onclick='subirFila();' width=12 heigh=18 border=0 onmouseOver=javascript:this.style.cursor='hand'>&nbsp;"+
				"<img src='../../images/btnBajar.gif' Title='Bajar' onclick='bajarFila();' width=12 heigh=12 border=0 onmouseOver=javascript:this.style.cursor='hand'>&nbsp;"+
				"<img src='../../images/btnEliminar.gif' Title='Eliminar' onclick='borrarFila();' border=0 hspace=0 onmouseOver=javascript:this.style.cursor='hand'>";
		}
		
		function borrarFila(){
			var curElement = event.srcElement;
			var tr= curElement.parentElement.parentElement;
         	document.all.tblOrdenar.deleteRow( tr.sectionRowIndex);
      	}
      	
      	function subirFila(){
			var curElement = event.srcElement;
			var oTable = document.all.tblOrdenar;
			var cantRows = oTable.rows.length;
         	var tr= curElement.parentElement.parentElement;
         	if ((tr.sectionRowIndex) <= 0){
         		return 0;         		
         	}else{
         		oTable.moveRow(tr.sectionRowIndex, tr.sectionRowIndex-1);
         	}
      	}
      	function bajarFila(){
			var curElement = event.srcElement;
			var oTable = document.all.tblOrdenar;
			var cantRows = oTable.rows.length;
			var tr = curElement.parentElement.parentElement;
         	
         	if ((tr.sectionRowIndex+1) >= cantRows){
         		return 0;         		
         	}else{
         		oTable.moveRow(tr.sectionRowIndex, tr.sectionRowIndex+1);
         	}
         	
      	}
    function validateForm(frmMostrar){
		var oTable = document.all.tblOrdenar;
		var cantRows = oTable.rows.length;
		
		if (cantRows >= 1){
			ordenarLista();
			return true;
		}
		alert("Debe agregar por lo menos una columna.\n")
		return false;
    }
    
    function ordenarLista() {	
	}

    </script>
</head>

<body>
    <form name="frmMostrar" onsubmit="javascript:validateForm(this)">
        <table class="formulario">
			<tr>
				<th colspan="3">
					Ordenar lista
				</th>
			</tr>
            <tr>
            	<td>
                    Columnas
                </td>
				<td>           
                	<select name="cboCampo" ID="combo" runat="server" Width="146px">
               			<c:forEach items="${param.columns}" var="nombreColumna">
							<option name="opcion" value='<c:out value="${nombreColumna}"/>'>
								<fmt:bundle basename="resources.FieldLabels">
									<fmt:message key="app.label.${nombreColumna}"/>
								</fmt:bundle>
							</option>
						</c:forEach>
					</select>
				</td>		                
                <td>
					 <button onclick="javascript:agregarFila(document.frmMostrar.cboCampo.value,document.frmMostrar.cboCampo.options[document.frmMostrar.cboCampo.selectedIndex].text,'ASC');" value="Agregar">
					 	Agregar
					 </button>
				</td>
			</tr>				
        </table>        
        <table class="formulario">
            <tr>
                <th width="30%">
                    Columna
                </th>
                <th width="40%">
                    Orden
                </th>
                <th width="30%">
                    Acción
                </th>                
            </tr>
            <tbody name=tblOrdenar id=tblOrdenar>
			</tbody>
        </table>
		<br>
        <table class="formulario">
            <tr>
				<td><input type="submit" value="Aceptar"></td>
                <td><input type="reset" name="Cancelar" value="Cancelar" onClick=window.close()></td>
            </tr>
        </table>
    </form>
</body>
</html>
