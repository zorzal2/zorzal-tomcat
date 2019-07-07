package com.fontar.seguridad.acegi;

import org.acegisecurity.ConfigAttribute;

import com.fontar.seguridad.FontarSecurityAttributeType;

public abstract class FontarSecurityConfig implements ConfigAttribute {

	private static final long serialVersionUID = 3012429670486573129L;

	private String name;


	public FontarSecurityConfig(String name) {
		this.name = name.toUpperCase();
	}

	public String getName() {
		return name;
	}

	public abstract FontarSecurityAttributeType getType();

	@Override
	public String toString() {
		return getAttribute();
	}
	
}
