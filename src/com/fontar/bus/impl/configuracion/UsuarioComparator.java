package com.fontar.bus.impl.configuracion;

import java.util.Comparator;

import com.fontar.data.impl.domain.ldap.Usuario;

public class UsuarioComparator implements Comparator<Usuario> {

	private static UsuarioComparator instance = new UsuarioComparator();
	
	public static  UsuarioComparator instance() {
		return instance;
	}
	
	private UsuarioComparator() {}
	
	public int compare(Usuario u1, Usuario u2) {
		return u1.getUserId().compareToIgnoreCase( u2.getUserId());
	}

}