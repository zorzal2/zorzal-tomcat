package com.fontar.web.action.proyecto.pac;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.proyecto.pac.AdministrarPACServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.bus.impl.misc.CollectionHandler;
import com.fontar.data.api.dao.PacDAO;
import com.fontar.data.api.dao.ProyectoDAO;
import com.fontar.data.filter.ProyectIdToolbarFilter;
import com.fontar.data.impl.domain.bean.PacBean;
import com.fontar.data.impl.domain.bean.ProyectoBean;
import com.fontar.data.impl.domain.bean.ProyectoPresupuestoBean;
import com.fontar.data.impl.domain.codes.proyecto.pac.EstadoPacItem;
import com.fontar.data.impl.domain.dto.CuadroResumenPacDTO;
import com.fontar.data.impl.domain.dto.MonedaDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.ResourceManager;
import com.fontar.util.SessionHelper;
import com.fontar.util.Util;
import com.fontar.web.action.IventarioConEstadoAnuladoAction;
import com.fontar.web.util.ActionUtil;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.util.ContextUtil;
import com.pragma.util.StringUtil;

/**
 * Action del inventario <code>Toolbar</code> de <code>Notificaciones</code>.<br> 
 * @author ssanchez
 * @version 1.10, 22/03/07
 */
public class ProyectoPacInventarioAction extends IventarioConEstadoAnuladoAction {

	AdministrarPACServicio administrarPACServicio;

	protected WFProyectoServicio wfProyectoServicio;

	@SuppressWarnings("unchecked")
	@Override
	protected void addCustomFilters(HttpServletRequest request, ToolbarFiltersForm form) {

		super.addCustomFilters(request, form);
		
		Long idProyecto = SessionHelper.getIdProyecto(request);

		ToolbarQueryFilter filtroProyecto = new ProyectIdToolbarFilter(idProyecto);
				
		Map filters = form.getFiltersMap();
		filters.put("idProyecto",filtroProyecto);		
		form.setFiltersMap(filters);
	}

	@Override
	/**
	 * Inicialización del inventario
	 */
	protected void initInventario(HttpServletRequest request) {
		Long idProyecto = null;

		idProyecto = SessionHelper.getIdProyecto(request);

		if ((!Util.isBlank(request.getParameter("id"))) && (!Util.isBlank(request.getParameter("patrimonio")))) {
			administrarPACServicio.updatePatrimonio(request.getParameter("id"));
		}
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		request.setAttribute("ES_ADQUISICION", visualizacionProyectoDto.getPermiteAdquisicion());
		request.setAttribute("tipoInstrumento", visualizacionProyectoDto.getTipoInstrumentoDef());
		if (ActionUtil.isEncryptionContextAvailable()) {
	
			request.setAttribute("presupuesto",null);
			ProyectoDAO proyectoDAO = (ProyectoDAO) ContextUtil.getBean("proyectoDao");
			ProyectoBean bean = proyectoDAO.read(idProyecto);
			ProyectoPresupuestoBean presupuesto = bean.getProyectoPresupuesto();
			boolean tienePresupuesto = presupuesto!=null;
			boolean tieneEtapas = tienePresupuesto && presupuesto.getEtapas().size()>0;
	
			if (tieneEtapas) {
				request.setAttribute("presupuesto","si");
			}
			
			/*
			 * Chequeo si se puede permitir la carga desde excel
			 *  - Existe un presupuesto
			 *  - El presupuesto tiene plan de trabajo no vacio.
			 *  - Todos los items de PAC estan en estado Anulado o
			 *    Pendientes de compra
			 */
			boolean permitirCargaDesdeExcel = tieneEtapas;
			
			CuadroResumenPacDTO cuadroDTO = administrarPACServicio.obtenerDatosCuadroResumen(idProyecto,bean);
			request.setAttribute("pac", cuadroDTO);
			if (cuadroDTO.getEsDistintoZero()) {
				request.setAttribute("esDistintoZero", "distinto");
			}
			
			//Mensaje de error indicando que faltan monedas
			if(cuadroDTO.getMonedasSinCotizacion().isEmpty()) {
				request.setAttribute("monedasSinCotizacionError", null);
			} else {
				Collection<MonedaDTO> monedasSinCotizacion = cuadroDTO.getMonedasSinCotizacion();
				ArrayList<String> monedasSinCotizacionDesc = new ArrayList<String>();
				for(MonedaDTO moneda : monedasSinCotizacion) monedasSinCotizacionDesc.add(moneda.getDescripcion());
				String errorText;
				if(cuadroDTO.getMonedasSinCotizacion().size()==1) {
					errorText = ResourceManager.getErrorResource(
							"app.pac.noHayCotizacionDeUnaMoneda", 
							monedasSinCotizacionDesc.get(0));
				} else {
					errorText = ResourceManager.getErrorResource(
							"app.pac.noHayCotizacionDeVariasMonedas", 
							StringUtil.join(monedasSinCotizacionDesc, ", ")
						);
				}
				request.setAttribute("monedasSinCotizacionError", errorText);
			}
			
			PacDAO pacDAO = (PacDAO) ContextUtil.getBean("pacDao");
			List<PacBean> pacs = pacDAO.findByProyecto(idProyecto);
			for(PacBean pac : pacs) {
				permitirCargaDesdeExcel =
					permitirCargaDesdeExcel && 
					(pac.getCodigoEstado().equals(EstadoPacItem.ANULADO) ||
					pac.getCodigoEstado().equals(EstadoPacItem.PENDIENTE_DE_COMPRA));
			}
			
			request.setAttribute("permitirCargaDesdeExcel", permitirCargaDesdeExcel);
			/* Fin del chequeo de carga desde excel*/
			
	//		setHeaderData(request, visualizacionProyectoDto.getDatosProyectoDto());
			setInitValues(request);
		}
		else {
			request.setAttribute("bundle", "errors");
			request.setAttribute("display", "app.error.encrypt");
		}
		
	}

	/**
	 * Cargo los datos necesitados por los combobox usados en los filtros
	 */
	/**
	 * Cargo los datos necesitados por los combobox usados en los filtros
	 */
	private void setInitValues(HttpServletRequest request) {
		CollectionHandler handler = new CollectionHandler();

		//ArrayList estados = (ArrayList) handler.getComboFiltro(EstadoPacItem.class);
		ArrayList estados = (ArrayList) handler.getComboFiltroConEmptyLabelAll(EstadoPacItem.class);
		request.setAttribute("estados", estados);

		ArrayList rubros = (ArrayList) handler.getRubrosNombre();
		request.setAttribute("rubros", rubros);

		ArrayList adquisiciones = (ArrayList) handler.getAdquisicionesDescripcion();
		request.setAttribute("adquisiciones", adquisiciones);

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
	
	protected String getPropertyName(){
		return "codigoEstado";
	}

}
