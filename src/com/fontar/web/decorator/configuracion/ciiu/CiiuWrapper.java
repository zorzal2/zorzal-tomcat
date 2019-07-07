package com.fontar.web.decorator.configuracion.ciiu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.CiiuBean;
import com.fontar.web.decorator.TableDecoratorSupport;
import com.lowagie.text.html.HtmlEncoder;
import com.pragma.util.WebUtils;

/**
 * 
 * @author ssanchez Cargar, editar, eliminar de pagina CIIUhhh
 */
public class CiiuWrapper extends TableDecoratorSupport {

	public String getLinkSeleccion() throws UnsupportedEncodingException {
		CiiuBean lObject = (CiiuBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();
		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('Ciiu','");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(lObject.getNombre()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");
		return link.toString();
	}
	
	public String getLinkSeleccionInventario() throws UnsupportedEncodingException {
		CiiuBean lObject = (CiiuBean) this.getCurrentRowObject();
		StringBuffer link = new StringBuffer();
		link.append("<a href='#' onclick=\"");
		link.append("handleCIIUSelection({id:");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append(",displayValue:");
		link.append(WebUtils.toSafeQuotedJavascriptString(HtmlEncoder.encode(String.valueOf(lObject.getNombre()))));		
		link.append(",code:'");
		link.append(lObject.getCodigo() );
		link.append("'});window.close();");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");
		return link.toString();
	}

}
