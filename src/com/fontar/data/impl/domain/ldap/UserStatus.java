package com.fontar.data.impl.domain.ldap;

import com.fontar.data.api.domain.codes.Enumerable;

public enum UserStatus  implements Enumerable{

	
	NEW("nuevo"),
	INISTIALIZED("inicializado");

	private String descripcion;

	private UserStatus(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getName() {
		return this.name();
	}

	@Override
	public String toString() {
		return getDescripcion();
	}
}
