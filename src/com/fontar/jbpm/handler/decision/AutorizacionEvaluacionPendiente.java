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
public class AutorizacionEvaluacionPendiente implements DecisionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {
		
		EstadoEvaluacion estado;
		String decide = "";
		
		//FF : parche para ver si me llaman de un test
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();
			WFEvaluacionServicio servicio = (WFEvaluacionServicio) ContextUtil.getBean("wfEvaluacionService");
	
			Long idEvaluacion = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_EVALUACION);
	
			if (idEvaluacion == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_EVALUACION, processName);
			}
	
			estado = servicio.obtenerEstadoEvaluacion(idEvaluacion);
		} else {
			estado = (EstadoEvaluacion)executionContext.getVariable(JbpmConstants.VariableNames.ESTADO);
		}
		
		if (estado.equals(EstadoEvaluacion.PEND_RESULT)) {
			decide = "NO";
		}
		else {
			decide = "SI";
		}
		return decide;
	}

}
