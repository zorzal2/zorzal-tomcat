package com.pragma.bus;

import com.pragma.PragmaException;

public class BusinessException extends PragmaException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException() {
		super();
	}
}