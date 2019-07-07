package com.fontar.seguridad.acegi;

import org.acegisecurity.AccessDecisionManager;
import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.intercept.ObjectDefinitionSource;

public abstract class AccessController {
	
	private AccessDecisionManager accessDecisionManager;
	
	private ObjectDefinitionSource objectDefinitionSource;
	
	public AccessController(){
		super();
	}
	
	public AccessController(AccessDecisionManager manager, ObjectDefinitionSource definitionSource){
		super();
		this.accessDecisionManager = manager;
		this.objectDefinitionSource = definitionSource;
	}

	public AccessDecisionManager getAccessDecisionManager() {
		return accessDecisionManager;
	}

	public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
		this.accessDecisionManager = accessDecisionManager;
	}

	public ObjectDefinitionSource getObjectDefinitionSource() {
		return objectDefinitionSource;
	}

	public void setObjectDefinitionSource(ObjectDefinitionSource objectDefinitionSource) {
		this.objectDefinitionSource = objectDefinitionSource;
	}
	
	public Authentication getAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
 
	

}
