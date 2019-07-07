package com.fontar.proyecto.pac.excel.parser;

import com.pragma.excel.exception.ValidationException;

public class PacValidationException extends ValidationException {
	private static final long serialVersionUID = 1L;

	public PacValidationException() {
		super();
	}

	public PacValidationException(String message) {
		super(message);
	}

}
