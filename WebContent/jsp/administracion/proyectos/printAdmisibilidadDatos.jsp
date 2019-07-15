<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<script language="javascript">
	// cambio de accion, hay diferencia para la primer evaluaci�n y las siguientes
	function swapAction() {
	  
	  toggleVisibility('divButtons', false);
	  alert("test");
	  //document.print();
	  toggleVisibility('divButtons', true);
	}
			
</script>


<!--  Detalle -->
<html:form action="/VisualizarAdmisibilidadProyecto.do">
<html:hidden property="id" />
<table class="formulario">		
<colgroup>
	<col width="25%">
	<col width="100%">
</colgroup>	

	<!------------General------------>
	<p>VISTO el Decreto N� [?.] del [?.], el Contrato de Pr�stamo suscrito con el BANCO INTERAMERICANO DE DESARROLLO N� 1728 OC/AR, la Resoluci�n de la  SECRETARIA DE CIENCIA Y TECNOLOGIA  N� [?] de fecha [?]y la Resoluci�n de esta AGENCIA NACIONAL DE PROMOCION CIENT�FICA Y TECNOL�GICA N� [?]de fecha [?..], y</p>
	<p>&nbsp;</p>
	<p>CONSIDERANDO:</p>
	<p>Que mediante la Resoluci�n de la AGENCIA NACIONAL DE PROMOCION CIENT�FICA Y TECNOL�GICA N� [?.], citada en el VISTO del presente acto se instrument� la Convocatoria <bean:write name="proyecto" property="instrumento" /> a concurso p�blico para la presentaci�n de proyectos de innovaci�n tecnol�gica para la adjudicaci�n de [?..]</p>
<p>Que a la citada convocatoria se presentaron un conjunto de empresas, entre las que se encuentra la que fue individualizada con el c�digo [??], correspondiente a la empresa <bean:write name="proyecto" property="txtEntidadBeneficiaria" /></p>
<p>Que analizada dicha presentaci�n se observa que la misma fue practicada en un todo de acuerdo con las pautas establecidas en la citada Resoluci�n N� [?.]</p>


<p>Que la presente medida se dicta en ejercicio de las atribuciones que confiere la Resoluci�n N� [?..] de fecha [...]</p>
<p>&nbsp;</p>
<p>Por ello,</p>
<p>EL DIRECTOR DE LA 
UNIDAD DE CONTROL DE GESTION Y ASUNTOS LEGALES
DECIDE:</p>
<p>ARTICULO 1�.-  Declarar propuesta admitida a la presentada por la empresa [?..], cuyo c�digo de identificaci�n es [?.]</p>
<p>ARTICULO 2�.- Pasar las presentes actuaciones al FONDO TECNOLOGICO ARGENTINO para su evaluaci�n.</p>
<p>ARTICULO 3�.- Reg�strese, notif�quese al interesado, tome raz�n el FONDO TECNOLOGICO ARGENTINO.</p>



<p>&nbsp;</p>

DISPOSICION N� <bean:write name="admisibilidad" property="fundamentacion"/>

</table>
<div id="divButtons">
	<pragmatags:btnOkCancel
		okJavascript="swapAction();"
		cancelJavascript="cerrarPopUp();" />
</div>
</html:form>