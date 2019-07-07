package com.fontar.web.action.administracion;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.intrumento.AdministrarInstrumentosServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.web.action.IventarioConEstadoAnuladoAction;
import com.pragma.toolbar.config.default_config.DefaultToolbarConfig;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilter;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilterBuilder;
import com.pragma.toolbar.properties.FilterType;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;

/**
 * Action de inventario de proyectos
 * @author gboaglio, ssanchez @ version 1.0.1, 13/12/06
 * 
 */
public class ProyectoInventarioAction extends IventarioConEstadoAnuladoAction {

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
		ArrayList estados = (ArrayList) handler.getComboFiltroConEmptyLabelAll(EstadoProyecto.class);
		request.setAttribute("estados", estados);

		ArrayList instrumentos = new ArrayList();
		
		instrumentos.addAll(handler.getLabelValueInstrumentos(administrarInstrumentosService.obtenerInstrumentos()));
		request.setAttribute("instrumentos", instrumentos);
	}

	protected String getPropertyName(){
		return "codigoEstado";
	}

}
