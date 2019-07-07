package com.fontar.jbpm.manager;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Clase Helper de las tareas del WorkFlow de proyecto
 * @author fferrara
 */
public class ProyectoTaskInstanceManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private TaskInstance currentTaskInstance;

	/**
	 * Constructor, siempre es necesario tener el id de tarea
	 * @param taskInstanceId
	 */
	public ProyectoTaskInstanceManager(Long taskInstanceId) {
		this.currentTaskInstance = this.jbpmManager.getTaskInstanceForUpdate(taskInstanceId);
	}

	/**
	 * Finaliza la tarea de Admisibilida con el resultado de la misma
	 * @param idAdmisiblidad
	 */
	public void finalizarAdmisibilidad(String resultado) {
		// currentTaskInstance.setVariable(JbpmConstants.VariableNames.RESULTADO_ADMISION,resultado);
		this.finalizarTarea();
	}

	/**
	 * Finaliza la tarea de Finalizar Control. Chequea que no haya WF de
	 * Evaluación Abiertos
	 * 
	 */
	public void finalizarTareaFinalizarControl() {
		// TODO: habría que chequear que todos los workflow de evaluación esten
		// terminados
	}

	/**
	 * Finaliza la tarea eligiendo como LeaveTransition la especificada en la
	 * variable LEAVE_TRANSITION
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

	/**
	 * Devuelve el id_proyecto asociado a la tarea
	 * @return
	 */
	public Long getIdProyecto() {
		return (Long) currentTaskInstance.getVariable(JbpmConstants.VariableNames.ID_PROYECTO);
	}
}
