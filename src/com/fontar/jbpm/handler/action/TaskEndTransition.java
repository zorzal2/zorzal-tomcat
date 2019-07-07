package com.fontar.jbpm.handler.action;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Se utiliza para especificar con la finalización de una tarea por que
 * transición del nodo sale el token.
 * 
 * IMPORTANTE: Cuando se utiliza en task node que tiene más de una tarea puede
 * pasar que las otras tareas del nodo queden abiertas si el nodo está marcado
 * con signal="first" Puede complementarse con la acción CancelOpenedTask en el
 * evento node-leave
 * 
 * @author fferrara
 * 
 */
public class TaskEndTransition implements ActionHandler {

	private static final long serialVersionUID = 6627506796871189729L;

	String transitionName;

	public void execute(ExecutionContext arg0) throws Exception {

		TaskInstance taskInstance = arg0.getTaskInstance();

		// me fijo si la tarea patea el token
		if (taskInstance.isSignalling()) {
			// si cancelan me voy por cancel.
			if (taskInstance.isCancelled()) {

				// TODO: FF / Tirar exception cuando no este la transición
				// cancel.
				// Tambien puede chequearse que exista una transición que vaya
				// al join
				// arg0.getNode().getLeavingTransition("cancel");

				arg0.leaveNode("cancel");
			}
			else {
				// me dicen a donde ir?
				if (transitionName != null) {
					arg0.leaveNode(transitionName);
				}
				else {
					arg0.leaveNode();
				}
			}
		}

	}

}
