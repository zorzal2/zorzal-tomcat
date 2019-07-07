package com.fontar.jbpm.manager;

import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Clase Helper de process instance del WorkFlow de IdeaProyecto
 * @author gboaglio
 */
public class IdeaProyectoProcessManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private ProcessInstance currentProcessInstance;

	public ProcessInstance getCurrentProcessInstance() {
		return currentProcessInstance;
	}

	public void setCurrentProcessInstance(ProcessInstance currentProcessInstance) {
		this.currentProcessInstance = currentProcessInstance;
	}

	/**
	 * Creo un nuevo workflow de IdeaProyecto, los parametros de entrada son los
	 * m�nimo indispensable para la creaci�n de la instancia.
	 * 
	 * @param idIdeaProyectoBean
	 * @return id process instance creado
	 */
	public Long nuevoProcessInstance(Long idIdeaProyectoBean, Long idInstrumento) {
		
		ProcessInstance processInstance = jbpmManager.newProcessInstanceSettings()
			.setProcessName(JbpmConstants.ProcessNames.IDEA_PROYECTO)
			.addVariable(JbpmConstants.VariableNames.ID_PROYECTO, idIdeaProyectoBean)
			.addVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, idInstrumento)
			.createProcessInstance();

		this.currentProcessInstance = processInstance;

		return processInstance.getId();
	}

}
