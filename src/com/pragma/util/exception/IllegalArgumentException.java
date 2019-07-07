package com.pragma.util.exception;

import com.pragma.toolbar.exception.UnControlledException;

public class IllegalArgumentException extends UnControlledException {

	public IllegalArgumentException() {
		super();
	}
	public IllegalArgumentException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;

}
