package com.fontar.util.bean;

import java.util.Date;

import javax.servlet.ServletException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

public class BeanUtilsConverterPlugin implements PlugIn {

	public void destroy() {
		ConvertUtils.deregister(Date.class);
	}

	public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
		Converter stringConverter = ConvertUtils.lookup(String.class);
		Converter extendedStringConverter = new StringConverter(stringConverter);
		ConvertUtils.register(extendedStringConverter, String.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new LongConverter(null), Long.TYPE);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.TYPE);
		ConvertUtils.register(new DateConverter(), Date.class);
	}

}
