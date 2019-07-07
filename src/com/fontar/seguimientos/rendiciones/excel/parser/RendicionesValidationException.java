package com.fontar.seguimientos.rendiciones.excel.parser;

import com.pragma.excel.exception.ValidationException;

public class RendicionesValidationException extends ValidationException {
	private static final long serialVersionUID = 1L;

	public RendicionesValidationException() {
		super();
	}

	public RendicionesValidationException(String message) {
		super(message);
	}
}
