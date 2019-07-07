package com.fontar.web.action.seguimientos.controlAdquisiciones;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.seguimientos.controlAdquisicion.AdministrarProcedimientoServicio;
import com.fontar.data.Constant;
import com.fontar.data.impl.domain.bean.ProcedimientoBean;
import com.fontar.data.impl.domain.codes.seguimiento.controlAdquisicion.ResultadoFontar;
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
public class CircuitoAutorizacionItemsInventarioAction extends BaseInventarioAction {

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
		
		String filtroItem = (String) request.getAttribute("filtroItem");
		if(Constant.FiltroProcedimientoItem.ITEMS_UFFA.equals(filtroItem)) {
			
			ToolbarQueryFilter filtroItemUffa = ConfigurableToolbarFilterBuilder.buildPropertyBasedFilter(
					"resultadoFontar",
					DefaultFilterTypeLibrary.NUMBER_EQUAL,
					ResultadoFontar.APROB_PEND_UFFA.getName(),
					Long.class);			
			
			filters.put("resultadoFontar",filtroItemUffa);
			
		} else if(Constant.FiltroProcedimientoItem.ITEMS_BID.equals(filtroItem)) {
			
			ToolbarQueryFilter filtroItemBid = ConfigurableToolbarFilterBuilder.buildPropertyBasedFilter(
					"resultadoFontar",
					DefaultFilterTypeLibrary.NUMBER_EQUAL,
					ResultadoFontar.APROB_PEND_BID.getName(),
					Long.class);			
			
			filters.put("resultadoFontar",filtroItemBid);
		}
		
		
		form.setFiltersMap(filters);
	}

	@Override
	protected ActionForward forwardInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		return mapping.findForward(SessionHelper.getForwardTiles(request));
	}
	
	@Override
	protected void initInventario(HttpServletRequest request) throws Exception {

		super.initInventario(request);
		
		ProcedimientoBean procedimiento = administrarProcedimientoServicio.obtenerProcedimiento(SessionHelper.getIdProcedimiento(request));
		request.setAttribute("procedimiento", procedimiento);
	}	
}
