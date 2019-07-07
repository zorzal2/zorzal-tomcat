package com.pragma.app;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class AppContextUtil {

	private static BeanFactory instance;

	private static void loadContext() {
		if (instance == null) {
			Resource res = new FileSystemResource("webroot/WEB-INF/conf/beans-context.xml");
			instance = new XmlBeanFactory(res);
		}
	}

	public static BeanFactory getBeanFactory() {

		loadContext();
		return instance;
	}

	public static Object getBean(String name) {

		return getBeanFactory().getBean(name);
	}
	
	public static void setBeanFactory(BeanFactory beanFactory){
		instance = beanFactory;
	}

}
