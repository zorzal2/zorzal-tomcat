package com.fontar.web.action.administracion.evaluacion;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.intrumento.AdministrarInstrumentosServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.web.action.IventarioConEstadoAnuladoAction;
import com.pragma.toolbar.config.default_config.DefaultToolbarConfig;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilter;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilterBuilder;
import com.pragma.toolbar.properties.FilterType;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;

/**
 * Action de inventario de evaluaciones
 * @see BaseInventarioAction: tiene los métodos necesarios para implementación
 * de la Toolbar
 * @author ssanchez
 * @version 1.00, 06/12/06
 */
public class EvaluacionInventarioAction extends IventarioConEstadoAnuladoAction {

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

		ArrayList estados = (ArrayList) handler.getComboFiltroConEmptyLabelAll(EstadoEvaluacion.class);
		request.setAttribute("estados", estados);
	}
	
	protected String getPropertyName(){
		return "estado";
	}

}
