<%@ taglib uri="/tags/struts-bean-el" prefix="bean-el" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="/tags/displaytag-el" prefix="display-el" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib tagdir="/WEB-INF/tags/pragmatags" prefix="pragmatags" %>
<%@ taglib uri="/tags/displaytag-12" prefix="display" %>

<h3>
	<bean-el:message bundle="titles" key="app.title.moneda.inventario"/>
</h3>
<pragmatags:btnAgregar action="/MonedaAgregar" permissions="MONEDAS-AGREGAR"/>
<br/>
<display-el:table name="collection" class="inventario" decorator="com.fontar.web.decorator.configuracion.moneda.MonedaWrapper">		
	<display-el:setProperty name="basic.show.header" value="false">
		<display-el:caption>
			<tr>
				<th><bean-el:message bundle="headers" key="app.header.moneda.tipoMoneda" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.moneda.descripcion" /></th>	    
				<th><bean-el:message bundle="headers" key="app.header.moneda.codigoEmerix" /></th>	    
				<th class="accionesInventario"><bean-el:message bundle="headers" key="app.header.acciones" /></th>
		    </tr> 
		</display-el:caption> 
        <display-el:column property="tipoMoneda" />   
        <display-el:column property="descripcion" /> 
        <display-el:column property="codigoEmerix" /> 
        <display-el:column class="accionesInventario" property="linkEditar" />
	</display-el:setProperty>
</display-el:table>