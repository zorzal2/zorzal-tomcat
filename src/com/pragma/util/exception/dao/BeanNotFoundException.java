package com.pragma.util.exception.dao;

import com.pragma.toolbar.exception.UnControlledException;

public class BeanNotFoundException extends UnControlledException {

	private static final long serialVersionUID = 1L;
	
	public BeanNotFoundException(String string) {
		super(string);
	}
	public BeanNotFoundException() {
		super();
	}
}