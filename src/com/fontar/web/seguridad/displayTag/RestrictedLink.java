package com.fontar.web.seguridad.displayTag;

import java.util.Set;

import org.acegisecurity.ConfigAttribute;

public interface RestrictedLink {

	public Set<ConfigAttribute> getSecurityAttributesRequired();
	
}
