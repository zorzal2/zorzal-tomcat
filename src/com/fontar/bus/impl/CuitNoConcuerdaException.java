package com.fontar.bus.impl;

import com.pragma.bus.BusinessException;

public class CuitNoConcuerdaException extends BusinessException {
	
	private static final long serialVersionUID = 1L;

	public CuitNoConcuerdaException() {
		super();
		this.setBundleKey("app.configuracion.entidad.cuitNoConcuerda");
	}
}
