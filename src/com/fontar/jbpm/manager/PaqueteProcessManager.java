package com.fontar.jbpm.manager;

import java.util.List;

import org.jbpm.graph.exe.ProcessInstance;

import com.fontar.data.impl.domain.codes.paquete.TipoPaquete;
import com.fontar.jbpm.util.JbpmConstants;
import com.pragma.jbpm.JbpmManager;

public class PaqueteProcessManager {

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
	public Long nuevoProcessInstance(Long idPaquete, String tipoPaquete, List idWorkFlow, Long idInstrumento) {
		String processName = JbpmConstants.ProcessNames.PAQUETE;
		if (tipoPaquete.equals(TipoPaquete.DIRECTORIO.name())) {
			processName = JbpmConstants.ProcessNames.PAQUETE_DIRECTORIO;
		}
		
		ProcessInstance processInstance = jbpmManager.newProcessInstanceSettings()
			.setProcessName(processName)
			.addVariable(JbpmConstants.VariableNames.ID_PAQUETE, idPaquete)
			.addVariable(JbpmConstants.VariableNames.TIPO_PAQUETE, tipoPaquete)
			.addVariable(JbpmConstants.VariableNames.ID_PROCESO_PROYECTOS, idWorkFlow)
			.addVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO, idInstrumento)
			.createProcessInstance();
		
		this.currentProcessInstance = processInstance;

		return processInstance.getId();
	}

}
