package com.fontar.web.seguridad.menu;

import java.util.HashMap;
import java.util.Map;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.PermissionsAdapter;

public class CacheableMenuPermissionAdapterDecotaror implements PermissionsAdapter {

	private Map<String,Boolean> allowed = new HashMap<String, Boolean>();
	
	private PermissionsAdapter permissionsAdapter;
	
	
	
	public CacheableMenuPermissionAdapterDecotaror(PermissionsAdapter permissionsAdapter) {
		super();
		this.permissionsAdapter = permissionsAdapter;
	}



	public boolean isAllowed(MenuComponent menuComponent) {
		Boolean isAllowed = this.allowed.get( menuComponent.getName() );
		if( isAllowed == null){
			isAllowed = this.permissionsAdapter.isAllowed(menuComponent);
			this.allowed.put( menuComponent.getName() , isAllowed );
		}
		return isAllowed;
	}



}
