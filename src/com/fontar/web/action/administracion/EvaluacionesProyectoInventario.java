package com.fontar.web.action.administracion;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.data.filter.ProyectIdToolbarFilter;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.SessionHelper;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.web.action.BaseInventarioAction;

public class EvaluacionesProyectoInventario extends BaseInventarioAction {
	
	AdministrarProyectoServicio administrarProyectoServicio;

	public void setAdministrarProyectoServicio(AdministrarProyectoServicio administrarProyectoServicio) {
		this.administrarProyectoServicio = administrarProyectoServicio;
	}

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

		Long idProyecto = getIdProyecto(request);	
			
		/* Para Visualización de Proyecto */
		//FIXME: tienen que traer solo la CabeceraDTO de Proyecto (no todo)!!!
		ProyectoVisualizacionDTO visualizacionDTO = (ProyectoVisualizacionDTO) administrarProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute("proyecto", visualizacionDTO);
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
