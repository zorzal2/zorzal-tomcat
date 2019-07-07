package com.fontar.jbpm.handler.action;

import java.util.Collection;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Finaliza todas las tareas abiertas del proceso
 * @author fferrara
 */

@SuppressWarnings("serial")
public class EndOpenedTasks implements ActionHandler {

	public void execute(ExecutionContext ctx) throws Exception {

		Collection<TaskInstance> unfinishedTasks = ctx.getTaskMgmtInstance().getTaskInstances();

		for (TaskInstance task : unfinishedTasks) {
			if (task.isOpen()) {
				task.cancel();
			}
		}
	}
}
