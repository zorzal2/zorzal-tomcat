package com.pragma.util.dataConversion.converters;

import java.math.BigDecimal;

import com.pragma.util.dataConversion.SimpleTypeConverter;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

public class BigDecimalToStringConverter extends SimpleTypeConverter {

	public Object convert(Object object, Class destClass) throws ConversionException {
		return object.toString();
	}

	protected Class defaultDestClass() {
		return String.class;
	}

	protected Class defaultSourceClass() {
		return BigDecimal.class;
	}

}
