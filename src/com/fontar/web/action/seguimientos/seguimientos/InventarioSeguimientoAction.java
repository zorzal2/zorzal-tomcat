package com.fontar.web.action.seguimientos.seguimientos;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.intrumento.AdministrarInstrumentosServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.web.action.IventarioConEstadoAnuladoAction;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * 
 * @author gboaglio
 *
 */
public class InventarioSeguimientoAction extends IventarioConEstadoAnuladoAction {
	
	private AdministrarInstrumentosServicio administrarInstrumentosService;
			
	public AdministrarInstrumentosServicio getAdministrarInstrumentosService() {
		return administrarInstrumentosService;
	}

	public void setAdministrarInstrumentosService(AdministrarInstrumentosServicio administrarInstrumentosService) {
		this.administrarInstrumentosService = administrarInstrumentosService;
	}

	@Override
	protected void initInventario(HttpServletRequest request) throws Exception {

		// GB/ Limpio la variable en la que se guarda el link del botón "Cerrar"
		// de las solapas de visualización de Seguimientos. 
		// Esto se hace porque a dicha visualización se puede haber llegado antes 
		// por otro camino y por lo tanto puede haber quedado la variable  
		// NAVIGATION_OVERRIDE_FORWARD apuntando a otro inventario distinto de este. 
		request.getSession().removeAttribute(BaseMappingDispatchAction.NAVIGATION_OVERRIDE_FORWARD);
		
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

		ArrayList estados = (ArrayList) handler.getComboFiltroConEmptyLabelAll(EstadoSeguimiento.class);
		request.setAttribute("estados", estados);
	}
	
	protected String getPropertyName(){
		return "estado";
	}
	
}
