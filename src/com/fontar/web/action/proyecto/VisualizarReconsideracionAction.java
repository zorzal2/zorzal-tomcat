package com.fontar.web.action.proyecto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.fontar.bus.api.proyecto.ProyectoReconsideracionService;
import com.fontar.data.impl.domain.dto.ProyectoReconsideracionDTO;

public class VisualizarReconsideracionAction extends MappingDispatchAction {

	private ProyectoReconsideracionService proyectoReconsideracionService;

	public void setProyectoReconsideracionService(ProyectoReconsideracionService proyectoReconsideracionService) {
		this.proyectoReconsideracionService = proyectoReconsideracionService;
	}
	public ActionForward visualizar(
			ActionMapping mapping, 
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response
		) throws Exception {
		
		Long id = new Long(request.getParameter("id"));
		
		ProyectoReconsideracionDTO reconsideracionDTO = proyectoReconsideracionService.load(id);

		request.setAttribute("reconsideracion", reconsideracionDTO);
		request.setAttribute("proyecto", proyectoReconsideracionService.getProyectoCabecera(id));
		
		return mapping.findForward("success");
	}
}
