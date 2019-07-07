package com.fontar.jbpm.handler.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.fontar.bus.api.workflow.WFEvaluacionServicio;
import com.fontar.data.impl.domain.codes.evaluacion.EstadoEvaluacion;
import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

/**
 * 
 * @author gboaglio
 * 
 */
public class AutorizacionEvaluacion implements DecisionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {
		
		String decide = "";
		EstadoEvaluacion estado;
		
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();
			WFEvaluacionServicio servicio = (WFEvaluacionServicio) ContextUtil.getBean("wfEvaluacionService");
	
			// TODO: FF / consultar servicio o variable si viene de test de unidad
			Long idEvaluacion = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_EVALUACION);
	
			if (idEvaluacion == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_EVALUACION, processName);
			}
			estado = servicio.obtenerEstadoEvaluacion(idEvaluacion);
		} else {
			estado = (EstadoEvaluacion)executionContext.getVariable(JbpmConstants.VariableNames.ESTADO);
		}
		
		if (estado.equals(EstadoEvaluacion.PEND_RESULT)) {
			decide = "SI";
		}

		if (estado.equals(EstadoEvaluacion.NO_AUTORIZADA)) {
			decide = "NO";
		}

		return decide;
	}

}
