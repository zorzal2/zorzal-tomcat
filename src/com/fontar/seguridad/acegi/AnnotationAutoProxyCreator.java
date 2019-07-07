package com.fontar.seguridad.acegi;

import java.lang.annotation.Annotation;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;

public class AnnotationAutoProxyCreator extends AbstractAutoProxyCreator {

	
	private static final long serialVersionUID = -4552559748992104031L;

	@SuppressWarnings("unchecked")
	@Override
	protected Object[] getAdvicesAndAdvisorsForBean(Class clazz, String beanName, TargetSource target) throws BeansException {
		Annotation annotation = clazz.getAnnotation(SecuredService.class);
		if(annotation != null)
			return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
		else
			return DO_NOT_PROXY;
	}

}
