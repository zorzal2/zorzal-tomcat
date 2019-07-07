package com.fontar.bus.impl;

import com.pragma.bus.BusinessException;

public class FechaAsuntoRegistroException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public FechaAsuntoRegistroException() {
		super();
		this.setBundleKey("app.proyecto.invalidFechaAsuntoRegistro");
	}
}