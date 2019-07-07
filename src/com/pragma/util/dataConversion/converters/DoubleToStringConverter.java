package com.pragma.util.dataConversion.converters;

import com.pragma.util.dataConversion.SimpleTypeConverter;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

public class DoubleToStringConverter extends SimpleTypeConverter {

	/**
	 * Si el doble representa un numero se muestra sin decimales.
	 */
	public Object convert(Object object, Class destClass) throws ConversionException {
		double d = (Double) object;
		long l = Math.round(d);
		if(d==l) return Long.toString(l);
		else return object.toString();
	}

	protected Class defaultDestClass() {
		return String.class;
	}

	protected Class defaultSourceClass() {
		return Object.class;
	}
}
