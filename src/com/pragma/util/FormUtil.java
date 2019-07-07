package com.pragma.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;

public class FormUtil {

	public static Boolean getCheckboxValue(ActionForm form, String campo) throws Exception {

		String value = BeanUtils.getProperty(form, campo);
		return new Boolean(value.equals("on"));
	}

	public static Date getDateValue(ActionForm form, String campo) throws Exception {
		String value = BeanUtils.getProperty(form, campo);

		if (StringUtil.isNotEmpty(value)) {
			return DateTimeUtil.getDate(value);
		}
		return null;

	}
	
	public static Integer getIntegerValue(ActionForm form, String campo) throws Exception {
		
		String value = BeanUtils.getProperty(form, campo);
		
		if (StringUtil.isNotEmpty(value)) {
			return new Integer(value);
		}
		return null;
	}

	public static BigDecimal getBigDecimalValue(ActionForm form, String campo) throws Exception {
		String value = BeanUtils.getProperty(form, campo);
		if (StringUtil.isNotEmpty(value)) {
			return new BigDecimal(value);
		}
		return null;

	}

	public static Long getLongValue(ActionForm form, String campo) throws Exception {
		String value = BeanUtils.getProperty(form, campo);
		if (StringUtil.isNotEmpty(value)) {
			return new Long(value);
		}
		return null;
	}

	/**
	 * Devuelve siempre un booleano. Si el campo del formulario esta vacío
	 * devuelve false. Nunca devuelve null. 
	 * @param form Formulario de origen de datos
	 * @param campo Atributo de origen de datos
	 * @return
	 * @throws Exception
	 */
	public static Boolean getBooleanValueNotNull(ActionForm form, String campo) throws Exception {
		Boolean value = getBooleanValue(form, campo);
		if (value == null) {
			return false;
		} else return value;
	}

	public static Boolean getBooleanValue(ActionForm form, String campo) throws Exception {
		String value = BeanUtils.getProperty(form, campo);
		if (StringUtil.isNotEmpty(value)) {
			return new Boolean(value);
		}
		return null;
	}

	public static String getStringValue(ActionForm form, String campo) throws Exception {
		String value = BeanUtils.getProperty(form, campo);
		if (StringUtil.isNotEmpty(value)) {
			return value;
		}
		return null;
	}

	public static String[] getStringArrayValue(ActionForm form, String campo) throws Exception {

		String[] value = BeanUtils.getArrayProperty(form, campo);
		// if (value!=null && value.getClass().equals(String.class)) {
		// return new String[]{value.toString()};
		// }
		//		
		if ((value != null) && (value.length != 0)) {
			return value;
		}
		return null;
	}

	public static void copyProperties(Object target, Object source) throws Exception {

		Method[] metodosSource = source.getClass().getMethods(); // getMethod(GetMethodId(),
																	// null).invoke(bean,
																	// null);
		Method[] metodosTarget = source.getClass().getMethods();
		for (Method metodo : metodosSource) {

			if (metodo.getName().startsWith("get")) {

				String atributo = metodo.getName().replaceFirst("get", "");
				String atributoFinal = atributo.substring(0, 1).toLowerCase() + atributo.substring(1);
				Object sourceTarget = metodo.invoke(source, (Object[])null);

				if (sourceTarget != null && sourceTarget.getClass().equals(Timestamp.class)) {

					sourceTarget = DateTimeUtil.formatDate((Date) sourceTarget);
				}
				if (containMethod(metodosTarget, "set" + atributo) != null) {
					BeanUtils.setProperty(target, atributoFinal, sourceTarget);
				}
			}

		}
	}

	private static Object containMethod(Method[] metodosTarget, String string) {

		for (Method metodo : metodosTarget) {
			if (metodo.getName().equals(string)) {
				return true;
			}
		}
		return false;
	}

	//
	// public static LocalizacionDTO getLocalizationDTO(ActionForm form, String
	// campo) throws Exception
	// {
	// String value = BeanUtils.getProperty(form, campo);
	// if (StringUtils.isNotEmpty(value)) {
	// return new LocalizacionDTO(value);
	// }
	// return null;
	//
	// }

	public static String[] getStringArray(ActionForm form, String campo) throws Exception {
		String[] arrayString = BeanUtils.getArrayProperty(form, campo);
		if (arrayString != null) {
			return arrayString;
		}
		return null;
	}

	/**
	 * Obtiene un array de <i>BigDecimal</i>
	 * des una array de <code>String</code>.<br>
	 * @param form
	 * @param campo
	 * @return array de BigDecimal
	 * @throws Exception
	 */
	public static BigDecimal[] getBigDecimalArray(ActionForm form, String campo) throws Exception {

		String[] arrayString = BeanUtils.getArrayProperty(form, campo);
		BigDecimal[] arrayBigDecimal = null;
		
		if (arrayString != null) {
			for (int i = 0; i < arrayString.length; i++) {
				arrayBigDecimal[i] = new BigDecimal(arrayString[i]);
			}
		}
		
		return null;
	}	
}
