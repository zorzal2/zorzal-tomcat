package com.fontar.seguridad.acegi;

import com.fontar.seguridad.FontarSecurityAttributeType;


public final class GrantedPermissionByInstrumento extends FontarGrantedAuthority {

	
	private static final long serialVersionUID = 5694744924692808737L;
	
	private Long idInstrumento;
	
	public GrantedPermissionByInstrumento(String permission, Long idInstrumento) {
		super(permission);
		this.idInstrumento = idInstrumento;
	}

	public Long getIdInstrumento() {
		return idInstrumento;
	}

	@Override
	public FontarSecurityAttributeType getType() {
		return FontarSecurityAttributeType.PERMISSION_BY_INSTRUMENTO;
	}

	public String getAuthority() {
		return getType().getPrefix()+"["+getIdInstrumento()+"]-"+getAuthorityDescription();
	}
}
