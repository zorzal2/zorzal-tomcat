package com.fontar.bus.impl;

import com.pragma.bus.BusinessException;

/**
 * 
 * @author ssanchez
 *
 */
public class CargarProyectoExistenteException extends BusinessException {
	
	private static final long serialVersionUID = 1L;

	public CargarProyectoExistenteException() {
		super();
		this.setBundleKey("app.proyecto.existeProyecto");
	}
}
