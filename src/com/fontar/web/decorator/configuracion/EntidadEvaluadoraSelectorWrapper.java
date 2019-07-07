package com.fontar.web.decorator.configuracion;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.EntidadEvaluadoraBean;
import com.fontar.web.decorator.TableDecoratorSupport;

public class EntidadEvaluadoraSelectorWrapper extends TableDecoratorSupport {
	public String getLinkSeleccion() throws UnsupportedEncodingException {
		EntidadEvaluadoraBean lObject = (EntidadEvaluadoraBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('EntidadEvaluadora','");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append("','");
		// link.append(String.valueOf(eb.getDenominacion()));
		link.append(String.valueOf(lObject.getDenominacion()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}
	
	public String getLinkSeleccionInventario() throws UnsupportedEncodingException {
		EntidadEvaluadoraBean lObject = (EntidadEvaluadoraBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("handleSelection({id:");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append(",displayValue:'");
		link.append(String.valueOf(lObject.getDenominacion()));
		link.append("'});window.close();");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");
		return link.toString();
	}
}