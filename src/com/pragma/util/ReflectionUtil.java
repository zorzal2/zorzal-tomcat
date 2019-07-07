/**
 * 
 */
package com.pragma.util;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author fferrara
 * 
 */
public class ReflectionUtil {

	public static Object instance(String fqn) {
		try {
			return ((Class) Class.forName(fqn)).newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException("error instanciando clase", e);
			// TODO crear excepcion acorde a la utilidad
		}
	}

	public static Object getAttribute(Object entity, String attribute) {
		try {
			return PropertyUtils.getProperty(entity, attribute);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
			// TODO add utility exception
		}
	}

	public static void setAttribute(Object entity, String attribute, Object value) {
		try {
			PropertyUtils.setProperty(entity, attribute, value);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
			// TODO add utility exception
		}
	}

	public static Class getAttributeType(Object object, String visibleAttribute) {
		Method method;
		// get systems defautl securitymanager.
		SecurityManager securityManager = System.getSecurityManager();
		try {
			// remove system's security manager to avoid Securityexception .
			System.setSecurityManager(null);
			method = object.getClass().getMethod("get" + StringUtils.capitalize(visibleAttribute));
			return method.getReturnType();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			// restore system's default security manager.
			System.setSecurityManager(securityManager);
		}
	}

}
