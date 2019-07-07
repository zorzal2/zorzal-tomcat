package com.fontar.web.action;

import com.pragma.PragmaException;

public class DeprecatedActionException extends PragmaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DeprecatedActionException(Class implementacion) {
		super("Esta implemetanci�n no se utiliza m�s, utilizar la implementaci�n en " + implementacion.getName());
	}

}
