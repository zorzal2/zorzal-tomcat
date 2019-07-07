package com.pragma.util.BeanUtils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pragma.util.Conditions;
import com.pragma.util.StringUtil;
import com.pragma.util.Conditions.Condition;
import com.pragma.util.dataConversion.ConversionUtils;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;
import com.pragma.util.exception.IllegalArgumentException;

public class BeanUtils {
	/**
	 * Copia los valores de las propiedades de un objeto que cumplen la condicion
	 * dada a otro objeto.
	 * Convierte si es necesario. Devuelve el objeto destino de la copia.
	 * @param src
	 * @param dest
	 * @return dest
	 * @throws ConversionException 
	 */
	public static Object copyProperties(Object src, Object dest, Condition<Object> condition) throws ConversionException{
		List<Getter> srcGetters = Getter.getAllFrom(src);
		for (Getter getter : srcGetters) {
			Setter destSetter = Setter.getByName(dest, getter.getName());
			if(destSetter!=null) {
				destSetter.setFromIfHolds(getter, condition);
			}
		}
		return dest; 
	}
	/**
	 * Setea todas las propiedades del diccionario dado en el objeto destino.
	 * El destino debe tener todos los setters requeridos. No se ignoran las
	 * propiedades que no existen.
	 * @param src
	 * @param dest
	 * @return
	 * @throws ConversionException
	 */
	public static Object setProperties(Map<String, Object> src, Object dest) throws ConversionException{
		for (Entry<String, Object> property : src.entrySet()) {
			Setter destSetter = Setter.getByName(dest, property.getKey());
			if(destSetter!=null) {
				destSetter.set(property.getValue());
			} else {
				throw new IllegalArgumentException();
			}
		}
		return dest; 
	}
	/**
	 * Copia solo las propiedades no nulas de un objeto a otro.
	 * @throws ConversionException 
	 */
	public static Object copyNotNullProperties(Object source, Object dest) throws ConversionException {
		return copyProperties(
			source,
			dest,
			Conditions.isNotNull()
		);
	}
	/**
	 * Copia los valores de las propiedades de un objeto a otro objeto.
	 * Convierte si es necesario. Devuelve el objeto destino de la copia.
	 * @param src
	 * @param dest
	 * @return dest
	 * @throws ConversionException 
	 */
	public static Object copyProperties(Object src, Object dest) throws ConversionException {
		if(src==null || dest==null) {
			throw new IllegalArgumentException("BeanUtils.copyProperties: src or dest objects are null.");
		}
		List<Getter> srcGetters = Getter.getAllFrom(src);
		for (Getter getter : srcGetters) {
			Setter destSetter = Setter.getByName(dest, getter.getName());
			if(destSetter!=null) {
				destSetter.setFrom(getter);
			}
		}
		return dest; 
	}
	/**
	 * Copia los valores de las propiedades de un objeto a otro objeto.
	 * Convierte si es necesario. Devuelve el objeto destino de la copia.
	 * Saltea las propiedades definidas como nombres separados por comas
	 * en el argumento skip
	 * @param src
	 * @param dest
	 * @param skip
	 * @return dest
	 * @throws ConversionException 
	 */
	public static Object copyProperties(Object src, Object dest, String skip) throws ConversionException{
		skip = ","+skip+",";
		if(src==null || dest==null) {
			throw new IllegalArgumentException("BeanUtils.copyProperties: src or dest objects are null.");
		}
		List<Getter> srcGetters = Getter.getAllFrom(src);
		for (Iterator iter = srcGetters.iterator(); iter.hasNext();) {
			Getter getter = (Getter) iter.next();
			if(skip.contains(","+getter.getName()+","))	continue;

			Setter destSetter = Setter.getByName(dest, getter.getName());
			if(destSetter!=null) {
				destSetter.setFrom(getter);
			}
		}
		return dest; 
	}
	
	/**
	 * Obtiene una propiedad simple: Ej. "proyecto.director.nombre"
	 * @param object
	 * @param name
	 * @return
	 */
	public static Object getProperty(Object object, String name) {
		if(name.equals("")) return object;
		String[] path = name.split("\\.");
		for(int i = 0; i<path.length; i++) {
			String getterName = "get"+StringUtil.toCamelCase(path[i]);
			try {
				object = getMethodWithNoParams(object, getterName).invoke(object);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException();
			}
		}
		return object;
	}
	
	public static void setProperty(Object object, String name, Object value) throws ConversionException {
		int lastDot = name.lastIndexOf('.');
		if(lastDot>0) {
			object = getProperty(object, name.substring(0, lastDot));
			name = name.substring(lastDot+1);
		}
		Setter setter = Setter.getByName(object, name);
		value = ConversionUtils.getDefault().convert(value, setter.getValueType());
		setter.set(value);
	}
	
	public static Method getStaticMethodWithNoParams(Class clazz, String methodName) throws SecurityException, NoSuchMethodException {
		Class[] parameterTypes = {};
		return clazz.getMethod(methodName, parameterTypes);
	}

	public static Method getStaticMethod(Class clazz, String methodName, Class param1) throws SecurityException, NoSuchMethodException {
		Class[] parameterTypes = {param1};
		return clazz.getMethod(methodName, parameterTypes);
	}

	public static Method getStaticMethod(Class clazz, String methodName, Class param1, Class param2) throws SecurityException, NoSuchMethodException {
		Class[] parameterTypes = {param1, param2};
		return clazz.getMethod(methodName, parameterTypes);
	}

	public static Method getStaticMethod(Class clazz, String methodName, Class param1, Class param2, Class param3) throws SecurityException, NoSuchMethodException {
		Class[] parameterTypes = {param1, param2, param3};
		return clazz.getClass().getMethod(methodName, parameterTypes);
	}
	
	public static Method getMethodWithNoParams(Object object, String methodName) throws SecurityException, NoSuchMethodException  {
		Class[] parameterTypes = {};
		return object.getClass().getMethod(methodName, parameterTypes);
	}
	public static Method getMethod(Object object, String methodName, Class param1) throws SecurityException, NoSuchMethodException {
		Class[] parameterTypes = {param1};
		return object.getClass().getMethod(methodName, parameterTypes);
	}
	public static Method getMethod(Object object, String methodName, Class param1, Class param2) throws SecurityException, NoSuchMethodException {
		Class[] parameterTypes = {param1, param2};
		return object.getClass().getMethod(methodName, parameterTypes);
	}

	public static Method getMethod(Object object, String methodName, Class param1, Class param2, Class param3) throws SecurityException, NoSuchMethodException {
		Class[] parameterTypes = {param1, param2, param3};
		return object.getClass().getMethod(methodName, parameterTypes);
	}

	public static Long getId(Object bean) {
		try {
			Object id = getMethodWithNoParams(bean, "getId").invoke(bean);
			if(id==null) return null;
			if (id instanceof String) {
				return Long.valueOf((String)id);				
			} else return (Long) id;
		} catch (Exception exeption) {
			throw new IllegalArgumentException("No se puede obtener el id del objeto");
		}
	}
}
