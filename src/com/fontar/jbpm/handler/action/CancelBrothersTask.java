package com.fontar.jbpm.handler.action;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

/**
 * Cancela las tareas de todos los tokens hijos del token principal del contexto
 * La cancelación es recursiva sobre todos los hermanos
 * 
 * @author fferrara
 */
public class CancelBrothersTask implements ActionHandler {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void execute(ExecutionContext arg0) throws Exception {

		// token actual
		Token token = arg0.getToken();
		TaskMgmtInstance taskMgmt = arg0.getTaskMgmtInstance();

		// cancelo las tareas a partir del padre
		this.cancelTask(token.getParent(), taskMgmt);

		token.unlock();

		// pateo el token
		token.signal();
	}

	private void cancelTask(Token token, TaskMgmtInstance taskMgmt) {
		// busco todos los hijos de mi padre
		Map childrens = token.getActiveChildren();
		Set keys = childrens.keySet();

		// cancela las tareas de mis hnos
		for (Object key : keys) {
			Token child = (Token) childrens.get(key);

			// saco las tareas no terminadas de un hno
			Collection<TaskInstance> unfinishedTasks = taskMgmt.getUnfinishedTasks(child);

			for (TaskInstance task : unfinishedTasks) {
				if (task.isOpen()) {
					task.addComment("Cancelada por el token hermano " + token.getName());
					task.cancel();
				}
			}
			cancelTask(child, taskMgmt);
		}
	}
}
