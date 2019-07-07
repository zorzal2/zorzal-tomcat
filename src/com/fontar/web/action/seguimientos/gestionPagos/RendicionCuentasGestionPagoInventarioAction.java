package com.fontar.web.action.seguimientos.gestionPagos;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.seguimientos.seguimientos.AdministrarSeguimientoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.SeguimientoVisualizacionCabeceraDTO;
import com.fontar.util.SessionHelper;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilterBuilder;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.web.action.BaseInventarioAction;

/**
 * @author ssanchez
 */
public class RendicionCuentasGestionPagoInventarioAction extends BaseInventarioAction {

	private static final String TOOLBAR_ID_PREFIX_CF = "CF"; 
	private static final String TOOLBAR_ID_PREFIX_ANR = "ANR"; 
	
	private AdministrarSeguimientoServicio administrarSeguimientoServicio;
	private String toolbarPrefix = "";
	
	public void setAdministrarSeguimientoServicio(AdministrarSeguimientoServicio administrarSeguimientoServicio) {
		this.administrarSeguimientoServicio = administrarSeguimientoServicio;
	}

	@Override
	protected void initInventario(HttpServletRequest request) throws Exception {
		
		super.initInventario(request);
		
		Long idSeguimiento = SessionHelper.getIdSeguimiento(request);
		SeguimientoVisualizacionCabeceraDTO dto = administrarSeguimientoServicio.obtenerDatosCabeceraSeguimientoVisualizacion(idSeguimiento);
		request.setAttribute(CabeceraAttribute.SEGUIMIENTO, dto);
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

	@Override
	protected void initToolbar(HttpServletRequest request, ToolbarFiltersForm form) {
		Long idSeguimiento = SessionHelper.getIdSeguimiento(request);
		//Agrego el sufijo adecuado segun el proyecto sea de credito fiscal o no
		if(administrarSeguimientoServicio.esDeInstrumentoConAlicuotaCF(idSeguimiento)) {
			toolbarPrefix = TOOLBAR_ID_PREFIX_CF;
		} else {
			toolbarPrefix = TOOLBAR_ID_PREFIX_ANR;
		}
		super.initToolbar(request, form);
	}
	@Override
	public String getIdToolbar() {
		return super.getIdToolbar()+toolbarPrefix;
	}
}
