package com.pragma.util.dataConversion.converters;

import java.math.BigDecimal;

import com.pragma.util.dataConversion.SimpleTypeConverter;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

public class StringToBigDecimalConverter extends SimpleTypeConverter {

	public Object convert(Object object, Class destClass) throws ConversionException {
		if (object == null || object.toString().length() == 0)
			return null;
		else {
			try {
				return new BigDecimal(object.toString());
			} catch(Exception e) {
				throw new ConversionException("Cannot convert String \""+object+"\" to a BigDecimal number.");
			}
		}
	}

	protected Class defaultDestClass() {
		return BigDecimal.class;
	}

	protected Class defaultSourceClass() {
		return String.class;
	}
}
