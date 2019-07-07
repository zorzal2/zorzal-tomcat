package com.fontar.jbpm.handler.action;

import java.util.Collection;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Cancela todas las instancias de tareas de un process instance.
 * @author ssanchez
 */
public class CancelAllOpenedTask implements ActionHandler {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void execute(ExecutionContext ctx) throws Exception {

		Collection<TaskInstance> unfinished = ctx.getTaskMgmtInstance().getTaskInstances();

		// busco las tareas
		for (TaskInstance ti : unfinished) {
			if (ti.isOpen()) {
				ti.cancel();
			}
		}
	}
}
