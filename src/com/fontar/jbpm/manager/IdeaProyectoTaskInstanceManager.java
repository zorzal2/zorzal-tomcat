package com.fontar.jbpm.manager;

import org.apache.commons.lang.NullArgumentException;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

public class IdeaProyectoTaskInstanceManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private TaskInstance currentTaskInstance;

	/**
	 * 
	 * @param taskInstanceId
	 */
	public IdeaProyectoTaskInstanceManager(Long taskInstanceId) {
		this.currentTaskInstance = this.jbpmManager.getTaskInstanceForUpdate(taskInstanceId);
	}

	/**
	 * 
	 * @return
	 */
	public Long getIdIdeaProyecto() {
		Long idIdeaProyecto = (Long) currentTaskInstance.getVariable(JbpmConstants.VariableNames.ID_PROYECTO);
		if (idIdeaProyecto == null)
			throw new NullArgumentException("El workflow no tiene cargado el atributo "
					+ JbpmConstants.VariableNames.ID_PROYECTO);
		return idIdeaProyecto;
	}

	/**
	 * 
	 * @param EstadoActual
	 */
	public void finalizarTarea() {
		String leaveTransitionName = (String) currentTaskInstance.getVariableLocally(JbpmConstants.VariableNames.LEAVE_TRANSITION);
		if (leaveTransitionName != null) {
			currentTaskInstance.end(leaveTransitionName);
		}
		else {
			currentTaskInstance.end();
		}
	}
	
	public Task getTask() {
		return currentTaskInstance.getTask();
	}

	public TaskInstance getCurrentTaskInstance() {
		return currentTaskInstance;
	}
}
