package com.fontar.web.action.instrumento.ventanilla.ideaproyecto;

import static com.fontar.data.Constant.CabeceraAttribute.IDEA_PROYECTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.ventanilla.AdministrarIdeaProyectoServicio;
import com.fontar.data.Constant.PreProyectos;
import com.fontar.data.impl.assembler.IdeaProyectoCabeceraAssembler;
import com.fontar.data.impl.domain.dto.IdeaProyectoCabeceraDTO;
import com.fontar.util.SessionHelper;
import com.fontar.web.action.adjuntos.AdjuntoAction;
import com.fontar.web.form.AdjuntoUploadForm;

public class IdeaProyectoAdjuntoAction extends AdjuntoAction {

	private AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio;

	public void setAdministrarIdeaProyectoServicio(AdministrarIdeaProyectoServicio administrarIdeaProyectoServicio) {
		this.administrarIdeaProyectoServicio = administrarIdeaProyectoServicio;
	}

	@Override
	public void setHeader(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long idProyecto = null;

		if (validateParameter(request, "id")) {
			idProyecto = new Long(request.getParameter("id"));
			SessionHelper.setIdProyecto(request,idProyecto);
		}
		else {
			idProyecto = SessionHelper.getIdProyecto(request);
		}
		
		IdeaProyectoCabeceraDTO cabeceraDTO = (IdeaProyectoCabeceraDTO) administrarIdeaProyectoServicio.getIdeaProyectoDTO(idProyecto, new IdeaProyectoCabeceraAssembler());
		
		request.setAttribute(IDEA_PROYECTO, cabeceraDTO);
		request.setAttribute("clase", PreProyectos.IDEA_PROYECTO);
	}

	@Override
	public Long getId(AdjuntoUploadForm form, HttpServletRequest request) {
		return SessionHelper.getIdProyecto(request);
	}
}
