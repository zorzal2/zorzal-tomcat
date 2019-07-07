package com.fontar.jbpm.handler.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.fontar.bus.api.workflow.WFIdeaProyectoServicio;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

/**
 * Verifica si una <code>IdeaProyecto</code> esta aprobada o no para ser un proyecto.
 * <br>Usada en el workflow de Idea Proyecto.<br> 
 * 
 * @author ssanchez
 * @version 1.10, 13/03/07
 */
public class Elegibilidad implements DecisionHandler {

	private static final long serialVersionUID = 1L;

	public String decide(ExecutionContext executionContext) throws Exception {

		String decide = "";
		EstadoIdeaProyecto estado; 
		
		if (ContextUtil.hasWebContext()){
			String processName = executionContext.getProcessDefinition().getName();
			WFIdeaProyectoServicio servicio = (WFIdeaProyectoServicio) ContextUtil.getBean("wfIdeaProyectoService");
	
			Long idIdeaProyecto = (Long) executionContext.getVariable(JbpmConstants.VariableNames.ID_PROYECTO);
	
			if (idIdeaProyecto == null) {
				throw new NullVariableException(JbpmConstants.VariableNames.ID_PROYECTO, processName);
			}
	
			estado = servicio.obtenerEstadoIdeaProyecto(idIdeaProyecto);
		} else{
			estado = (EstadoIdeaProyecto) executionContext.getVariable(JbpmConstants.VariableNames.ESTADO);
		}

		// check de decision
		switch(estado) {
		case ELEGIBLE:
			decide = "SI";
			break;
		case NO_ELEGIBLE:
			decide = "NO";
			break;
		case PENDIENTE:
			decide = "pendiente";
		}
		return decide;
	}

}
