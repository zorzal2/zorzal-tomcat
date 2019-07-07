package com.fontar.web.action.seguimientos.evaluaciones.rendiciones;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.evaluacion.EvaluarProyectoServicio;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.fontar.util.SessionHelper;
import com.pragma.toolbar.data.filter.ConfigurableToolbarFilterBuilder;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.properties.DefaultFilterTypeLibrary;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.web.action.BaseInventarioAction;

/**
 * Inventario de las <code>Rendición de Cuentas de 
 * Evaluación Seguimiento</code>.<br>
 * Permite modificar los montos aprobados 
 * (de evaluación) de una rendición.<br>
 * @author ssanchez
 */
public class RendicionEvaluacionSeguimientoInventarioAction extends BaseInventarioAction {

	private EvaluarProyectoServicio evaluarProyectoServicio;
	
	public void setEvaluarProyectoServicio(EvaluarProyectoServicio evaluarProyectoServicio) {
		this.evaluarProyectoServicio = evaluarProyectoServicio;
	}


	public Long getIdEvaluacion(HttpServletRequest request) {
		return SessionHelper.getIdEvaluacion(request);
	}	
	protected Long getIdSeguimiento(HttpServletRequest request){
		return SessionHelper.getIdSeguimiento(request);
	}	

	@Override
	protected void initInventario(HttpServletRequest request) {		
		
		Long idEvaluacion = getIdEvaluacion(request);
		EvaluacionSeguimientoCabeceraDTO cabeceraDTO = evaluarProyectoServicio.obtenerCabeceraEvaluacionSeguimiento(idEvaluacion);	

		request.setAttribute(CabeceraAttribute.EVALUACION_SEGUIMIENTO, cabeceraDTO);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void addCustomFilters(HttpServletRequest request, ToolbarFiltersForm form) {
		
		Long idSeguimiento = SessionHelper.getIdSeguimiento(request);		
		
		ToolbarQueryFilter filtroSeguimiento = ConfigurableToolbarFilterBuilder.buildPropertyBasedFilter(
				"idSeguimiento",
				DefaultFilterTypeLibrary.NUMBER_EQUAL,
				idSeguimiento,
				java.lang.Long.class
		);
		
		
		Map filters = form.getFiltersMap();
		filters.put("idSeguimiento",filtroSeguimiento);
		
		form.setFiltersMap(filters);
	}	
}
