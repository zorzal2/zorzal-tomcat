package com.fontar.web.action.seguimientos.rendiciones;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.bean.SeguimientoBean;
import com.fontar.data.impl.domain.dto.ProyectoCabeceraDTO;
import com.fontar.util.SessionHelper;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilterBuilder;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.web.action.BaseInventarioAction;

/**
 * 
 * @author gboaglio
 * 
 */
public class RendicionCuentasInventarioAction extends BaseInventarioAction {

	private AdministrarSeguimientoServicio administrarSeguimientoService;
		
	@Override
	protected void initInventario(HttpServletRequest request) throws Exception {		

		
		Long idSeguimiento = getIdSeguimiento(request);
		SeguimientoBean seguimiento = administrarSeguimientoService.obtenerSeguimiento(idSeguimiento);
		request.setAttribute("seguimientoId", seguimiento.getId());

		Boolean permiteAgregarOQuitarRendiciones = administrarSeguimientoService.permiteAgregarOQuitarRendiciones(idSeguimiento);
		Boolean permiteEdicionDeMontosSolicitados = administrarSeguimientoService.permiteEdicionDeMontosSolicitados(idSeguimiento);
		
		
		request.setAttribute("permiteAgregarOQuitarRendiciones", permiteAgregarOQuitarRendiciones);
		request.setAttribute("permiteEdicionDeMontosSolicitados", permiteEdicionDeMontosSolicitados);
		
		setCabeceraVisualizacionSeguimiento(request);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void addCustomFilters(HttpServletRequest request, ToolbarFiltersForm form) {

		Long idSeguimiento = SessionHelper.getIdSeguimiento(request);		
		
		ToolbarQueryFilter filtroSeguimiento = ConfigurableToolbarFilterBuilder.buildPropertyBasedFilter(
				"idSeguimiento",
				DefaultFilterTypeLibrary.NUMBER_EQUAL,
				idSeguimiento,
				Long.class
		);
		
		Map filters = form.getFiltersMap();
		filters.put("idSeguimiento",filtroSeguimiento);
		
		form.setFiltersMap(filters);
		
		
	}
	
	public Long getIdSeguimiento(HttpServletRequest request) {
		return SessionHelper.getIdSeguimiento(request);
	}
	
	/**
	 * 	Guarda en el request los datos necesarios para armar la cabecera    
	 */
	public void setCabeceraVisualizacionSeguimiento(HttpServletRequest request) {
		Long idSeguimiento = getIdSeguimiento(request);

		ProyectoCabeceraDTO dto = administrarSeguimientoService.obtenerDatosCabeceraSeguimientoVisualizacion(idSeguimiento);		
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, dto);
	}
	
	/**
	 * 
	 */
	public void setAdministrarSeguimientoService(AdministrarSeguimientoServicio administrarSeguimientoService) {
		this.administrarSeguimientoService = administrarSeguimientoService;
	}
}
