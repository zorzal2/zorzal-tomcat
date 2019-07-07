package com.fontar.jbpm.handler.action;

import java.util.Collection;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

/**
 * Se utiliza para especificar con la finalización de una tarea por que
 * transición del nodo sale el token.
 * Esta implementacion difiere de TaskEndTransition en que:
 * - si la terminacion de la tarea es por cancelación, no se toma ninguna 
 *   transición. Esto es util cuando otra tarea del nodo cancela a esta y decide 
 *   que transicion tomar. 
 * - En caso de terminar por fin normal, cancela las demás tareas
 *   abiertas del token.
 *   
 * La idea es que todas las tareas del nodo tengan este evento de terminacion.
 * 
 * IMPORTANTE: Cuando se utiliza en task node que tiene más de una tarea puede
 * pasar que las otras tareas del nodo queden abiertas si el nodo está marcado
 * con signal="first" Puede complementarse con la acción CancelOpenedTask en el
 * evento node-leave
 * 
 * @author fferrara
 * 
 */
public class CancelOpenedTasksAndLeave implements ActionHandler {

	private static final long serialVersionUID = 6627506796871189729L;

	String transitionName;

	public void execute(ExecutionContext arg0) throws Exception {

		TaskInstance taskInstance = arg0.getTaskInstance();

		// me fijo si la tarea patea el token
		if (taskInstance.isSignalling()) {
			// si cancelan me voy por cancel.
			if (!taskInstance.isCancelled()) {
				
				Token token = arg0.getToken();

				Collection<TaskInstance> unfinishedTasks = arg0.getTaskMgmtInstance().getUnfinishedTasks(token);
				
				// me dicen a donde ir?
				if (transitionName != null) {
					arg0.leaveNode(transitionName);
				}
				else {
					arg0.leaveNode();
				}

				for (TaskInstance task : unfinishedTasks) {
					if (task.isOpen()) {
						task.addComment("Cancelada por el token " + token.getName());
						task.cancel();
					}
				}
			}
		}

	}

}
