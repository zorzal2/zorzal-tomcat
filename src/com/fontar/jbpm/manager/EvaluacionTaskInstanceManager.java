package com.fontar.jbpm.manager;

import org.apache.commons.lang.NullArgumentException;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

public class EvaluacionTaskInstanceManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private TaskInstance currentTaskInstance;

	/**
	 * 
	 * @param taskInstanceId
	 */
	public EvaluacionTaskInstanceManager(Long taskInstanceId) {
		this.currentTaskInstance = this.jbpmManager.getTaskInstanceForUpdate(taskInstanceId);
	}

	/**
	 * 
	 * @return
	 */
	public Long getIdEvaluacion() {
		Long idEvaluacion = (Long) currentTaskInstance.getVariable(JbpmConstants.VariableNames.ID_EVALUACION);
		if (idEvaluacion == null)
			throw new NullArgumentException("El workflow no tiene cargado el atributo "
					+ JbpmConstants.VariableNames.ID_EVALUACION);
		return idEvaluacion;
	}

	public void finalizarTarea() {
		currentTaskInstance.end();
	}
}
