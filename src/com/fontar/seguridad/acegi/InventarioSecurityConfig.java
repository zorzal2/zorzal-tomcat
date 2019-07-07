package com.fontar.seguridad.acegi;

import org.acegisecurity.SecurityConfig;

public class InventarioSecurityConfig extends SecurityConfig {

	
	private static final long serialVersionUID = 2728331768311286898L;
	
	public InventarioSecurityConfig(String config) {
		super(config.toUpperCase().replace("MENU","") + "-INVENTARIO");
	}


}
