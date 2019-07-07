package com.fontar.web.action.seguimientos.seguimientos;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.data.filter.ProyectIdToolbarFilter;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.SessionHelper;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.util.ContextUtil;
import com.pragma.web.action.BaseInventarioAction;
import com.pragma.web.action.BaseMappingDispatchAction;

public class SeguimientoInventarioAction extends BaseInventarioAction {

		
	@Override
	protected void addCustomFilters(HttpServletRequest request, ToolbarFiltersForm form) {

		Long idProyecto = getIdProyecto(request);

		ToolbarQueryFilter filtroProyecto = new ProyectIdToolbarFilter(idProyecto);
				
		Map filters = form.getFiltersMap();
		filters.put("idProyecto",filtroProyecto);		
		form.setFiltersMap(filters);
	}

	@Override
	protected void initInventario(HttpServletRequest request) throws Exception {

		// GB/ Limpio la variable en la que se guarda el link del botón "Cerrar"
		// de las solapas de visualización de Seguimientos. 
		// Esto se hace porque a dicha visualización se puede haber llegado antes 
		// por otro camino y por lo tanto puede haber quedado la variable  
		// NAVIGATION_OVERRIDE_FORWARD apuntando a otro inventario distinto de este. 
		request.getSession().removeAttribute(BaseMappingDispatchAction.NAVIGATION_OVERRIDE_FORWARD);
		
		Long idProyecto = getIdProyecto(request);
			/* Para Visualización de Proyecto */
			AdministrarProyectoServicio  administracionProyectoService = (AdministrarProyectoServicio) ContextUtil.getBean("administrarProyectoService");
			ProyectoVisualizacionDTO visualizacionDTO = (ProyectoVisualizacionDTO) administracionProyectoService.obtenerDatosVisualizacionProyecto(idProyecto);
			request.setAttribute("proyecto", visualizacionDTO);
							
			Boolean permiteFinanciamientoBancario = visualizacionDTO.getPermiteFinanciamientoBancario();
			request.setAttribute("permiteFinanciamientoBancario", permiteFinanciamientoBancario);
			request.setAttribute("labelNumeroOrigen", "numeroPresentacion");
			request.setAttribute("entidadBeneficiariaOrigen", visualizacionDTO.getEntidadBeneficiariaOrigen());
			request.setAttribute("numeroOrigen", visualizacionDTO.getCodigoPresentacion());	
			request.setAttribute("ES_ADQUISICION", visualizacionDTO.getPermiteAdquisicion());
			request.setAttribute("tipoInstrumento", visualizacionDTO.getTipoInstrumentoDef());
		
	}
	
	private Long getIdProyecto(HttpServletRequest request){
		Long idProyecto = null;
		//veo si viene por parametro 
		if (validateParameter(request,"id")){
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		} else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		return idProyecto;
	}
	
}
