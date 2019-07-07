package com.pragma;

public abstract class PragmaException extends RuntimeException {

	private String bundleKey;

	private String[] params;

	public String getBundleKey() {
		return bundleKey;
	}

	public String[] getParam() {
		return params;
	}

	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}

	public void setBundleKey(String bundleKey, String[] params) {
		this.bundleKey = bundleKey;
		this.params = params;
	}

	public void setBundleKey(String bundleKey, String param) {
		this.bundleKey = bundleKey;
		this.params = new String[1];
		this.params[0] = param;
	}

	public PragmaException() {
		super();
	}

	public PragmaException(String message) {
		super(message);
	}

	public PragmaException(String message, Throwable cause) {
		super(message, cause);
	}

	public PragmaException(String message, String bundleKey, Throwable cause) {
		super(message, cause);
		this.bundleKey = bundleKey;
	}

	public PragmaException(Throwable cause) {
		super(cause);
	}
}