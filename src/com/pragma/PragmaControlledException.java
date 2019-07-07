package com.pragma;

public abstract class PragmaControlledException extends Exception {

	private String bundleKey;

	private String[] params = {};

	public String getBundleKey() {
		return bundleKey;
	}

	public String[] getParam() {
		return params;
	}

	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}

	public void setBundleKey(String bundleKey, String... params) {
		this.bundleKey = bundleKey;
		this.params = params;
	}

	public PragmaControlledException() {
		super();
	}

	public PragmaControlledException(String message) {
		super(message);
	}

	public PragmaControlledException(String message, Throwable cause) {
		super(message, cause);
	}

	public PragmaControlledException(String message, String bundleKey, Throwable cause) {
		super(message, cause);
		this.bundleKey = bundleKey;
	}

	public PragmaControlledException(Throwable cause) {
		super(cause);
	}
}