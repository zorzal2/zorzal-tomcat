package com.pragma.web;

import com.pragma.PragmaException;

public class InvalidForwardException extends PragmaException {

	private static final long serialVersionUID = 1L;

	public InvalidForwardException() {
		super();
		this.setBundleKey("app.error.InvalidForward");
	}

	public InvalidForwardException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidForwardException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidForwardException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}