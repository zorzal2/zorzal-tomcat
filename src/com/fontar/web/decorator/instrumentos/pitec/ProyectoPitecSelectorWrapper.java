package com.fontar.web.decorator.instrumentos.pitec;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.ProyectoPitecBean;
import com.fontar.web.decorator.TableDecoratorSupport;

public class ProyectoPitecSelectorWrapper extends TableDecoratorSupport {

	public String getLinkSeleccion() throws UnsupportedEncodingException {
		ProyectoPitecBean lObject = (ProyectoPitecBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();

		link.append("<a href=# onclick=\"");
		link.append("cargarDatos('ProyectoPitec','");
		link.append(URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8"));
		link.append("','");
		link.append(String.valueOf(lObject.getCodigo()));
		link.append("');");
		link.append("\"><img src='images/aceptar.gif' border=0></a>");

		return link.toString();
	}
}
