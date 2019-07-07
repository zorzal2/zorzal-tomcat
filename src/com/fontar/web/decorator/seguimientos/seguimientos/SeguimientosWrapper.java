package com.fontar.web.decorator.seguimientos.seguimientos;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.fontar.web.decorator.workflow.BaseEntityWorkFlowWrapper;

/**
 * 
 * @author ssanchez, gboaglio 
 * Cargar, editar, eliminar de pagina Seguimientos
 */
public class SeguimientosWrapper extends BaseEntityWorkFlowWrapper {

	public String getLinkVisualizar() {
		SeguimientoBean seguimientoBean = (SeguimientoBean) this.getCurrentRowObject();
		
		VisualizarLink visualizarLink = new VisualizarLink("SeguimientoVisualizar.do","app.alt.visualizarSeguimiento", seguimientoBean.getId());
		visualizarLink.setPermissionsByInstrumentoRequired(seguimientoBean.getProyecto().getIdInstrumento(), "SEGUIMIENTOS-VISUALIZAR");
		
		return visualizarLink.displayValue();
	}

	public String getLinkAlta() throws UnsupportedEncodingException {
		String link = "";

		link = "<a href=\"ABMArticulo.do?accion=verItem" + "&codArticulo="
				+ "\"><img class=\"imageButton\" src='images/cargarproyecto.gif' border=0></a>";

		return link;
	}

	public String getLinkEdicion() throws UnsupportedEncodingException {
		String link = "";

		link = "<a href=\"ABMArticulo.do?accion=verItem" + "&codArticulo="
				+ "\"><img class=\"imageButton\" src='images/edicion.gif' border=0></a>";

		return link;
	}

	public String getLinkBorrado() throws UnsupportedEncodingException {
		String link = "";

		link = "<a href=\"ABMArticulo.do?accion=verItem" + "&codArticulo="
				+ "\"><img class=\"imageButton\" src='images/eliminar.gif' border=0></a>";

		return link;
	}
	
}





