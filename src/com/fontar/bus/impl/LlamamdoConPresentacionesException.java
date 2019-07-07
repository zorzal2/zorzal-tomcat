package com.fontar.bus.impl;

import com.pragma.bus.BusinessException;

public class LlamamdoConPresentacionesException extends BusinessException {
	
	private static final long serialVersionUID = 1L;

	public LlamamdoConPresentacionesException() {
		super();
		this.setBundleKey("app.error.eliminarConvocatoria");
	}
}
