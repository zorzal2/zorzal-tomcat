package com.fontar.seguridad;
/**
 * 
 * @author llobeto
 *
 */
public enum FontarSecurityAttributeType {
	SIMPLE_PERMISSION("PERMISSION-"),
	PERMISSION_BY_INSTRUMENTO("INSTRUMENTO"),
	/**
	 * No esta en uso todavia.
	 */
	PERMISION_BY_INSTANCE("INSTANCE");
	
	private String prefix;
	public String getPrefix() {
		return prefix;
	}
	private FontarSecurityAttributeType(String prefix) {
		this.prefix = prefix;
	}
}
