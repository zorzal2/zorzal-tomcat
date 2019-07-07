package com.pragma.util.dataConversion.converters;

import com.pragma.util.dataConversion.SimpleTypeConverter;
import com.pragma.util.dataConversion.ConversionUtils.ConversionException;

public class ObjectToStringConverter extends SimpleTypeConverter {

	public Object convert(Object object, Class destClass) throws ConversionException {
		return object.toString();
	}

	protected Class defaultDestClass() {
		return String.class;
	}

	protected Class defaultSourceClass() {
		return Object.class;
	}
}
