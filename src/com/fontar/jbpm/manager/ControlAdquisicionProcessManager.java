package com.fontar.jbpm.manager;

import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Esta clase provee funciones para manejar el <code>Process Instance</code> 
 * de un WorkFlow de <code>ControlAdquisición</code>.<br>
 * @author ssanchez
 */
public class ControlAdquisicionProcessManager {

	private JbpmManager jbpmManager = JbpmManager.instance();
	private ProcessInstance currentProcessInstance;

	public ProcessInstance getCurrentProcessInstance() {
		return currentProcessInstance;
	}

	public void setCurrentProcessInstance(ProcessInstance currentProcessInstance) {
		this.currentProcessInstance = currentProcessInstance;
	}

	/**
	 * Crea un <code>Process Instance</code> del Workflow de <code>ControlAdquisición</code>
	 * con la variable <i>ID_PROCEDIMIENTO</i> cuyo valor es dado por el 
	 * parámetro <i>idProcedimiento</i>.
	 */
	public Long nuevoProcessInstance(Long idProcedimiento , Long idInstrumento) {
		ProcessInstance processInstance = jbpmManager.newProcessInstanceSettings()
			.setProcessName(JbpmConstants.ProcessNames.CONTROL_ADQUISICION)
			.addVariable(JbpmConstants.VariableNames.ID_PROCEDIMIENTO, idProcedimiento)
			.addVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, idInstrumento)
			.createProcessInstance();

		this.currentProcessInstance = processInstance;

		return processInstance.getId();
	}
}
