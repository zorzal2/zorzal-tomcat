package com.fontar.seguridad.acegi;

import com.fontar.seguridad.FontarSecurityAttributeType;


public class SimplePermissionSecurityConfig extends FontarSecurityConfig {

	private static final long serialVersionUID = 3012429670486573129L;
	
	public SimplePermissionSecurityConfig(String config) {
		super(config);
	}

	public String getAttribute() {
		return getType().getPrefix()+getName();
	}

	@Override
	public FontarSecurityAttributeType getType() {
		return FontarSecurityAttributeType.SIMPLE_PERMISSION;
	}
	
}
