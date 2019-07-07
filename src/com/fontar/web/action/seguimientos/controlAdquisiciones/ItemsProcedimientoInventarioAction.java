package com.fontar.web.action.seguimientos.controlAdquisiciones;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.util.SessionHelper;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilterBuilder;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.web.action.BaseInventarioAction;

/**
 * Inventario de los items de un procedimiento.<br>
 * @author ssanchez
 */
public class ItemsProcedimientoInventarioAction extends BaseInventarioAction {

	protected AdministrarProcedimientoServicio administrarProcedimientoServicio;
	
	public void setAdministrarProcedimientoServicio(AdministrarProcedimientoServicio administrarProcedimientoServicio) {
		this.administrarProcedimientoServicio = administrarProcedimientoServicio;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addCustomFilters(HttpServletRequest request, ToolbarFiltersForm form) {
		
		Long idProcedimiento = SessionHelper.getIdProcedimiento(request);		
		
		ToolbarQueryFilter filtroSeguimiento = ConfigurableToolbarFilterBuilder.buildPropertyBasedFilter(
				"idProcedimiento",
				DefaultFilterTypeLibrary.NUMBER_EQUAL,
				idProcedimiento,
				Long.class
		);
		
		Map filters = form.getFiltersMap();
		filters.clear();
		filters.put("idProcedimiento",filtroSeguimiento);
		
		form.setFiltersMap(filters);
	}

	@Override
	protected void initInventario(HttpServletRequest request) throws Exception {

		super.initInventario(request);
		
		ProcedimientoBean procedimiento = administrarProcedimientoServicio.obtenerProcedimiento(SessionHelper.getIdProcedimiento(request));
		request.setAttribute("procedimiento", procedimiento);
	}
}
