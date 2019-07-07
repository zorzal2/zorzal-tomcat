package com.fontar.jbpm.manager;

import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Clase Helper de process instance del WorkFlow de Idea Proyecto Pitec
 * @author gboaglio
 */
public class IdeaProyectoPitecProcessManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private ProcessInstance currentProcessInstance;

	public ProcessInstance getCurrentProcessInstance() {
		return currentProcessInstance;
	}

	public void setCurrentProcessInstance(ProcessInstance currentProcessInstance) {
		this.currentProcessInstance = currentProcessInstance;
	}

	/**
	 * Creo un nuevo workflow de Idea Proyecto Pitec, los parametros de entrada son los
	 * mínimo indispensable para la creación de la instancia
	 * @param idProyectoBean
	 * @return id process instance creado
	 */
	public Long nuevoProcessInstance(Long idIdeaProyectoBean, Boolean esVentanilla, Long idInstrumento) {
		
		ProcessInstance processInstance = jbpmManager.newProcessInstanceSettings()
			.setProcessName(JbpmConstants.ProcessNames.IDEA_PROYECTO_PITEC)
			.addVariable(JbpmConstants.VariableNames.ID_PROYECTO, idIdeaProyectoBean)
			.addVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, idInstrumento)
			.createProcessInstance();

		this.currentProcessInstance = processInstance;

		return processInstance.getId();
	}

}





