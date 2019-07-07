package com.pragma.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private ResourceBundle bundle;
	public Messages(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public String getString(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	public String getString(String key, String[] parameters) {
		String ret = getString(key);
		//reemplazo los parametros
		for(int i = 0; i<parameters.length; i++) {
			String param = parameters[i];
			ret = ret.replaceAll("\\{"+i+"\\}", param);
		}
		return ret;
	}
	public String getString(String key, String parameter) {
		String[] parameters = {parameter};
		return getString(key, parameters);
	}
	public String getString(String key, String parameter1, String parameter2) {
		String[] parameters = {parameter1, parameter2};
		return getString(key, parameters);
	}
	public String getString(String key, String parameter1, String parameter2, String parameter3) {
		String[] parameters = {parameter1, parameter2, parameter3};
		return getString(key, parameters);
	}
	public Iterable<String> all() {
		return new EnumerationAdapter<String>(bundle.getKeys());
	}
}
