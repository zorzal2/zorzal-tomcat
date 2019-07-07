package com.fontar.web.action.instrumento.ventanilla.ideaproyecto;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.ventanilla.AdministrarIdeaProyectoServicio;
import com.fontar.data.Constant.PreProyectos;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.assembler.IdeaProyectoDTOAssembler;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.data.impl.domain.dto.IdeaProyectoVisualizarDTO;
import com.fontar.util.SessionHelper;
import com.pragma.web.action.BaseMappingDispatchAction;

/**
 * 
 * @author gboaglio, ssanchez
 * @version 1.01, 21/12/06
 */

public class VisualizarIdeaProyectoAction extends BaseMappingDispatchAction {

	
	private AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio;

	public void setAdministrarIdeaProyectoServicio(AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio) {
		this.administrarIdeaProyectoServicio = administrarIdeaProyectoServicio;
	}

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idProyecto = null;
		
		if (validateParameter(request, "id")) {
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		}
		else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		
		//cargo datos de cabecera
		IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO) administrarIdeaProyectoServicio.getIdeaProyectoDTO(idProyecto, new IdeaProyectoCabeceraAssembler());
		
		request.setAttribute(IDEA_PROYECTO, cabeceraDTO);
		request.setAttribute("clase", PreProyectos.IDEA_PROYECTO);

		IdeaProyectoVisualizarDTO ideaProyectoVisualizarDTO = (IdeaProyectoVisualizarDTO) administrarIdeaProyectoServicio.getIdeaProyectoVisualizarDTO(idProyecto, new IdeaProyectoDTOAssembler());
		request.setAttribute("ideaProyectoVisualizar", ideaProyectoVisualizarDTO);
		
		return mapping.findForward(FORWARD_SUCCESS);
	}
}
