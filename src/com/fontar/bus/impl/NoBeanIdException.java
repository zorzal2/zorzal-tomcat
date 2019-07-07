package com.fontar.bus.impl;

import com.pragma.bus.BusinessException;

public class NoBeanIdException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public NoBeanIdException() {
		super();
		this.setBundleKey("app.proyecto.necesarioID");
	}
}