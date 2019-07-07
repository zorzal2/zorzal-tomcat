package com.fontar.jbpm.handler.assigner;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.jbpm.util.JbpmConstants;
import com.fontar.seguridad.AuthorizationService;
import com.fontar.util.Util;
import com.pragma.util.ContextUtil;

/**
 * Asigna los pooledActors a la tarea consultando al AuthorizationService 
 * @author fferrara, llobeto
 * 
 */
public class ActorAssigner extends BaseActorAssigner {

	private static final long serialVersionUID = 1L;

	private String idActionWorkflow;

	public String getIdActionWorkflow() {
		return idActionWorkflow;
	}

	public void setIdActionWorkflow(String idActionWorkflow) {
		this.idActionWorkflow = idActionWorkflow;
	}

	@Override
	public String[] getPooledActors(TaskInstance taskInstance) {
		Long idInstrumento = (Long) taskInstance.getVariable(JbpmConstants.VariableNames.ID_INSTRUMENTO);
		AuthorizationService authorizationService = (AuthorizationService) ContextUtil.getBean("authorizationService");
		if (!Util.isBlank(this.getIdActionWorkflow()))
			return authorizationService.getPooledActors(this.getIdActionWorkflow(),idInstrumento);
		else return null;
	}
}
