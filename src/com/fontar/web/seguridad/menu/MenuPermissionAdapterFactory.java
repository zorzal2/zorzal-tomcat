package com.fontar.web.seguridad.menu;

import net.sf.navigator.menu.PermissionsAdapter;

import org.acegisecurity.AccessDecisionManager;
import org.acegisecurity.intercept.ObjectDefinitionSource;


public final  class  MenuPermissionAdapterFactory {
	
	
	private AccessDecisionManager accessDecisionManager;
	
	private ObjectDefinitionSource objectDefinitionSource;
	
	public PermissionsAdapter newInstance( ){
		PermissionsAdapter adapter = new MenuPermissionAdapter( this.accessDecisionManager , this.objectDefinitionSource );
		return new CacheableMenuPermissionAdapterDecotaror( adapter );
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

	
	
}
