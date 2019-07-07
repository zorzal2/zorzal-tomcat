package com.fontar.web.decorator.administracion.notificacion;

import java.io.UnsupportedEncodingException;

import com.fontar.data.impl.domain.bean.NotificacionBean;
import com.fontar.web.decorator.link.impl.VisualizarLink;
import com.fontar.web.decorator.workflow.BaseEntityWorkFlowWrapper;

/**
 * Muestra links en cada fila del inventario
 * @author ssanchez 
 */
public class NotificacionWrapper extends BaseEntityWorkFlowWrapper {

	public String getLinkVisualizar() throws UnsupportedEncodingException {
		NotificacionBean notificacionBean = (NotificacionBean) this.getCurrentRowObject();
		VisualizarLink visualizarLink = new VisualizarLink("VisualizarNotificacion.do", "app.alt.visualizarNotificacion", notificacionBean.getId());

		return visualizarLink.displayValue();
	}
}
