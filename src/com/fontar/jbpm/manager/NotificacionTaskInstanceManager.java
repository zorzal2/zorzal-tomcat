package com.fontar.jbpm.manager;

import org.apache.commons.lang.NullArgumentException;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Esta clase provee funciones para manejar el <code>Task Instance</code> 
 * de un <code>Process Instance</code> de <code>Notificacion</code>.<br>
 * @author ssanchez
 *
 */
public class NotificacionTaskInstanceManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private TaskInstance currentTaskInstance;

	/**
	 * Constructor del Manager
	 * @param taskInstanceId
	 */
	public NotificacionTaskInstanceManager(Long taskInstanceId) {
		this.currentTaskInstance = this.jbpmManager.getTaskInstanceForUpdate(taskInstanceId);
	}

	/**
	 * Obtiene el <i>idNotificacion</i> de una <code>Notificacion</code>
	 * en base al <i>idTaskInstance</i> de una tarea.<br>
	 * @return idNotificacion.
	 */
	public Long getIdNotificacion() {
		Long idNotificacion = (Long) currentTaskInstance.getVariable(JbpmConstants.VariableNames.ID_NOTIFICACION);
		if (idNotificacion == null)
			throw new NullArgumentException("El workflow no tiene cargado el atributo "
					+ JbpmConstants.VariableNames.ID_NOTIFICACION);
		return idNotificacion;
	}

	/**
	 * Finaliza un <code>Task Instance</code>.
	 */
	public void finalizarTarea() {
		currentTaskInstance.end();
	}
}
