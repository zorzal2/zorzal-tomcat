package com.fontar.jbpm.manager;

import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Esta clase provee funciones para manejar el <code>Process Instance</code> 
 * de un WorkFlow de <code>Notificacion</code>.<br>
 * @author ssanchez
 */
public class NotificacionProcessManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private ProcessInstance currentProcessInstance;

	public ProcessInstance getCurrentProcessInstance() {
		return currentProcessInstance;
	}

	public void setCurrentProcessInstance(ProcessInstance currentProcessInstance) {
		this.currentProcessInstance = currentProcessInstance;
	}

	/**
	 * Crea un <code>Process Instance</code> del Workflow de <code>Notificacion</code>
	 * con la variable <i>ID_NOTIFICACION</i> cuyo valor es dado por el 
	 * parámetro <i>idNotificacion</i>.
	 */
	public Long nuevoProcessInstance(Long idNotificacion , Long idInstrumento) {
		
		ProcessInstance processInstance = jbpmManager.newProcessInstanceSettings()
		.setProcessName(JbpmConstants.ProcessNames.NOTIFICACION)
		.addVariable(JbpmConstants.VariableNames.ID_NOTIFICACION, idNotificacion)
		.addVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, idInstrumento)
		.createProcessInstance();
		
		this.currentProcessInstance = processInstance;

		return processInstance.getId();
	}
}
