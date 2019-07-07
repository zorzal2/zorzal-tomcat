package com.fontar.seguridad.acegi;

import com.fontar.seguridad.FontarSecurityAttributeType;


public final class GrantedSimplePermission extends FontarGrantedAuthority {

	
	private static final long serialVersionUID = 5694744924692808737L;
	
	public GrantedSimplePermission(String permission) {
		super(permission);
	}

	@Override
	public FontarSecurityAttributeType getType() {
		return FontarSecurityAttributeType.SIMPLE_PERMISSION;
	}

	public String getAuthority() {
		return getType().getPrefix()+getAuthorityDescription();
	}
}
