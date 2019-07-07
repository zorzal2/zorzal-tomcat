package com.fontar.web.action.instrumento.ventanilla.ideaproyecto;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fontar.bus.api.ventanilla.AdministrarIdeaProyectoServicio;
import com.fontar.data.Constant.PreProyectos;
import com.fontar.data.filter.ProyectIdToolbarFilter;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.IdeaProyectoDTOAssembler;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoVisualizarDTO;
import com.fontar.util.SessionHelper;
import com.pragma.toolbar.data.filter.ToolbarQueryFilter;
import com.pragma.toolbar.web.form.ToolbarFiltersForm;
import com.pragma.util.ContextUtil;
import com.pragma.web.action.BaseInventarioAction;

public class EvaluacionesIdeaProyectoInventario extends BaseInventarioAction {
	
	AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio;

	public void setAdministrarIdeaProyectoServicio(AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio) {
		this.administrarIdeaProyectoServicio = administrarIdeaProyectoServicio;
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
		
		//FIXME: REVISAR...
		administrarIdeaProyectoServicio = (AdministrarIdeaProyectoServicio) ContextUtil.getBean("administrarIdeaProyectoService");
		IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO) administrarIdeaProyectoServicio.getIdeaProyectoDTO(idProyecto, new IdeaProyectoCabeceraAssembler());
		
		request.setAttribute(IDEA_PROYECTO, cabeceraDTO);
		request.setAttribute("clase", PreProyectos.IDEA_PROYECTO);

		IdeaProyectoVisualizarDTO ideaProyectoVisualizarDTO = (IdeaProyectoVisualizarDTO) administrarIdeaProyectoServicio.getIdeaProyectoVisualizarDTO(idProyecto, new IdeaProyectoDTOAssembler());
		request.setAttribute("ideaProyectoVisualizar", ideaProyectoVisualizarDTO);	
		
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
