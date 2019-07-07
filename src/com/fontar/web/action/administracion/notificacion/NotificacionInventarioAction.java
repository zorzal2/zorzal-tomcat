package com.fontar.web.action.administracion.notificacion;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.notificacion.AdministrarNotificacionServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.notificacion.EstadoNotificacion;
import com.fontar.web.action.IventarioConEstadoAnuladoAction;

/**
 * Action del inventario <code>Toolbar</code> de <code>Notificaciones</code>.<br> 
 * @author ssanchez
 * @version 1.10, 22/03/07
 */
public class NotificacionInventarioAction extends IventarioConEstadoAnuladoAction {

	AdministrarNotificacionServicio administrarNotificacionServicio;

	public void setAdministrarNotificacionServicio(AdministrarNotificacionServicio administrarNotificacionServicio) {
		this.administrarNotificacionServicio = administrarNotificacionServicio;
	}

	@Override
	/**
	 * Inicialización del inventario
	 */
	protected void initInventario(HttpServletRequest request) {
		setInitValues(request);
	}

	/**
	 * Cargo los datos necesitados por los combobox usados en los filtros
	 */
	private void setInitValues(HttpServletRequest request) {
		CollectionHandler handler = new CollectionHandler();

		ArrayList estados = (ArrayList) handler.getComboFiltroConEmptyLabelAll(EstadoNotificacion.class);
		request.setAttribute("estados", estados);
	}
	
	@Override
	protected String getPropertyName() {
		return "estado";
	}

}
