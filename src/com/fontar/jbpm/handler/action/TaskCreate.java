package com.fontar.jbpm.handler.action;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.fontar.jbpm.util.JbpmConstants;
import com.fontar.util.ResourceManager;
import com.pragma.jbpm.TaskInstanceHelper;

/**
 * Usado en el evento task-create de una tarea
 * de workflow para asignar variables y 
 * determinar comportamientos.<br>  
 * @author fferrara
 * @author ssanchez
 */
public class TaskCreate implements ActionHandler {

	private static final long serialVersionUID = 2322743254882099968L;

	String beanActionId;
	String taskDescription;
	int taskPriority;
	String suspendTask;

	public void execute(ExecutionContext ctx) throws Exception {

		if (beanActionId != null) {
			ctx.getTaskInstance().setVariableLocally(JbpmConstants.VariableNames.BEAN_ACTION_NAME, beanActionId);
		}

		//HACK: FF / la prioridad en tiempo de diseño no queda reflejada en la BD
		ctx.getTaskInstance().setPriority(taskPriority);
		ctx.getTaskInstance().setDescription(ResourceManager.getWFTasksResource(taskDescription));
		
		if (Boolean.valueOf(suspendTask)) {
			ctx.getTaskInstance().suspend();
		}
		
		//Setea el permiso requerido para ejecutar la tarea
		TaskInstanceHelper.setPermissionRequired( ctx.getTaskInstance() );
	}

}
