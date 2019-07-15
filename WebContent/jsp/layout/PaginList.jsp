<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<TITLE>Paginar Lista</TITLE>	
		<META http-equiv=Content-Type content="text/html; charset=iso-8859-1">
		<LINK href="../../css/estilo.css" type=text/css rel=stylesheet>
		<script type="text/javascript" src="../../js/libreria.js"></script>
		
		<SCRIPT>
			function changePageSize()
			{
				var i=0;
				var size=0;
	
				while (i<document.forms[0].opcion.length) {
				  if (document.forms[0].opcion[i].checked) {
				    if (i!=3)
				    	size=document.forms[0].opcion[i].value;
				    else
				    	size=document.forms[0].txtBox.value;
				    	if (size=="") {
							alert("Debe seleccionar un valor");
							return false;
				    	}				    	
				  }
				  i++;
				}
				
				var myLocation = window.opener.location.href;
				myLocation = setParamValue("pagesize", size, myLocation);
						
				window.close();
				return true;
			}
		</SCRIPT>
		<META content="MSHTML 6.00.2900.2912" name=GENERATOR>
	</HEAD>
	<BODY>
		<FORM name="form1" onsubmit="return changePageSize();">
			<DIV>
			<TABLE class=formulario id=Table1>
			  <TH colspan=8 align=center>
				Paginar lista
			  </TH>

			  <TR>
				<TD colSpan=8>&nbsp;</TD>
			  </TR>

			  <TR>
				<TD class=displayListItems scope=colgroup colSpan=2>
					<INPUT type=radio CHECKED value=10 name=opcion>10
			    </TD>

				<TD class=displayListItems scope=colgroup colSpan=2>
					<INPUT type=radio value=50 name=opcion>50
				</TD>

				<TD class=displayListItems colSpan=2>
					<INPUT type=radio value=100 name=opcion>100
			    </TD>

				<TD colspan=2>
					<INPUT type=radio value=1 name=opcion id=rad onfocus="javascript:document.forms.form1.txtBox.focus();"> Otro &nbsp;&nbsp;
					<INPUT maxLength=3 size=3 name=txtBox onfocus="javascript:document.forms.form1.opcion[3].checked=true;">
			  </TR>

			  <TR>
				<TD>&nbsp;</TD>
			  </TR>
			  <TR>
				<TD colspan=8 align=center>
					<INPUT type=submit value=Aceptar></INPUT>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<INPUT onclick=javascript:window.close() type=button value=Cancelar></INPUT>
				</TD>
			  </TR>
			</TABLE>
			</DIV>
		</FORM>
	</BODY>
</HTML>
