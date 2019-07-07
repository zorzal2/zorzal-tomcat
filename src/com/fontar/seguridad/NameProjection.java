package com.fontar.seguridad;

import org.apache.commons.collections.Transformer;

import com.fontar.data.impl.domain.ldap.Permiso;

public class NameProjection implements Transformer {

	public Object transform(Object object) {
		Permiso permiso = (Permiso) object;
		return permiso.getIdPermiso();
	}
	
	
}