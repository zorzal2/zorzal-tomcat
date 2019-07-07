package com.fontar.web.action.bitacora;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.proyecto.AdministrarProyectoServicio;
import com.fontar.bus.api.ventanilla.AdministrarIdeaProyectoServicio;
import com.fontar.data.Constant.PreProyectos;
import com.fontar.data.filter.ProyectIdToolbarFilter;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.IdeaProyectoDTOAssembler;
import com.fontar.data.impl.domain.codes.bitacora.TipoBitacora;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoVisualizarDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.SessionHelper;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.util.ContextUtil;
import com.pragma.web.action.BaseInventarioAction;

public class BitacoraInventarioAction extends BaseInventarioAction {

		
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
		String claseProyecto = getClaseProyecto(request);
		if (claseProyecto.equals("P")){
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
		
		if (claseProyecto.equals("IP")){
			//cargo datos de cabecera
			AdministrarIdeaProyectoServicio  administrarIdeaProyectoServicio = (AdministrarIdeaProyectoServicio) ContextUtil.getBean("administrarIdeaProyectoService");
			IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO) administrarIdeaProyectoServicio.getIdeaProyectoDTO(idProyecto, new IdeaProyectoCabeceraAssembler());
			
			request.setAttribute(IDEA_PROYECTO, cabeceraDTO);
			request.setAttribute("clase", PreProyectos.IDEA_PROYECTO);

			IdeaProyectoVisualizarDTO ideaProyectoVisualizarDTO = (IdeaProyectoVisualizarDTO) administrarIdeaProyectoServicio.getIdeaProyectoVisualizarDTO(idProyecto, new IdeaProyectoDTOAssembler());
			request.setAttribute("ideaProyectoVisualizar", ideaProyectoVisualizarDTO);	
		} 
		
		
		request.setAttribute("claseBitacora",claseProyecto);
		/* Filtros bitacora */
		request.setAttribute("tiposBitacora",collectionHandler.getComboFiltro(TipoBitacora.class));
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
	
		
	@Override
	protected ActionForward forwardFiltrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String claseProyecto = getClaseProyecto(request);
		if (claseProyecto.equals("P")) {
			return mapping.findForward("success_p");
		}
		if (claseProyecto.equals("IP")) {
			return mapping.findForward("success_ip");
		}
		return super.forwardFiltrar(mapping, form, request, response);
	}

	@Override
	protected ActionForward forwardInventario(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String claseProyecto = getClaseProyecto(request);
		if (claseProyecto.equals("P")) {
			return mapping.findForward("success_p");
		}
		if (claseProyecto.equals("IP")) {
			return mapping.findForward("success_ip");
		}
		return super.forwardInventario(mapping, form, request, response);
	}

	private String getClaseProyecto(HttpServletRequest request){
		String claseProyecto = null;
		//veo si viene por parametro 
		if (validateParameter(request,"clase")){
			claseProyecto = request.getParameter("clase");
			SessionHelper.setClaseProyecto(request,claseProyecto);
		} else {
			claseProyecto = SessionHelper.getClaseProyecto(request);
		}
		return claseProyecto;
	}
}
