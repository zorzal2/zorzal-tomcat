package com.pragma.util.dataConversion.converters;

import com.pragma.util.dataConversion.SimpleTypeConverter;

public class DoubleToLongConverter extends SimpleTypeConverter {

	@Override
	protected Class defaultSourceClass() {
		return Double.class;
	}

	@Override
	protected Class defaultDestClass() {
		return Long.class;
	}

	public Object convert(Object object, Class destClass) {
		return Math.round((Double)object);
	}

}
