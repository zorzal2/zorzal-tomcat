package com.fontar.web.decorator.configuracion.entidades.personas;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.PersonaBean;

public class PersonasSelectorWrapper extends PersonasWrapper {
 
	
	public String getLinkSeleccion() throws UnsupportedEncodingException {
		PersonaBean lObject = (PersonaBean) this.getCurrentRowObject();
		StringBuffer link = new StringBuffer();

		link.append("<a href='#' onclick=\"");
		link.append("handleSelection({id:");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append(",displayValue:'");
		link.append(String.valueOf(lObject.getNombre()));
		link.append("', eventName: 'selectionPersonaEvent'");
		link.append("});window.close();");
		//link.append(String.valueOf(lObject.getNombre()));
		//link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	} 
}
