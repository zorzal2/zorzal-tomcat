package com.fontar.web.action;

import com.pragma.PragmaException;

public class InvalidParameterException extends PragmaException {
	
	private static final long serialVersionUID = 1L;

	public InvalidParameterException(String parameterName) {
		super("El parametro del request " + parameterName + " es nulo o no existe");
	}
}
