package com.pragma.util.interceptors;

import com.pragma.PragmaException;

public class NoEncryptContextException extends PragmaException {
	private static final long serialVersionUID = 1L;

	public NoEncryptContextException(Throwable cause) {
		super("No tiene clave encriptacion", "app.error.encrypt", cause);
	}
}