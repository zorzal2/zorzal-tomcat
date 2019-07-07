package com.fontar.bus.impl;

import com.pragma.bus.BusinessException;

public class ProyectoSinWflException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ProyectoSinWflException(String[] param) {
		super();
		this.setBundleKey("app.proyecto.sinWflCargado", param);
	}
}