package com.fontar.seguridad.acegi;

import org.acegisecurity.GrantedAuthority;

import com.fontar.seguridad.FontarSecurityAttributeType;

public abstract class FontarGrantedAuthority implements GrantedAuthority {

	
	private static final long serialVersionUID = 5694744924692808737L;
	
	private String authorityDescription; 
	
	public FontarGrantedAuthority(String authorityDescription) {
		this.authorityDescription = authorityDescription;
	}

	public abstract FontarSecurityAttributeType getType();

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FontarGrantedAuthority) {
			return ((FontarGrantedAuthority)obj).getAuthority().equals(authorityDescription); 
		} else return false;
	}

	@Override
	public int hashCode() {
		return getAuthority().hashCode();
	}

	@Override
	public String toString() {
		return getAuthority();
	}
	
	public String getAuthorityDescription() {
		return authorityDescription;
	}

	public boolean authorityDescriptionIs(String text) {
		return authorityDescription.equals(text);
	}
}