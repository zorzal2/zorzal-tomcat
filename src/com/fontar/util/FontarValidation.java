package com.fontar.util;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.FieldChecks;
import org.apache.struts.validator.Resources;

import com.fontar.bus.api.configuracion.UsuarioService;
import com.pragma.util.ContextUtil;
import com.pragma.util.DateTimeUtil;
import com.pragma.util.StringUtil;

public class FontarValidation extends FieldChecks implements Serializable {

	// Constantes
	static final int MIN_DAYS = 1;
	static final int MAX_DAYS = 99;
	static final double MAX_CURRENCY = 999999999999999.99;
	static final double MIN_CURRENCY = -999999999999999.99;
	static final int MIN_ANIO = 1900;
	static final int MAX_ANIO = 9999;
	static final int MAX_CHAR_3500 = 3500;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static 	Pattern DATE_PATTERN = Pattern.compile("\\d{1}(\\d{1})*/\\d{1}(\\d{1})*/(\\d{4})");
	
	public FontarValidation() {
		super();
	}

	public static boolean validateLocalizacion(Object bean, ValidatorAction va, Field field, ActionErrors errors,
			HttpServletRequest request) {
		return true;
	}

	public boolean validateDateGreaterThan(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {

		String secondDateString = ValidatorUtils.getValueAsString(bean, field.getProperty());
		String firstDateString = ValidatorUtils.getValueAsString(bean, field.getVarValue("dateGreaterThan"));
		if (!GenericValidator.isBlankOrNull(firstDateString) && !GenericValidator.isBlankOrNull(secondDateString)) {
			try {
				Date firstDate = DateTimeUtil.getDate(firstDateString);
				Date seconDate = DateTimeUtil.getDate(secondDateString);
				if (!seconDate.after(firstDate)) {
					errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
					return false;
				}
			}
			catch (ParseException e) {
				return false;
			}
		}
		return true;
	}

	public static boolean validatePorcentaje(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				// Chequea que sea un double y que este entre 0 y 100
				Double currentValue = GenericTypeValidator.formatDouble(value);
				if (currentValue == null || !GenericValidator.isInRange(currentValue, 0, 100)) {
					errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
					return false;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateDay(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				// Chequea que sea un entero y que este entre el minimo y maximo de dias
				Integer currentValue = GenericTypeValidator.formatInt(value);
				if (currentValue == null || !GenericValidator.isInRange(currentValue, MIN_DAYS, MAX_DAYS)) {
					errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
					return false;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateNumPos(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				Integer numValue = GenericTypeValidator.formatInt(value);
				if (numValue == null || numValue < 0) {
					errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
					return false;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean validateCurrency(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {
		
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if(value.indexOf(" ") >= 0) {
			errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			return false;
		}
		if(value.indexOf("d") > 0) {
			errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			return false;
		}
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				// Chequea que sea un double y que este entre 0 y el maximo permitido para currency
				Double currentValue = GenericTypeValidator.formatDouble(value);
				if (currentValue == null || !GenericValidator.isInRange(currentValue, 0, MAX_CURRENCY)) {
					errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
					return false;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean validateImporte(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {
		
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if(value.indexOf(" ") >= 0) {
			errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			return false;
		}
		if(value.indexOf("d") > 0) {
			errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			return false;
		}
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				// Chequea que sea un double y que este entre 0 y el maximo permitido para currency
				Double currentValue = GenericTypeValidator.formatDouble(value);
				if (currentValue == null || !GenericValidator.isInRange(currentValue, MIN_CURRENCY, MAX_CURRENCY)) {
					errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
					return false;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateNonZero(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if(GenericValidator.isBlankOrNull(value)) return true;
		
		Double dbl = (Double) validateDouble(bean, va, field, errors, validator, request);
		if(dbl==null || dbl.doubleValue()<=0.0) {
			errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			return false;
		} else return true;
	}

	public static boolean validateAnio(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				Integer currentValue = GenericTypeValidator.formatInt(value);
				if (currentValue == null || !GenericValidator.isInRange(currentValue, MIN_ANIO, MAX_ANIO)) {
					errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
					return false;
				}
			}
			catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateMayuscula(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (!GenericValidator.isBlankOrNull(value)) {
		}
		return true;
	}
	
	public static boolean validateMaxLength3500(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {
		
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		
		if (!GenericValidator.isBlankOrNull(value)) {
			if(value.length() > MAX_CHAR_3500) {
				errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
				return false;
			}
		}
		return true;
	}

	public static boolean validateMaxLength(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {

		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		int maxLength = Integer.parseInt(field.getVar("maxLength").getValue());
		if (!GenericValidator.isBlankOrNull(value)) {
			if(value.length() > maxLength) {
				errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
				return false;
			}
		}
		return true;
	}
	
	public static Object validateDate(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {
		 String value = evaluateBean(bean, field);
		 if(!GenericValidator.isBlankOrNull( value)){
			 Matcher dateMatcher = DATE_PATTERN.matcher(value);
			 if(!dateMatcher.matches()){
				 errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
				 return Boolean.FALSE;
			 }else{
				 if(field.getVarValue("datePattern")==null)
					 field.addVar("datePattern", DateTimeUtil.DATE_FORMAT,"String");
				 return FieldChecks.validateDate(bean,va,field,errors,validator,request);
			 }
		 }
		 return FieldChecks.validateDate(bean,va,field,errors,validator,request);
	}
	
	public static Object validateUnico(Object bean, ValidatorAction va, Field field, ActionMessages errors,
			Validator validator, HttpServletRequest request) {
		 String value = evaluateBean(bean, field);
//		 if(!GenericValidator.isBlankOrNull( value)){
//			 Matcher dateMatcher = DATE_PATTERN.matcher(value);
//			 if(!dateMatcher.matches()){
//				 errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
//				 return Boolean.FALSE;
//			 }else{
//				 return FieldChecks.validateDate(bean,va,field,errors,validator,request);
//			 }
//		 }
		 return value;
	}
	
	 private static String evaluateBean(Object bean, Field field) {
	        String value;

	        if (isString(bean)) {
	            value = (String) bean;
	        } else {
	            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
	        }

	        return value;
	    }
	 
	 public boolean validatePassword(Object bean, ValidatorAction va, Field field, ActionMessages errors,
				Validator validator, HttpServletRequest request) {

			String passwordConfirmation = ValidatorUtils.getValueAsString(bean, "password");
			String password = ValidatorUtils.getValueAsString(bean, "confPassword");
			if (! Util.notNullEquals( password, passwordConfirmation)) {
				errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
				return false;
			}
			return true;
		}
	 
	 /**
	 ** Controla que el parametro sea una coleccion con al menos un elemento
	 **/
	 public static Object validateCollectionRequired(Object bean, ValidatorAction va, Field field, ActionMessages errors,
				Validator validator, HttpServletRequest request) {
		 	String[] collection;
			try {
				collection = BeanUtils.getArrayProperty(bean, field.getProperty());
				if(collection != null && collection.length > 0)
			 		return true;
			 	else{
			 		errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			 		return false;
			 	}

			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
	}
	 
	 public static Object validateUniqueUserIdentifier(Object bean, ValidatorAction va, Field field, ActionMessages errors,
				Validator validator, HttpServletRequest request) {
		 
		 String userIdentifier = ValidatorUtils.getValueAsString(bean, field.getProperty());
		 UsuarioService usuarioService = (UsuarioService) ContextUtil.getBean("usuarioService");
		 if(usuarioService.availableIdentifier(userIdentifier))
			 return true;
		 else{
		 	errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
		 	return false;
		 }		 
	 }
	 	 
	 
	 public static Object validateAccent (Object bean, ValidatorAction va, Field field, ActionMessages errors,
				Validator validator, HttpServletRequest request) {
		 
		 //TODO reemplzar por una expresionr regular y controlar otros caracteres
		 String value  = ValidatorUtils.getValueAsString(bean, field.getProperty());
		 if(value.contains("á") || value.contains("é") || value.contains("í")  || value.contains("ó")  || value.contains("ú") ){
		 	 errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			 return false;
		 }
		 return true;
	 }
	 
	 public static Object alphaNumericSpace(Object bean, ValidatorAction va, Field field, ActionMessages errors,
				Validator validator, HttpServletRequest request) {
		 String value  = ValidatorUtils.getValueAsString(bean, field.getProperty());
		 if( !StringUtil.isAlphanumericSpace(value)){
		 	 errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			 return false;
		 }
		 return validateAccent(bean,va,field,errors,validator,request);
	 }
	 
	 public static Object esSinEspacio(Object bean, ValidatorAction va, Field field, ActionMessages errors,
				Validator validator, HttpServletRequest request) {
		 String value  = ValidatorUtils.getValueAsString(bean, field.getProperty());
		 if( !StringUtil.esSinEspacio(value)){
		 	 errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			 return false;
		 }
		 return validateAccent(bean,va,field,errors,validator,request);
	 }
	 
	 public static Object esMayuscula(Object bean, ValidatorAction va, Field field, ActionMessages errors,
				Validator validator, HttpServletRequest request) {
		 String value  = ValidatorUtils.getValueAsString(bean, field.getProperty());
		 if( !StringUtil.esMayuscula(value)){
		 	 errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			 return false;
		 }
		 return validateAccent(bean,va,field,errors,validator,request);
	 }
	 
	 public static Object esNroProyecto(Object bean, ValidatorAction va, Field field, ActionMessages errors,
				Validator validator, HttpServletRequest request) {
		 String value  = ValidatorUtils.getValueAsString(bean, field.getProperty());
		 if( (!StringUtil.esMayuscula(value)) || (!StringUtil.esSinEspacio(value))){
		 	 errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
			 return false;
		 }
		 return validateAccent(bean,va,field,errors,validator,request);
	 }
	 
	 public static boolean isDate(String date , boolean optional){
		 if(!GenericValidator.isBlankOrNull( date)){
			 Matcher dateMatcher = DATE_PATTERN.matcher(date);
			 if(dateMatcher.matches()){
				 return GenericTypeValidator.formatDate(date, DateTimeUtil.DATE_FORMAT, false)!=null;
			 }else
				 return false;
		 }
		 return optional;
	 }
}