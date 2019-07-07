package com.fontar.web.decorator.instrumentos.pitec;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.data.impl.domain.bean.IdeaProyectoPitecBean;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.administracion.proyecto.ProyectoWrapper;

/**
 * 
 * @author gboaglio
 */
public class IdeaProyectoPitecWrapper extends ProyectoWrapper {

	public String getLinkCargar() throws UnsupportedEncodingException {
		IdeaProyectoPitecBean ideaProyectoPitecBean = (IdeaProyectoPitecBean) this.getCurrentRowObject();

		StringBuffer link = new StringBuffer();

		if (ideaProyectoPitecBean.getEstado().equals(EstadoProyecto.ADJUDICADO)) {
			link.append("<a href=\"IdeaProyectoPitecCargarVentanilla.do?");
			link.append("id=" + URLEncoder.encode(String.valueOf(ideaProyectoPitecBean.getId()), "UTF-8"));
			link.append("\"><img src='images/cargarproyecto.gif' title=");
			link.append("'" + ResourceManager.getAltResource("app.alt.cargarProyecto") + "' border=0 /></a>");
		}

		return link.toString();
	}

}
