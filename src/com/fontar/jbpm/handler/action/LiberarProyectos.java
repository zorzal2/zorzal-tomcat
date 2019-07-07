package com.fontar.jbpm.handler.action;

import java.util.Iterator;
import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import com.fontar.jbpm.handler.NullVariableException;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.WorkFlowException;

/**
 * Saca a todos los proyectos del paquete del estado en espera EnPaquete
 * @author fferrara
 */

@SuppressWarnings("serial")
public class LiberarProyectos implements ActionHandler {

	public LiberarProyectos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void execute(ExecutionContext arg0) throws Exception {
		List idProyectosPaquete = (List) arg0.getContextInstance().getVariable(JbpmConstants.VariableNames.ID_PROCESO_PROYECTOS);

		if (idProyectosPaquete == null) {
			throw new NullVariableException(JbpmConstants.VariableNames.ID_PROCESO_PROYECTOS, arg0.getProcessDefinition().getName());
		}

		for (Iterator iter = idProyectosPaquete.iterator(); iter.hasNext();) {
			Long idProcesoProyecto = (Long) iter.next();
			ProcessInstance procesoProyecto = arg0.getJbpmContext().loadProcessInstance(idProcesoProyecto);

			Token principal = procesoProyecto.findToken("principal");

			if (principal == null) {
				throw new WorkFlowException("El proceso " + idProcesoProyecto + " no tiene token principal");
			}

			// Chequeo que el token principal este En Paquete
			if (principal.getNode().getName().equals("Controlado")) {
				// pateo el token
				principal.signal();
			}
		}
		arg0.getToken().signal();
	}
}
