package com.fontar.jbpm.manager;

import java.util.List;

import org.apache.commons.lang.NullArgumentException;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

public class PaqueteTaskInstanceManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private TaskInstance currentTaskInstance;

	/**
	 * 
	 * @param taskInstanceId
	 */
	public PaqueteTaskInstanceManager(Long taskInstanceId) {
		this.currentTaskInstance = this.jbpmManager.getTaskInstanceForUpdate(taskInstanceId);
	}

	/**
	 * 
	 * @return
	 */
	public Long getIdPaquete() {
		Long idPaquete = (Long) currentTaskInstance.getVariable(JbpmConstants.VariableNames.ID_PAQUETE);
		if (idPaquete == null)
			throw new NullArgumentException("El workflow no tiene cargado el atributo "
					+ JbpmConstants.VariableNames.ID_PAQUETE);
		return idPaquete;
	}

	/**
	 * 
	 * @param EstadoActual
	 */
	public void finalizarTarea() {
		currentTaskInstance.end();
	}

	/**
	 * 
	 * @param idWorkflow
	 */
	public void finalizarTareaModifcarPaquete(List<Long> idWorkFlow) {
		currentTaskInstance.setVariable(JbpmConstants.VariableNames.ID_PROCESO_PROYECTOS, idWorkFlow);
		currentTaskInstance.end();
	}
}
