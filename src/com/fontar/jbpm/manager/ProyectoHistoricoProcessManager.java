package com.fontar.jbpm.manager;

import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Clase Helper de process instance del WorkFlow de Proyecto Histórico
 * @author ssanchez
 */
public class ProyectoHistoricoProcessManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private ProcessInstance currentProcessInstance;

	public ProcessInstance getCurrentProcessInstance() {
		return currentProcessInstance;
	}

	public void setCurrentProcessInstance(ProcessInstance currentProcessInstance) {
		this.currentProcessInstance = currentProcessInstance;
	}

	/**
	 * Creo un nuevo workflow de Proyecto Histórico.
	 * @param idProyecto
	 * @return id process instance creado
	 */
	public Long nuevoProcessInstance(Long idProyecto, Boolean esVentanilla, Long idInstrumento) {
		
		ProcessInstance processInstance = jbpmManager.newProcessInstanceSettings()
			.setProcessName(JbpmConstants.ProcessNames.PROYECTO_HISTORICO)
			.addVariable(JbpmConstants.VariableNames.ID_PROYECTO, idProyecto)
			.addVariable(JbpmConstants.VariableNames.ES_VENTANILLA, esVentanilla? "SI":"NO")
			.addVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, idInstrumento)
			.createProcessInstance();

		this.currentProcessInstance = processInstance;

		return processInstance.getId();
	}
}
