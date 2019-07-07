package com.fontar.jbpm.handler.action;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.fontar.bus.api.ventanilla.IdeaProyectoService;
import com.fontar.data.impl.domain.codes.ideaProyecto.EstadoIdeaProyecto;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.util.ContextUtil;

/**
 * Cambia el estado de una idea proyecto a PENDIENTE
 * 
 * @author llobeto
 */
public class PasarAPendienteTask implements ActionHandler {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void execute(ExecutionContext context) throws Exception {

		Long idIdeaProyecto = (Long)context.getContextInstance().getVariable(JbpmConstants.VariableNames.ID_PROYECTO);
		
		IdeaProyectoService servicio = (IdeaProyectoService) ContextUtil.getBean("ideaProyectoService");
		
		servicio.updateEstado(idIdeaProyecto, EstadoIdeaProyecto.PENDIENTE);
		
		context.getToken().signal();
	}
}
