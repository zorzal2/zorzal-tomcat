package com.pragma.util.BeanUtils;

import java.lang.reflect.Method;

public abstract class Accessor {
	private String name;
	private Method method;
	private Object referrer;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getReferrer() {
		return referrer;
	}
	public void setReferrer(Object referrer) {
		this.referrer = referrer;
	}
	public Method getMethod() {
		return method;
	}
	protected void setMethod(Method method) {
		this.method = method;
	}
	protected abstract Class getValueType();
}
