package com.fontar.web.action.instrumento.pitec;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.intrumento.AdministrarInstrumentosServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.pragma.web.action.BaseInventarioAction;

/**
 * Action de inventario de ideas proyecto pitec
 * @author gboaglio
 * 
 */
public class IdeaProyectoPitecInventarioAction extends BaseInventarioAction {

	AdministrarInstrumentosServicio administrarInstrumentosService;

	public void setAdministrarInstrumentosService(AdministrarInstrumentosServicio administrarInstrumentosService) {
		this.administrarInstrumentosService = administrarInstrumentosService;
	}

	@Override
	protected void initInventario(HttpServletRequest request) throws SQLException {
		setComboOptions(request);
	}

	@SuppressWarnings("unchecked")
	private void setComboOptions(HttpServletRequest request) throws SQLException {
		CollectionHandler handler = new CollectionHandler();
		ArrayList estados = (ArrayList) handler.getComboFiltro(EstadoProyecto.class);
		request.setAttribute("estados", estados);

		ArrayList instrumentos = new ArrayList();
		instrumentos.addAll(handler.getLabelValueInstrumentos(administrarInstrumentosService.obtenerInstrumentos()));
		request.setAttribute("instrumentos", instrumentos);
	}

}
