package com.fontar.jbpm.manager;

import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

/**
 * Clase Helper de process instance del WorkFlow de Evaluacion
 * @author fferrara
 */
public class EvaluacionProcessManager {

	private JbpmManager jbpmManager = JbpmManager.instance();

	private ProcessInstance currentProcessInstance;

	/*
	 * (non-Javadoc)
	 * @see com.fontar.jbpm.manager.ProcessManager#getCurrentProcessInstance()
	 */
	public ProcessInstance getCurrentProcessInstance() {
		return currentProcessInstance;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.jbpm.manager.ProcessManager#setCurrentProcessInstance(org.jbpm.graph.exe.ProcessInstance)
	 */
	public void setCurrentProcessInstance(ProcessInstance currentProcessInstance) {
		this.currentProcessInstance = currentProcessInstance;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fontar.jbpm.manager.ProcessManager#nuevoProcessInstance(java.lang.Long,
	 * java.lang.String)
	 */
	public Long nuevoProcessInstance(Long idEvaluacionBean, Long idInstrumento) {

		ProcessInstance processInstance = jbpmManager.newProcessInstanceSettings()
			.setProcessName(JbpmConstants.ProcessNames.EVALUACION)
			.addVariable(JbpmConstants.VariableNames.ID_EVALUACION, idEvaluacionBean)
			.addVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, idInstrumento)
			.createProcessInstance();
		
		this.currentProcessInstance = processInstance;

		return processInstance.getId();
	}
}
