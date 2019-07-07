package com.fontar.web.action.evaluacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.fontar.bus.api.evaluacion.AdministrarEvaluacionesServicio;
import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.web.action.adjuntos.AdjuntoAction;
import com.fontar.web.form.AdjuntoUploadForm;

public class EvaluacionAdjuntoAction extends AdjuntoAction {

	private AdministrarEvaluacionesServicio administrarEvaluacionesServicio;
	
	private static final String EVALUACION_ATTRIBUTE = "evaluacion";

	public void setAdministrarEvaluacionesServicio(AdministrarEvaluacionesServicio administrarEvaluacionesServicio) {
		this.administrarEvaluacionesServicio = administrarEvaluacionesServicio;
	}

	@Override
	public void setHeader(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Long id = getId((AdjuntoUploadForm)form,request);
		
		EvaluacionDTO dto = administrarEvaluacionesServicio.getEvaluacionDTO(Long.valueOf(id), new VisualizarEvaluacionAssembler());
		request.setAttribute(EVALUACION_ATTRIBUTE, dto);
	}
}
