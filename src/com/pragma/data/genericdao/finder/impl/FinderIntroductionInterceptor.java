package com.pragma.data.genericdao.finder.impl;

import java.lang.annotation.Annotation;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;

import com.pragma.data.genericdao.finder.FinderExecutor;
import com.pragma.util.annotation.HasNamedParams;
import com.pragma.util.annotation.ParamName;

/**
 * Connects the Spring AOP magic with the Hibernate DAO magic For any method
 * beginning with "find" this interceptor will use the FinderExecutor to call a
 * Hibernate named query
 */
public class FinderIntroductionInterceptor implements IntroductionInterceptor {

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {

		FinderExecutor<?> executor = (FinderExecutor) methodInvocation.getThis();

		String methodName = methodInvocation.getMethod().getName();
		if (methodName.startsWith("find") || methodName.startsWith("list")) {
			return executor.executeFinder(methodInvocation.getMethod(), argMap(methodInvocation));
		}
		else if (methodName.startsWith("iterate")) {
			return executor.iterateFinder(methodInvocation.getMethod(), argMap(methodInvocation));
		}
		else if (methodName.startsWith("select")) {
			return executor.executeSelect(methodInvocation.getMethod(), argMap(methodInvocation));
		}
		// else if(methodName.startsWith("scroll"))
		// {
		// Object[] arguments = methodInvocation.getArguments();
		// return executor.scrollFinder(methodInvocation.getMethod(),
		// arguments);
		// }
		else {
			return methodInvocation.proceed();
		}
	}

	public boolean implementsInterface(Class intf) {
		return intf.isInterface() && FinderExecutor.class.isAssignableFrom(intf);
	}
	
	private ArgumentMap argMap(MethodInvocation invocation) {
		ArgumentMap ret = new ArgumentMap();
		//Busco en la clase la marca que indica que tiene parametros por nombre
		boolean hasNotNamedArguments = invocation.getMethod().getDeclaringClass().getAnnotation(HasNamedParams.class)==null;
		if(hasNotNamedArguments) {
			ret.add(invocation.getArguments());
			return ret;
		}
		//La clase soporta parametros con nombre
		Object[] arguments = invocation.getArguments();
		if(arguments==null) return ret;
		Annotation[][] parameterAnnotations = invocation.getMethod().getParameterAnnotations();
		
		for(int i = 0; i<arguments.length; i++) {
			//Busco el nombre del parametro en una anotacion @ParamName.
			String paramName = null;
			for(Annotation annotation : parameterAnnotations[i]) {
				if(annotation instanceof ParamName)
					paramName = ((ParamName)annotation).value();
			}
			ret.add(paramName, arguments[i]);
		}
		return ret;
	}
}
