package com.fontar.web.action.evaluacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;

public class CargarResultadoSeguimientoAction extends EvaluacionBaseTaskAction {
	
	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		ActionUtil.checkValidEncryptionContext(messages);
	}

	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		Long idEvaluacion = FormUtil.getLongValue(form, "idEvaluacion");
		Long idProyecto = FormUtil.getLongValue(form, "idProyecto");

		EvaluacionGeneralDTO evaluacionDTO = new EvaluacionGeneralDTO();

		evaluacionDTO.setId(idEvaluacion);
		evaluacionDTO.setIdProyecto(idProyecto);
		evaluacionDTO.setFecha(BeanUtils.getProperty(form, "fechaEvaluacion"));
		evaluacionDTO.setFundamentacion(BeanUtils.getProperty(form, "fundamentacion"));

		Boolean acepta = FormUtil.getBooleanValue(form, "aceptaEvaluacion");

		evaluacionDTO.setAceptada(acepta);

		wfEvaluacionServicio.cargarResultadoEvaluacion(evaluacionDTO, acepta, idTaskInstance);
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

//		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		EvaluacionDTO evaluacion = wfEvaluacionServicio.obtenerEvaluacion(idTaskInstance, new VisualizarEvaluacionAssembler());
		request.setAttribute("evaluacion", this.decorate(evaluacion));
		
		//TODO: setear idTipoProyecto y buscar criterios para este tipo
		BeanUtils.setProperty(form, "idEvaluacion", evaluacion.getId());
		BeanUtils.setProperty(form, "idProyecto", evaluacion.getEvaluable().getId());
		// si no vengo de una validacion cargo los datos del FORM
		if (getErrors(request).isEmpty()){
			if (!evaluacion.getEstado().equals(EstadoEvaluacion.PEND_RESULT)) { 
				//FIXMEBeanUtils.setProperty(form, "aceptaEvaluacion", evaluacion.getAceptada().toString());
			}
			BeanUtils.setProperty(form, "fundamentacion", evaluacion.getFundamentacion());
			BeanUtils.setProperty(form, "fechaEvaluacion", evaluacion.getFecha());
		}
	}

	protected EvaluacionDTO decorate(EvaluacionDTO dto ){
		dto.setResumido();
		return dto;
	}
	
}