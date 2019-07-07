package com.fontar.web.decorator.configuracion;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.dto.EvaluadorDTO;
import com.fontar.web.decorator.TableDecoratorSupport;

public class EvaluadorSelectorWrapper extends TableDecoratorSupport {

	public String getLinkSeleccion() throws UnsupportedEncodingException {
		EvaluadorDTO lObject = (EvaluadorDTO) this.getCurrentRowObject();
		
		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('Evaluador','");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(lObject.getNombre()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}
}
