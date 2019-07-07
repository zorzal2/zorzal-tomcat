package com.fontar.bus.impl;

import java.util.Collection;
import java.util.Map;

import com.fontar.data.api.dao.Rol;
import com.pragma.PragmaControlledException;

public class ObjetoEnUsoException extends PragmaControlledException {
	
	private static final long serialVersionUID = 1L;
	private Map<Rol, Collection<String>> usos;
	
	public ObjetoEnUsoException(Map<Rol, Collection<String>>  usos) {
		super();
		this.usos = usos;
	}

	public Map<Rol, Collection<String>>  getUsos() {
		return usos;
	}

}
