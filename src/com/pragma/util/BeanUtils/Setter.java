package com.pragma.util.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.pragma.util.Conditions.Condition;
import com.pragma.util.dataConversion.ConversionUtils;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

public class Setter extends Accessor {
	
	protected Setter(String name, Method method, Object referrer) {
		setName(name);
		setMethod(method);
		setReferrer(referrer);
	}
	public void set(Object value) throws IllegalArgumentException {
		Object[] args = {value};
		try {
			this.getMethod().invoke(this.getReferrer(), args);
		} catch (IllegalAccessException exception) {
			exception.printStackTrace();
		} catch (InvocationTargetException exception) {
			exception.printStackTrace();
		}
	}
	/**
	 * Setea el valor a partir de un getter dado.
	 * Intenta hacer una conversion de datos si es necesaria.
	 * @param getter
	 * @throws IllegalArgumentException
	 */
	public void setFrom(Getter getter)throws ConversionException {
		Object value = getter.get();
		this.set(ConversionUtils.getDefault().convert(value, getValueType()));
	}
	/**
	 * Setea el valor a partir de un getter dado solo si el valor obtenido cumple
	 * cierta condicion.
	 * Intenta hacer una conversion de datos si es necesaria.
	 * @param getter
	 * @throws ConversionException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalArgumentException
	 * @throws ConversionException 
	 */
	public void setFromIfHolds(Getter getter, Condition condition) throws ConversionException {
		Object value = getter.get();
		if(condition.isTrueFor(value))
			this.set(ConversionUtils.getDefault().convert(value, getValueType()));
	}
	public Class getValueType() {
		return getMethod().getParameterTypes()[0];
	}

	/**
	 * Setea el valor <i>value</i> dado solo si el valor obtenido 
	 * cumple cierta condicion.
	 * Intenta hacer una conversion de datos si es necesaria.
	 * @param value
	 * @author ssanchez
	 * @throws ConversionException 
	 */
	public void setValueFromIfHolds(Object value, Getter getter, Condition condition) throws ConversionException, ConversionException {
		if(condition.isTrueFor(getter.get()))
			this.set(ConversionUtils.getDefault().convert(value, getValueType()));
	}	
	
	/**
	 * Devuelve el setter con el nombre de propiedad dado a partir de un objeto.
	 * Devuelve null si no existe.
	 * @param object
	 * @param name
	 * @return
	 */
	public static Setter getByName(Object object, String name) {
		Method[] methods = object.getClass().getMethods();
		String methodName = getMethodNameFromPropertyName(name);
		for (int i = 0; i < methods.length; i++) {			
			if(methodName.equals(methods[i].getName())) {
				return new Setter(name, methods[i], object);
			}
		}
		return null;
	}

	private static String getMethodNameFromPropertyName(String name) {
		return "set"+name.substring(0, 1).toUpperCase()+name.substring(1);
	}
}