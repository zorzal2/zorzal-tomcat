package com.fontar.web.action.evaluacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.domain.dto.VisualizarEvaluacionProyectoDTO;
import com.pragma.util.FormUtil;

public class ConfirmarEvaluacionAction extends EvaluacionBaseTaskAction {

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		request.setAttribute("esProyecto",true);
		request.setAttribute("resumenDePresupuesto", wfEvaluacionServicio.obtenerResumenDePresupuesto(idTaskInstance));		
		super.executeCargarTarea(mapping, form, request, response, messages,
				idTaskInstance);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		String observaciones = FormUtil.getStringValue(form, "observaciones");
		wfEvaluacionServicio.confirmarEvaluacion(observaciones, idTaskInstance);
	}
	
	protected  VisualizarEvaluacionProyectoDTO decorate( VisualizarEvaluacionProyectoDTO dto ){
		return dto;
	}
}
