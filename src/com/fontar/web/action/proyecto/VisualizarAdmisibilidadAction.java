package com.fontar.web.action.proyecto;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.proyecto.AdmisibilidadProyectoServicio;
import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.impl.domain.dto.AdmisibilidadVisualizarDTO;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.SessionHelper;
import com.pragma.util.ContextUtil;
import com.pragma.web.action.BaseMappingDispatchAction;

public class VisualizarAdmisibilidadAction extends BaseMappingDispatchAction {

	private AdmisibilidadProyectoServicio admisibilidadProyectoServicio;
	
	public void setAdmisibilidadProyectoServicio(AdmisibilidadProyectoServicio admisibilidadProyectoServicio) {
		this.admisibilidadProyectoServicio = admisibilidadProyectoServicio;
	}

	public ActionForward visualizar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idProyecto = null;
		
		
		Long id = new Long(request.getParameter("id"));
		idProyecto = SessionHelper.getIdProyecto(request);
		
		WFProyectoServicio wfProyectoServicio = (WFProyectoServicio) ContextUtil.getBean("wfProyectoService");
		
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		
		AdmisibilidadVisualizarDTO admisibilidadVisualizarDTO = admisibilidadProyectoServicio.getVisualizacionAdmisibilidad(id);
		
		request.setAttribute("admisibilidad", admisibilidadVisualizarDTO);
		 
		return mapping.findForward(FORWARD_SUCCESS);
	}

	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long idProyecto = null;
		
		
		Long id = new Long(request.getParameter("id"));
		idProyecto = SessionHelper.getIdProyecto(request);
		
		WFProyectoServicio wfProyectoServicio = (WFProyectoServicio) ContextUtil.getBean("wfProyectoService");
		
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		
		AdmisibilidadVisualizarDTO admisibilidadVisualizarDTO = admisibilidadProyectoServicio.getVisualizacionAdmisibilidad(id);
		
		request.setAttribute("admisibilidad", admisibilidadVisualizarDTO);
		 
		return mapping.findForward(FORWARD_SUCCESS);
	}

}