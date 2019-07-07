package com.fontar.jbpm.handler.action;

import java.util.Collection;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

public class CancelOpenedTask implements ActionHandler {

	/**
	 * Cancela las tareas abiertas por el token del Contexto. Se puede utilizar
	 * en el evento node-leave cancelando de esta manera todas las tareas del
	 * Task Node
	 */
	private static final long serialVersionUID = 1L;

	public void execute(ExecutionContext arg0) throws Exception {
		Token token = arg0.getToken();
		TaskMgmtInstance taskMgmt = arg0.getTaskMgmtInstance();

		Collection<TaskInstance> unfinishedTasks = taskMgmt.getUnfinishedTasks(token);

		for (TaskInstance task : unfinishedTasks) {
			if (task.isOpen()) {
				task.addComment("Cancelada por el token " + token.getName());
				task.cancel();
			}
		}

	}

}
