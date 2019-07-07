package com.pragma.util.dataConversion.converters;

import com.pragma.util.dataConversion.SimpleTypeConverter;

public class DoubleToIntegerConverter extends SimpleTypeConverter {

	@Override
	protected Class defaultSourceClass() {
		return Double.class;
	}

	@Override
	protected Class defaultDestClass() {
		return Integer.class;
	}

	public Object convert(Object object, Class destClass) {
		return Long.valueOf(Math.round((Double)object)).intValue();
	}

}
