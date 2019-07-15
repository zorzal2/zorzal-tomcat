<%@ taglib uri="/tags/struts-bean" prefix="bean" %> 
<%@ taglib uri="/tags/struts-html" prefix="html" %> 
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<table border="0" width="100%">
	<colgroup>
		<col width="25%">
		<col width="100%">
	</colgroup>	

	<tr>
 		<td class="obligatorio"><bean:message bundle="labels" key="app.label.direccion"/></td>
		<td class="opcional">
			<c:out value="${direccion}"/>
		</td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.localidad"/></td>
		<td class="opcional">
			<c:out value="${localidad}"/>
		</td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.departamento"/></td>
		<td class="opcional">
	   		<c:out value="${departamento}"/>
		</td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.codigoPostal"/></td>
		<td class="opcional">
	   		<c:out value="${codigoPostal}"/>
		</td>
     </tr>
  	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.pais"/></td>
		<td class="opcional">
	   		<c:out value="${pais}"/>
		</td>
    </tr>
	   	<tr>
		<td class="obligatorio">
			<bean:message bundle="labels" key="app.label.idJurisdiccion"/>
		</td>
		<td class="opcional">
	   		<c:out value="${nombreJurisdiccion}"/>
		</td>
    </tr>    
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.telefono"/></td>
		<td class="opcional">
	   		<c:out value="${telefono}"/>
		</td>
    </tr>
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.fax"/></td>
		<td class="opcional">
	   		<c:out value="${fax}"/>
		</td>
    </tr>      
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.email"/></td>
		<td class="opcional">
	   		<c:out value="${email}"/>
		</td>
     </tr>  
   	<tr>
		<td class="obligatorio"><bean:message bundle="labels" key="app.label.paginaWeb"/></td>
		<td class="opcional">
	   		<c:out value="${paginaWeb}"/>
		</td>
     </tr>  
</table>

