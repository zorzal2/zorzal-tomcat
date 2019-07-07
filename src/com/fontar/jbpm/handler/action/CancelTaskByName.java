package com.fontar.jbpm.handler.action;

import java.util.Collection;
import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

/**
 * Cancela la lista de tareas especificadas en tasksName.<br>
 * La lista de tareas consideradas para cancelar pertenecen a un token, 
 * no son globales a todo el process instance.
 * @author ssanchez
 */
public class CancelTaskByName implements ActionHandler {

	private static final long serialVersionUID = 1L;

	List tasksName;
	
	@SuppressWarnings("unchecked")
	public void execute(ExecutionContext context) throws Exception {
		Token token = context.getToken();
		TaskMgmtInstance taskMgmt = context.getTaskMgmtInstance();
		
		Collection<TaskInstance> unfinishedTasks = taskMgmt.getUnfinishedTasks(token);

		int found = 0;
		for (TaskInstance task : unfinishedTasks) {
			if (tasksName.contains(task.getName())) {
				if (task.isOpen()) {
					task.addComment("Cancelada por el token " + token.getName());
					task.cancel();
				}
				found++;

				if (found == tasksName.size())
					return;
			}			
		}

	}
}
