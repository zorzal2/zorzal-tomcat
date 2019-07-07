package com.fontar.bus.impl;

import com.pragma.bus.BusinessException;

public class SinProyCargadoException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public SinProyCargadoException() {
		super();
		this.setBundleKey("app.evaluacion.sinProyCargado");
	}
}
