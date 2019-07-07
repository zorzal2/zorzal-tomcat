package com.pragma.util;

import org.springframework.beans.factory.BeanFactory;

import com.pragma.app.AppContextUtil;
import com.pragma.web.WebContextUtil;

public class ContextUtil {

	public static boolean hasWebContext(){
		return WebContextUtil.getBeanFactory()!=null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {

		if (WebContextUtil.getBeanFactory() != null) {

			return (T)WebContextUtil.getBeanFactory().getBean(name);
		}
		else {
			return (T)AppContextUtil.getBean(name);
		}
	}

	public static BeanFactory getBeanFactory() {

		if (WebContextUtil.getBeanFactory() != null) {

			return WebContextUtil.getBeanFactory();
		}
		else {
			return AppContextUtil.getBeanFactory();
		}

	}
}
