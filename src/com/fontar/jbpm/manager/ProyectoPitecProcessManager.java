package com.fontar.jbpm.manager;

import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Clase Helper de process instance del WorkFlow de Proyecto Pitec
 * @author gboaglio
 */
public class ProyectoPitecProcessManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private ProcessInstance currentProcessInstance;

	public ProcessInstance getCurrentProcessInstance() {
		return currentProcessInstance;
	}

	public void setCurrentProcessInstance(ProcessInstance currentProcessInstance) {
		this.currentProcessInstance = currentProcessInstance;
	}

	/**
	 * Creo un nuevo workflow de Proyecto Pitec, los parametros de entrada son los
	 * mínimo indispensable para la creación de la instancia
	 * @param idProyectoBean
	 * @return id process instance creado
	 */
	public Long nuevoProcessInstance(Long idProyectoPitecBean, Boolean esVentanilla, Long idInstrumento) {
		
		ProcessInstance processInstance = jbpmManager.newProcessInstanceSettings()
		.setProcessName(JbpmConstants.ProcessNames.PROYECTO_PITEC)
		.addVariable(JbpmConstants.VariableNames.ID_PROYECTO, idProyectoPitecBean)
		.addVariable(JbpmConstants.VariableNames.ES_VENTANILLA, esVentanilla? "SI":"NO")
		.addVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, idInstrumento)
		.createProcessInstance();
		
		this.currentProcessInstance = processInstance;

		return processInstance.getId();
	}

}





