package com.fontar.jbpm.handler.assigner;

import java.util.Collection;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.fontar.seguridad.AuthorizationService;
import com.fontar.seguridad.GroupUpdateEvent;
import com.fontar.seguridad.SecurityConfigInterceptor;
import com.fontar.seguridad.UserUpdateEvent;
import com.pragma.jbpm.JbpmManager;
import com.pragma.util.ContextUtil;

public class ActorAssignerInterceptor extends BaseActorAssignerInterceptor implements SecurityConfigInterceptor {
	
	private JbpmManager jbpmManager = JbpmManager.instance();
	
	public AuthorizationService getAuthorizationService() {
		return (AuthorizationService) ContextUtil.getBean("authorizationService");
	}

	public void update(UserUpdateEvent userUpdateEvent) {
		if(userUpdateEvent.hasGroupChanges() && userUpdateEvent.hasWorkflowChanges())
			assign(this.getInvolvedTaskInstances(userUpdateEvent.getPermissionDisjuntion()));
	}
	
	public void update(GroupUpdateEvent groupUpdateEvent) {
		if(groupUpdateEvent.hasWorkflowChanges())
			assign(this.getInvolvedTaskInstances(groupUpdateEvent.getDisjuntionNames()));
	}

	private Collection<TaskInstance> getInvolvedTaskInstances(Collection<String> relatedPermissions) {
		return this.jbpmManager.findOpenTaskInstances(relatedPermissions);
	}
}
