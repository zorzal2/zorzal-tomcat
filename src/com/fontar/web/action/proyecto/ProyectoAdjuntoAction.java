package com.fontar.web.action.proyecto;

import static com.fontar.data.Constant.CabeceraAttribute.PROYECTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.impl.domain.dto.ProyectoVisualizacionDTO;
import com.fontar.util.SessionHelper;
import com.fontar.web.action.adjuntos.AdjuntoAction;
import com.fontar.web.form.AdjuntoUploadForm;

public class ProyectoAdjuntoAction extends AdjuntoAction {

	private WFProyectoServicio wfProyectoServicio;
	
	public void setWfProyectoServicio(WFProyectoServicio wfProyectoServicio) {
		this.wfProyectoServicio = wfProyectoServicio;
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
		
		ProyectoVisualizacionDTO visualizacionProyectoDto = wfProyectoServicio.obtenerDatosVisualizacionProyecto(idProyecto);
		request.setAttribute(PROYECTO, visualizacionProyectoDto);
		request.setAttribute("ES_ADQUISICION", visualizacionProyectoDto.getPermiteAdquisicion());
		request.setAttribute("tipoInstrumento", visualizacionProyectoDto.getTipoInstrumentoDef());
	}

	@Override
	public Long getId(AdjuntoUploadForm form, HttpServletRequest request) {
		return SessionHelper.getIdProyecto(request);
	}
}
