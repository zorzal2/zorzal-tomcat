package com.fontar.jbpm.handler.action;

import java.util.Collection;
import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Resumen las tareas especificadas en tasksName, generalmente es utilizada para
 * resumir las tareas Suspendidas con SuspendTask
 * 
 * IMPORTANTE: asumimos que solo se usara en Tareas Globales -> NO hay 2
 * taskInstances con el mismo nombre
 * @author fferrara
 * 
 */
public class ResumeTask implements ActionHandler {

	private static final long serialVersionUID = 1L;

	List tasksName;

	@SuppressWarnings("unchecked")
	public void execute(ExecutionContext ctx) throws Exception {

		// TODO: FF / ¿se podrá hacer más performante?
		Collection<TaskInstance> unfinished = ctx.getTaskMgmtInstance().getTaskInstances();
		int found = 0;

		// busco las tareas
		for (TaskInstance ti : unfinished) {
			if (tasksName.contains(ti.getName())) {
				ti.resume();
				found++;
				// ya encontre todas?
				if (found == tasksName.size())
					return;
			}
		}
	}

}
