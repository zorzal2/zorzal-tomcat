package com.fontar.web.decorator.evaluacion;

import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.web.decorator.TableDecoratorSupport;

public class CargarEvaluadorWrapper extends TableDecoratorSupport {

	public String getLinkBorrado() throws Exception {

		ProyectoBean lObject = (ProyectoBean) this.getCurrentRowObject();
		String idProyecto = URLEncoder.encode(String.valueOf(lObject.getId()), "UTF-8");

		String link = "";

		link = "<a href='#' onclick='' " + "\"><img src='images/eliminar.gif' border=0></a>";

		return link;
	}
}
