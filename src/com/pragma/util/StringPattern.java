package com.pragma.util;

public class StringPattern {
	private String result;
	public StringPattern(String basePattern) {
		this.result = basePattern;
	}
	@SuppressWarnings("el-syntax")
	public StringPattern set(String property, Object value) {
		result = result.replace("${"+property+"}", String.valueOf(value));
		return this;
	}
	@Override
	public String toString() {
		return result;
	}
}
