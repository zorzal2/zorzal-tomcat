package com.fontar.web.action.seguimientos.evaluaciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.fontar.bus.api.seguimientos.seguimientos.AnalisisGastosSeguimientoServicio;
import com.fontar.bus.api.workflow.WFEvaluacionServicio;
import com.fontar.bus.impl.seguimientos.seguimientos.ResumenRendicionesPorProyecto;
import com.fontar.data.Constant.CabeceraAttribute;
import com.fontar.data.impl.assembler.VisualizarEvaluacionAssembler;
import com.fontar.data.impl.domain.dto.EvaluacionGeneralDTO;
import com.fontar.data.impl.domain.dto.EvaluacionSeguimientoCabeceraDTO;
import com.fontar.data.impl.domain.dto.ResumenDeRendicionCompactoDTO;
import com.fontar.data.impl.domain.dto.VisualizarEvaluacionProyectoDTO;
import com.fontar.jbpm.manager.EvaluacionTaskInstanceManager;
import com.fontar.web.util.ActionUtil;
import com.pragma.util.FormUtil;
import com.pragma.web.action.GenericJbpmTaskAction;

public class ConfirmarEvaluacionSeguimientoAction extends GenericJbpmTaskAction {

	protected WFEvaluacionServicio wfEvaluacionServicio;

	protected AnalisisGastosSeguimientoServicio gastosSeguimientoServicio;
	
	
	@Override
	protected void executeEjecutarYTerminarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		String observaciones = FormUtil.getStringValue(form, "observaciones");
		wfEvaluacionServicio.confirmarEvaluacion(observaciones, idTaskInstance);
	}
	
	@Override
	protected void executeCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {

		EvaluacionGeneralDTO evaluacionGeneralDTO = wfEvaluacionServicio.obtenerEvaluacionGeneral(idTaskInstance);
	
		request.setAttribute("evaluacion", evaluacionGeneralDTO);
		
		ResumenDeRendicionCompactoDTO resumenDeRendiciones = wfEvaluacionServicio.obtenerTotalesRendicionesParaConfirmarEvaluacionDeSeguimiento(idTaskInstance);
		request.setAttribute("rendiciones", resumenDeRendiciones);
	
		EvaluacionSeguimientoCabeceraDTO cabeceraDTO = wfEvaluacionServicio.obtenerCabeceraEvaluacionSeguimiento(idTaskInstance);
		request.setAttribute(CabeceraAttribute.EVALUACION_SEGUIMIENTO, cabeceraDTO);
		VisualizarEvaluacionProyectoDTO evaluacion = (VisualizarEvaluacionProyectoDTO) wfEvaluacionServicio.obtenerEvaluacion(idTaskInstance, new VisualizarEvaluacionAssembler());
		request.setAttribute("evaluacion", this.decorate(evaluacion));
		
		// Guardo estas propiedades ya q las necesito luego
		BeanUtils.setProperty(form, "idEvaluacion", evaluacion.getId());
		BeanUtils.setProperty(form, "idProyecto", evaluacion.getEvaluable().getId());

	}

	protected  VisualizarEvaluacionProyectoDTO decorate( VisualizarEvaluacionProyectoDTO dto ){
		return dto;
	}

	public WFEvaluacionServicio getWfEvaluacionServicio() {
		return wfEvaluacionServicio;
	}

	public void setWfEvaluacionServicio(WFEvaluacionServicio wfEvaluacionServicio) {
		this.wfEvaluacionServicio = wfEvaluacionServicio;
	}
	
	
	
	
	public AnalisisGastosSeguimientoServicio getGastosSeguimientoServicio() {
		return gastosSeguimientoServicio;
	}

	public void setGastosSeguimientoServicio(AnalisisGastosSeguimientoServicio gastosSeguimientoServicio) {
		this.gastosSeguimientoServicio = gastosSeguimientoServicio;
	}

	@Override
	protected void validateCargarTarea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages, Long idTaskInstance) throws Exception {
		
		ActionUtil.checkValidEncryptionContext(messages);
		if(messages.isEmpty()){
			EvaluacionTaskInstanceManager taskHelper = new EvaluacionTaskInstanceManager(idTaskInstance);
			ResumenRendicionesPorProyecto resumen = this.gastosSeguimientoServicio.rendicionCuentasAnalisisGastosSeguimiento(taskHelper.getIdEvaluacion());
			
			if( !resumen.validarTotalRendiciones())
				addError(messages,"app.error.totalAprobado");
			
			if( !resumen.validarTotalRendicionesSeguimientoActual())
				addError(messages,"app.error.totalRendido");
			
		}
	}
}
