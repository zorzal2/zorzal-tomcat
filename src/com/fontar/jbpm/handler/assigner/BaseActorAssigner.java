package com.fontar.jbpm.handler.assigner;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Asigna los pooledActors a la tarea consultando al AuthorizationService 
 * @author llobeto
 * 
 */
public abstract class BaseActorAssigner implements AssignmentHandler {

	public void assign(Assignable assignable, ExecutionContext executionContext) throws Exception {
		String[] actors = getPooledActors(executionContext.getTaskInstance());
		if(actors!=null) assignable.setPooledActors(actors);
	}
	public void assign(TaskInstance taskInstance) {
		String[] actors = getPooledActors(taskInstance);
		if(actors!=null){
			taskInstance.setPooledActors( actors );
		}else{
			System.err.println("No hay un instrumento relacionado al taskinstance @" + taskInstance.getId());
			/*
			 * FIXME: Si esto es realmente un error hay que disparar la excepcion
			 * adecuada. Si es una condicion que no deberia ocurrir disparar una
			 * RuntimeException.
			 */
		}
	}

	/**
	 * Devuelve los ids de los usuarios que pueden realizar la tarea.
	 * @param executionContext
	 * @return
	 */
	public abstract String[] getPooledActors(TaskInstance taskInstance);
}
