package com.fontar.bus.impl;

import com.pragma.bus.BusinessException;

/**
 * 
 * @author ssanchez
 *
 */
public class ProyectoConFinanciamientoBancarioSinEntidadException extends BusinessException {
	
	private static final long serialVersionUID = 1L;

	public ProyectoConFinanciamientoBancarioSinEntidadException() {
		super();
		this.setBundleKey("app.proyecto.entidadFinanciera");
	}
}
