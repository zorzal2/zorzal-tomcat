package com.fontar.seguridad.cripto;

import com.pragma.PragmaControlledException;

public class AccesoDenegadoException extends PragmaControlledException {
	private static final long serialVersionUID = 1L;
	
	private static String ACCESS_DENIED = "app.authorization.accessDenied";
	private static String ACCESS_DENIED_CAUSE ="app.authorization.accessDenied";

	public AccesoDenegadoException() {
		super();
		setBundleKey(ACCESS_DENIED);
	}

	public AccesoDenegadoException(String fieldName) {
		super(fieldName);
		setBundleKey(ACCESS_DENIED_CAUSE, fieldName);
	}

	public AccesoDenegadoException(String fieldName, Throwable cause) {
		super(fieldName, cause);
		setBundleKey(ACCESS_DENIED_CAUSE, fieldName);
	}

	public AccesoDenegadoException(Throwable cause) {
		super(cause);
	}

}
