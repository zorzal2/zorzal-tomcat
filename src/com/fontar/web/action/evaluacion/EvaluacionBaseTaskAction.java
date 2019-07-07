package com.fontar.web.action.evaluacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.workflow.WFEvaluacionServicio;
import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.dto.VisualizarEvaluacionProyectoDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

/**
 * Clase base para las tareas de evaluacion
 * @author fferrara
 * 
 */
public class EvaluacionBaseTaskAction extends GenericJbpmTaskAction {

	protected WFEvaluacionServicio wfEvaluacionServicio;

	public void setWfEvaluacionServicio(WFEvaluacionServicio wfEvaluacionServicio) {
		this.wfEvaluacionServicio = wfEvaluacionServicio;
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		VisualizarEvaluacionProyectoDTO evaluacion = (VisualizarEvaluacionProyectoDTO) wfEvaluacionServicio.obtenerEvaluacion(idTaskInstance, new VisualizarEvaluacionAssembler());
		request.setAttribute("evaluacion", this.decorate(evaluacion));
		
		// Guardo estas propiedades ya q las necesito luego
		BeanUtils.setProperty(form, "idEvaluacion", evaluacion.getId());
		BeanUtils.setProperty(form, "idProyecto", evaluacion.getEvaluable().getId());

	}

	protected VisualizarEvaluacionProyectoDTO decorate( VisualizarEvaluacionProyectoDTO dto ){
		dto.setResumido();
		return dto;
	}
	
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}
}
