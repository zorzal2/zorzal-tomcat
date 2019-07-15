<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>

<script language="javascript">
	// cambio de accion, hay diferencia para la primer evaluación y las siguientes
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
	<p>VISTO el Decreto N° [?.] del [?.], el Contrato de Préstamo suscrito con el BANCO INTERAMERICANO DE DESARROLLO Nº 1728 OC/AR, la Resolución de la  SECRETARIA DE CIENCIA Y TECNOLOGIA  Nº [?] de fecha [?]y la Resolución de esta AGENCIA NACIONAL DE PROMOCION CIENTÍFICA Y TECNOLÓGICA Nº [?]de fecha [?..], y</p>
	<p>&nbsp;</p>
	<p>CONSIDERANDO:</p>
	<p>Que mediante la Resolución de la AGENCIA NACIONAL DE PROMOCION CIENTÍFICA Y TECNOLÓGICA Nº [?.], citada en el VISTO del presente acto se instrumentó la Convocatoria <bean:write name="proyecto" property="instrumento" /> a concurso público para la presentación de proyectos de innovación tecnológica para la adjudicación de [?..]</p>
<p>Que a la citada convocatoria se presentaron un conjunto de empresas, entre las que se encuentra la que fue individualizada con el código [??], correspondiente a la empresa <bean:write name="proyecto" property="txtEntidadBeneficiaria" /></p>
<p>Que analizada dicha presentación se observa que la misma fue practicada en un todo de acuerdo con las pautas establecidas en la citada Resolución Nº [?.]</p>


<p>Que la presente medida se dicta en ejercicio de las atribuciones que confiere la Resolución Nº [?..] de fecha [...]</p>
<p>&nbsp;</p>
<p>Por ello,</p>
<p>EL DIRECTOR DE LA 
UNIDAD DE CONTROL DE GESTION Y ASUNTOS LEGALES
DECIDE:</p>
<p>ARTICULO 1º.-  Declarar propuesta admitida a la presentada por la empresa [?..], cuyo código de identificación es [?.]</p>
<p>ARTICULO 2º.- Pasar las presentes actuaciones al FONDO TECNOLOGICO ARGENTINO para su evaluación.</p>
<p>ARTICULO 3º.- Regístrese, notifíquese al interesado, tome razón el FONDO TECNOLOGICO ARGENTINO.</p>



<p>&nbsp;</p>

DISPOSICION Nº <bean:write name="admisibilidad" property="fundamentacion"/>

</table>
<div id="divButtons">
	<pragmatags:btnOkCancel
		okJavascript="swapAction();"
		cancelJavascript="cerrarPopUp();" />
</div>
</html:form>