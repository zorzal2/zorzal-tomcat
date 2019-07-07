package com.fontar.web.seguridad.menu;

import com.fontar.seguridad.acegi.SimplePermissionSecurityConfig;

public class MenuPermissionSecurityConfig extends SimplePermissionSecurityConfig {

	private static final long serialVersionUID = 3983275234167825853L;
	/** 
	 * Los permisos de acceso a menu estan dados por los valores
	 * MENUNAME-INVENTARIO
	 ***/
	
	public MenuPermissionSecurityConfig(String config) {
		super(config.toUpperCase().replace("MENU","") + "-INVENTARIO");
	}
}
