package com.fontar.web.action.seguimientos.evaluaciones;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.intrumento.AdministrarInstrumentosServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.seguimiento.evaluacion.EstadoEvaluacionSeguimiento;
import com.fontar.web.action.IventarioConEstadoAnuladoAction;


/**
 * Action para el inventario de <code>Evaluaciones de Seguimiento</code>.<br>
 * @author ssanchez
 */
public class EvaluacionSeguimientoInventarioAction extends IventarioConEstadoAnuladoAction {

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

		ArrayList instrumentos = new ArrayList();
		instrumentos.addAll(handler.getLabelValueInstrumentos(administrarInstrumentosService.obtenerInstrumentos()));
		request.setAttribute("instrumentos", instrumentos);

		ArrayList estados = (ArrayList) handler.getComboFiltroConEmptyLabelAll(EstadoEvaluacionSeguimiento.class);
		request.setAttribute("estados", estados);
	}

	@Override
	protected String getPropertyName() {
		return "estado";
	}

}
