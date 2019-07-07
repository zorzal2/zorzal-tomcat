package com.fontar.jbpm.handler.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.fontar.bus.api.workflow.WFSeguimientoServicio;
import com.fontar.data.impl.domain.codes.seguimiento.EstadoSeguimiento;
import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

/**
 * Determina la transición a seguir según si
 * la <code>Finalización de Control</code>  
 * <i>acepto</i> o <i>rechazo</i> el <code>Seguimiento</code>.<br>
 * @author gboaglio, ssanchez
 */
public class SeguimientoRechazado implements DecisionHandler {

	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {

		EstadoSeguimiento estado;
		String decide = "";
		
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();
			WFSeguimientoServicio servicio = (WFSeguimientoServicio) ContextUtil.getBean("wfSeguimientoService");

			Long idSeguimiento = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_SEGUIMIENTO);

			if (idSeguimiento == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_SEGUIMIENTO, processName);
			}

			estado = servicio.obtenerEstadoSeguimiento(idSeguimiento);
		} else {
			estado = (EstadoSeguimiento) executionContext.getVariable(JbpmConstants.VariableNames.ESTADO); 
		}
		

		if (estado.equals(EstadoSeguimiento.RECHAZADO)) {
			decide = "rechazado";
		} else {
			decide = "aceptado";
		}

		return decide;
	}
}
