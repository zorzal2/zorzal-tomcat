package com.fontar.jbpm.handler.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.fontar.bus.api.workflow.WFProyectoServicio;
import com.fontar.data.impl.domain.codes.proyecto.EstadoProyecto;
import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

public class ResultadoAnalisisRecomendacion implements DecisionHandler {

	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {

		String decide = "";
		
		EstadoProyecto estado; 
		
		// FF : parche para ver si me llaman de un test
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();
			WFProyectoServicio servicio = (WFProyectoServicio) ContextUtil.getBean("wfProyectoService");
	
			// TODO: FF / consultar servicio o variable si viene de test de unidad
			Long idProyecto = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_PROYECTO);
	
			if (idProyecto == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_PROYECTO, processName);
			}
	
			estado = servicio.obtenerEstadoProyecto(idProyecto);
		} else{
			estado = (EstadoProyecto) executionContext.getVariable(JbpmConstants.VariableNames.ESTADO);
		}
		
		// check de decision
		if (estado.equals(EstadoProyecto.CERRADO)) {
			decide = "cerrado";
		} else {

			if (estado.equals(EstadoProyecto.RECON)) {
				decide = "autorizado";
			} else {
				decide = "no_autorizado";	
			}
		}
		
		// TODO: no cambia de estado a
		return decide;
	}
}