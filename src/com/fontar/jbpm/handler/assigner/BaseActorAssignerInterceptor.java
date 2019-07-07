package com.fontar.jbpm.handler.assigner;

import java.util.Collection;

import org.jbpm.taskmgmt.exe.TaskInstance;

public abstract class BaseActorAssignerInterceptor {

	/**
	 * Recorre todas las taskInstances y reasigna los actores de acuerdo
	 * al ActorAssigner que le corresponda a la tarea. 
	 * @param taskInstances
	 */
	protected void assign(Collection<TaskInstance> taskInstances) {
		try {
			for (TaskInstance taskInstance : taskInstances) {
				//BaseActorAssigner actorAssigner = (BaseActorAssigner) taskInstance.getTask().getAssignmentDelegation().getInstance();
				//actorAssigner.assign(taskInstance);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RuntimeException runtimeException = new RuntimeException(e);
			throw runtimeException;
		}
	}
}
