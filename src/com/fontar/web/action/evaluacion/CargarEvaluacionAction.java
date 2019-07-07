package com.fontar.web.action.evaluacion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.data.impl.domain.codes.evaluacion.TipoEvaluacion;
import com.fontar.data.impl.domain.dto.EvaluacionDTO;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.VisualizarEvaluacionGeneralDecorator;
import com.fontar.util.SessionHelper;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;
import com.pragma.util.StringUtil;

public class CargarEvaluacionAction extends EvaluacionBaseTaskAction {
	
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
		String proyectoTipo = BeanUtils.getProperty(form, "idTipoProyecto");
		String proyectoTipoCriterio = (String) SessionHelper.getNombreCriterios(request);
		
		Object idCriterios = null;
		if ((proyectoTipo.equals(proyectoTipoCriterio) && (!StringUtil.isEmpty(proyectoTipoCriterio)))) {
			idCriterios = SessionHelper.getIdCriterios(request);
		}
			
		wfEvaluacionServicio.cargarCriterioEvaluacion(idProyecto,idEvaluacion,proyectoTipo,idCriterios);
		wfEvaluacionServicio.cargarResultadoEvaluacion(evaluacionDTO, acepta, idTaskInstance);
	}

	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

//		super.executeCargarTarea(mapping, form, request, response, messages, idTaskInstance);
		EvaluacionDTO evaluacion = wfEvaluacionServicio.obtenerEvaluacion(idTaskInstance, new VisualizarEvaluacionAssembler());
		request.setAttribute("evaluacion", this.decorate(evaluacion));
		
		//TODO: buscar mejor camino para saber si es tecnico o no
		validarCriterios(mapping,form,request,response,evaluacion);
		
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
	
	protected void setCriterios(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response,EvaluacionDTO evaluacion){
		
	}
	
	protected void validarCriterios(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, EvaluacionDTO evaluacion)  throws Exception {

		//	TODO: buscar mejor camino para saber si es tecnico o no
		if (evaluacion.getTipo().getName().equals(TipoEvaluacion.EVAL_GEN.name())) {
			VisualizarEvaluacionGeneralDecorator dto = (VisualizarEvaluacionGeneralDecorator) evaluacion;
			if (dto.getEsTecnica().equals("true")) {
				if (!dto.getProyecto().toString().contains("Idea")) {
					setCriterios(mapping,form,request,response,evaluacion);
					if (getErrors(request).isEmpty()){
						String[] idCriterios = wfEvaluacionServicio.obtenerCriterioEvaluacion(evaluacion.getId());
						SessionHelper.setIdCriterios(request, idCriterios);
						String idTipoProyecto = wfEvaluacionServicio.obtenerTipoProyecto(evaluacion.getEvaluable().getId());
						BeanUtils.setProperty(form, "idTipoProyecto", idTipoProyecto);
						SessionHelper.setNombreCriterios(request,idTipoProyecto);		
					}
				}
			}
		}
	}
}