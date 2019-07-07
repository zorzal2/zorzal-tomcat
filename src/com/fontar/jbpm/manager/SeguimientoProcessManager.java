package com.fontar.jbpm.manager;

import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Clase Helper de process instance del WorkFlow de Seguimiento
 * @author gboaglio
 */
public class SeguimientoProcessManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private ProcessInstance currentProcessInstance;

	public ProcessInstance getCurrentProcessInstance() {
		return currentProcessInstance;
	}

	public void setCurrentProcessInstance(ProcessInstance currentProcessInstance) {
		this.currentProcessInstance = currentProcessInstance;
	}

	/**
	 * Creo un nuevo workflow de Seguimiento, los parametros de entrada son los
	 * mínimo indispensable para la creación de la instancia.
	 * 
	 * @param idSeguimientoBean
	 * @param idInstrumento
	 * @return id process instance creado
	 */
	public Long nuevoProcessInstance(Long idSeguimientoBean, Long idInstrumento) { 
		ProcessInstance processInstance = jbpmManager.newProcessInstanceSettings()
			.setProcessName(JbpmConstants.ProcessNames.SEGUIMIENTO)
			.addVariable(JbpmConstants.VariableNames.ID_SEGUIMIENTO, idSeguimientoBean)
			.addVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, idInstrumento)
			.createProcessInstance();

		this.currentProcessInstance = processInstance;

		return processInstance.getId();
	}

}








