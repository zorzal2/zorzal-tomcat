package com.fontar.jbpm.handler.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.fontar.bus.api.workflow.WFSeguimientoServicio;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

/**
 * Determina si el seguimiento será revaluado
 * según su estado.<br>
 * Si su estado es <i>Gestionado</i> o <i>No Gestionado</i> 
 * sale por <i>No</i>. Si el estado es <i>Evaluacion</i>
 * sale por <i>Si</i>.<br>
 * @author ssanchez
 */
public class RevaluarSeguimiento implements DecisionHandler {

	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {

		EstadoSeguimiento estado;
		String decide = "";
		
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();
			WFSeguimientoServicio wfSeguimientoServicio = (WFSeguimientoServicio) ContextUtil.getBean("wfSeguimientoService");

			Long idSeguimiento = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_SEGUIMIENTO);

			if (idSeguimiento == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_SEGUIMIENTO, processName);
			}

			estado = wfSeguimientoServicio.obtenerEstadoSeguimiento(idSeguimiento);
		} else {
			estado = (EstadoSeguimiento) executionContext.getVariable(JbpmConstants.VariableNames.ESTADO); 
		}
		

		if (EstadoSeguimiento.EVALUACION.equals(estado)) {
			decide = "SI";
		} else {
			decide = "NO";
		}

		return decide;
	}
}
