package com.fontar.web.seguridad;

import java.lang.reflect.Method;

import org.acegisecurity.ConfigAttributeDefinition;

public class MethodDefinitionAttributes extends org.acegisecurity.intercept.method.MethodDefinitionAttributes {

	@Override
	protected ConfigAttributeDefinition lookupAttributes(Method method) {
		// TODO Auto-generated method stub
		return super.lookupAttributes(method);
	}

	
	
}
