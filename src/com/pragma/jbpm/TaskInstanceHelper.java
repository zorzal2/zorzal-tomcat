package com.pragma.jbpm;

import org.jbpm.context.exe.VariableInstance;
import org.jbpm.instantiation.Delegation;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.jbpm.handler.assigner.ActorAssigner;
import com.fontar.jbpm.util.JbpmConstants;

public class TaskInstanceHelper {

		
	static public String getPermissionRequired(TaskInstance taskInstance){
		return (String) taskInstance.getVariable(JbpmConstants.VariableNames.PERMISSION_REQUIRED);
	}
	
	/* no utilizado
	static public void setPermissionRequired(ProcessInstance processInstance , String permission){
		processInstance.getContextInstance().setVariable(JbpmConstants.VariableNames.PERMISSION_REQUIRED, permission);
	}
	*/
	
	static public void setPermissionRequired(TaskInstance taskInstance){
		Delegation delegation = taskInstance.getTask().getAssignmentDelegation();
		if(delegation != null){
			String permission =  ((ActorAssigner)delegation.getInstance()).getIdActionWorkflow();
			VariableInstance variable = VariableInstance.create( taskInstance.getToken(), JbpmConstants.VariableNames.PERMISSION_REQUIRED , permission);
			taskInstance.addVariableInstance( variable);
		}
	}

	

	
	
	
}
