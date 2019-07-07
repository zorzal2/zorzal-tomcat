package com.fontar.seguridad;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.fontar.data.api.domain.codes.Enumerable;
import com.fontar.seguridad.cripto.BigDecimalDecorator;
import com.fontar.util.Util;
import com.pragma.util.ReflectionUtil;

public class ObjectUtils {

	static public  final String ENCRIPTION_WARNING = "CRIPTO";
	static public  final String ENCRIPTION_EMPTY = "";
	
	public static String encriptedStringSafeGet(Object object, String propertyName){
		String value = null;
		try{
			value = (String) ReflectionUtil.getAttribute(object, propertyName);
		}catch(SecurityException e){
			value = ENCRIPTION_WARNING;
		}
		return value;
	}
	
	public static Boolean booleanSafeGet(Object object, String propertyName){
		Boolean value = null;
		try{
			value = (Boolean) PropertyUtils.getProperty(object, propertyName);
			
		}catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e) {
			value = false;
		}
		catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		return value;
	}
	
	public static String encriptedEnumSafeGet(EncryptedObject encryptedObject){
		
		String descripcion = null; 
		if(encryptedObject==null) return "";
		
		try{
			Enumerable enumerable = (Enumerable) encryptedObject.getObject();
			descripcion = (enumerable!=null)? enumerable.getDescripcion() : "";
		}catch(SecurityException e){
			descripcion = ENCRIPTION_WARNING;
		}
		return descripcion;
	}
	
	public static BigDecimal encriptedBigDecimalSafeGet(EncryptedObject encryptedObject){
		
		BigDecimal value = null; 
		
		try{
			value = (BigDecimal) encryptedObject.getObject();

		}catch(SecurityException e){
			
		}
		return value;
	}

	public static BigDecimal secureBigDecimal(EncryptedObject encryptedObject){
		return new BigDecimalDecorator(encryptedObject);
	}
	
	public static String nullSafeGet(Object object, String property){
		
		byte[] data = (byte[]) ReflectionUtil.getAttribute(object, property + "Data");
		if(data !=null){
			String propertyValue;
			try {
				propertyValue = BeanUtils.getProperty(object,property);
			}
			catch (Exception e) {
				return ENCRIPTION_WARNING;
			}
			return ( Util.isBlank( propertyValue) )? ENCRIPTION_WARNING : propertyValue;
			
		}else{
			return ENCRIPTION_EMPTY;
		}
		
		
	}
	
}
