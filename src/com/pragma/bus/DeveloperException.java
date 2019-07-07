package com.pragma.bus;

import com.pragma.PragmaException;

public class DeveloperException extends PragmaException {

	private static final long serialVersionUID = 1L;

	public DeveloperException(String message) {
		super(message);
	}

	public DeveloperException() {
		super();
	}
}