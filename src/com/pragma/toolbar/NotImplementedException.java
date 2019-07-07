package com.pragma.toolbar;

import com.pragma.toolbar.exception.UnControlledException;

public class NotImplementedException extends UnControlledException {
	private static final long serialVersionUID = 1L;
	
	public NotImplementedException() {}
	
	public NotImplementedException(String message) {
		super(message);
	}
}
