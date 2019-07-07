package com.fontar.web.action;

import com.pragma.PragmaException;

public class DeprecatedActionException extends PragmaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DeprecatedActionException(Class implementacion) {
		super("Esta implemetanción no se utiliza más, utilizar la implementación en " + implementacion.getName());
	}

}
