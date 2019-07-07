package com.fontar.web.action.administracion.paquete;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.intrumento.AdministrarInstrumentosServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.paquete.EstadoPaquete;
import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.web.action.IventarioConEstadoAnuladoAction;

/**
 * Action de inventario de paquetes
 * @see BaseInventarioAction: tiene los métodos necesarios para implementación
 * de la Toolbar
 * @author ssanchez
 * @version 1.00, 30/11/06
 */
public class PaqueteInventarioAction extends IventarioConEstadoAnuladoAction {

	AdministrarInstrumentosServicio administrarInstrumentosService;

	public void setAdministrarInstrumentosService(AdministrarInstrumentosServicio administrarInstrumentosService) {
		this.administrarInstrumentosService = administrarInstrumentosService;
	}

	@Override
	/**
	 * Inicialización del inventario
	 */
	protected void initInventario(HttpServletRequest request) {
		setInitValues(request);
	}

	/**
	 * Cargo los datos necesitados por los combobox
	 */
	@SuppressWarnings("unchecked")
	private void setInitValues(HttpServletRequest request) {
		CollectionHandler handler = new CollectionHandler();

		ArrayList tipos = (ArrayList) handler.getComboFiltro(TipoPaquete.class);
		request.setAttribute("tipos", tipos);

		ArrayList estados = (ArrayList) handler.getComboFiltroConEmptyLabelAll(EstadoPaquete.class);
		request.setAttribute("estados", estados);

		ArrayList instrumentos = new ArrayList();
		instrumentos.addAll(handler.getLabelValueInstrumentos(administrarInstrumentosService.obtenerInstrumentos()));
		request.setAttribute("instrumentos", instrumentos);
		
	}
	
	protected String getPropertyName(){
		return "codigoEstado";
	}
	
}
