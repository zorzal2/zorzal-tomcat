package com.fontar.util.bean;

import java.util.Date;

import org.apache.commons.beanutils.Converter;

import com.pragma.util.DateTimeUtil;

public class StringConverter implements Converter {

	Converter converter;

	public StringConverter(Converter converter) {
		super();
		this.converter = converter;
	}

	public Object convert(Class target, Object object) {
		if (object instanceof Date)
			return DateTimeUtil.formatDate((Date) object);
		return this.converter.convert(target, object);

	}

}
