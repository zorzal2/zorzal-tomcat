package com.pragma.util.BeanUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Getter extends Accessor {
	
	protected Getter(String name, Method method, Object referrer) {
		setName(name);
		setMethod(method);
		setReferrer(referrer);
	}

	public Object get() {
		try {
			return getMethod().invoke(getReferrer());
		} catch (Exception exception) {}
		return null;
	}

	protected Class getValueType() {
		return getMethod().getReturnType();
	}
	/**
	 * Devuelve una lista de getters del objeto dado.
	 */
	public static List<Getter> getAllFrom(Object object) {
		if(object==null) {
			throw new IllegalArgumentException("Argument must not be null!");
		}
		Method[] methods = object.getClass().getMethods();
		List<Getter> ret = new ArrayList<Getter>();
		
		for (int i = 0; i < methods.length; i++) {
			String name = getGetterNameFromMethodName(methods[i].getName());
			if	(name!=null) {
				Getter getter = new Getter(name, methods[i], object);
				ret.add(getter);
			}
		}
		return ret;
	}
	
	private static String getGetterNameFromMethodName(String name){
		//Chequeo precondiciones
		if(name.length()<4)return null;
		if(!name.startsWith("get"))return null;
		String firstLetter = name.substring(3, 4);
		String firstLetterLC = firstLetter.toLowerCase();
		if(firstLetter.equals(firstLetterLC)) return null;
		return firstLetterLC.concat(name.substring(4));
	}
}
