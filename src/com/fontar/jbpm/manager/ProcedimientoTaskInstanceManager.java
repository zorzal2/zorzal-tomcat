package com.fontar.jbpm.manager;

import org.apache.commons.lang.NullArgumentException;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Clase Helper de las tareas del WorkFlow de un Procedimiento.<br>
 * @author ssanchez
 */
public class ProcedimientoTaskInstanceManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private TaskInstance currentTaskInstance;

	/**
	 * Constructor, siempre es necesario tener el id de tarea
	 * @param taskInstanceId
	 */
	public ProcedimientoTaskInstanceManager(Long taskInstanceId) {
		this.currentTaskInstance = this.jbpmManager.getTaskInstanceForUpdate(taskInstanceId);
	}

	/**
	 * Finaliza un <code>Task Instance</code>.
	 */
	public void finalizarTarea() {
		currentTaskInstance.end();
	}
	
	/**
	 * Obtiene el <i>idProcedimiento</i> de un <code>ProcedimientoBean</code>
	 * en base al <i>idTaskInstance</i> de una tarea.<br>
	 * @return id del procedimiento
	 */
	public Long getIdProcedimiento() {
		
		Long idProcedimiento = (Long) currentTaskInstance.getVariable(JbpmConstants.VariableNames.ID_PROCEDIMIENTO);
		if (idProcedimiento == null)
			throw new NullArgumentException("El workflow no tiene cargado el atributo "
					+ JbpmConstants.VariableNames.ID_PROCEDIMIENTO);
		
		return idProcedimiento;
	}
}
