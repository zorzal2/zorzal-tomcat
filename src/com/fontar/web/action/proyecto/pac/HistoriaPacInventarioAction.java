package com.fontar.web.action.proyecto.pac;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.configuracion.FaltanCotizacionesException;
import com.fontar.bus.api.proyecto.pac.AdministrarPACServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.filter.PacIdToolbarFilter;
import com.fontar.data.impl.domain.dto.DesembolsoUFFADTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.data.impl.domain.dto.VisualizarPacItemDTO;
import com.fontar.util.SessionHelper;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.web.action.BaseInventarioAction;

public class HistoriaPacInventarioAction extends BaseInventarioAction {

	AdministrarPACServicio administrarPACServicio;

	protected WFProyectoServicio wfProyectoServicio;

	@Override
	protected void addCustomFilters(HttpServletRequest request, ToolbarFiltersForm form) {
		Long idProyecto = SessionHelper.getIdProyecto(request);
		String id = request.getParameter("id");
		Long idPacItem = new Long(id);
		ToolbarQueryFilter filtroPac = new PacIdToolbarFilter(idPacItem);
				
		Map filters = form.getFiltersMap();
		filters.put("idPacItem",filtroPac);		
		form.setFiltersMap(filters);
	}

	@Override
	/**
	 * Inicialización del inventario
	 */
	protected void initInventario(HttpServletRequest request) {

		
		Long id = new Long(request.getParameter("id"));
		
		Long idProyecto = SessionHelper.getIdProyecto(request);
		if(idProyecto ==null)
			idProyecto = new Long(request.getParameter("ID_PROYECTO"));
		
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		
		String opcion= request.getParameter("OPCION_CORTA");
		request.setAttribute("OPCION_CORTA", opcion);
				
		VisualizarPacItemDTO pacDTO;
		try {
			pacDTO = administrarPACServicio.obtenerDatosItemTabla(id);
		}
		catch (FaltanCotizacionesException e) {
			throw new RuntimeException(e);
		}
		request.setAttribute("pac", pacDTO);
		request.setAttribute("nada", id);
		
		Collection<DesembolsoUFFADTO> desembolsos = administrarPACServicio.obtenerDesembolsos(id);
		//Ordeno los items
		Comparator<DesembolsoUFFADTO> comparator = new Comparator<DesembolsoUFFADTO>() {
			public int compare(DesembolsoUFFADTO o1, DesembolsoUFFADTO o2) {
				return o1.getCuota().compareTo(o2.getCuota());
			}};
		Collection<DesembolsoUFFADTO> desembolsosOrdenados = new TreeSet<DesembolsoUFFADTO>(comparator);
		desembolsosOrdenados.addAll(desembolsos);
		request.setAttribute("desembolsos", desembolsosOrdenados);
	}

	public AdministrarPACServicio getAdministrarPACServicio() {
		return administrarPACServicio;
	}

	public void setAdministrarPACServicio(AdministrarPACServicio administrarPACServicio) {
		this.administrarPACServicio = administrarPACServicio;
	}

	public WFProyectoServicio getWfProyectoServicio() {
		return wfProyectoServicio;
	}

	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
	}

}