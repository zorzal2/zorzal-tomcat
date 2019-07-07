package com.pragma.toolbar.config;

public class PropertyNotPresentException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public PropertyNotPresentException(String propertyName, String toolbarId) {
        super("Property "+propertyName+" not found in configuration for toolbar "+toolbarId);
    }
}