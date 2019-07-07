package com.fontar.proyecto.datos.excel.parser;

import com.pragma.excel.exception.ValidationException;

public class ProyectosExcelValidationException extends ValidationException {
	private static final long serialVersionUID = 1L;

	public ProyectosExcelValidationException() {
		super();
	}

	public ProyectosExcelValidationException(String message) {
		super(message);
	}
}
