package com.fontar.web.seguridad;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;



public class SecurityAnnotationAttributes extends org.acegisecurity.annotation.SecurityAnnotationAttributes {

	@Override
	public Collection getAttributes(Class arg0, Class arg1) {
		// TODO Auto-generated method stub
		return super.getAttributes(arg0, arg1);
	}

	@Override
	public Collection getAttributes(Class arg0) {
		// TODO Auto-generated method stub
		return super.getAttributes(arg0);
	}

	@Override
	public Collection getAttributes(Field arg0, Class arg1) {
		// TODO Auto-generated method stub
		return super.getAttributes(arg0, arg1);
	}

	@Override
	public Collection getAttributes(Field arg0) {
		// TODO Auto-generated method stub
		return super.getAttributes(arg0);
	}

	@Override
	public Collection getAttributes(Method arg0, Class arg1) {
		// TODO Auto-generated method stub
		return super.getAttributes(arg0, arg1);
	}

	@Override
	public Collection getAttributes(Method arg0) {
		// TODO Auto-generated method stub
		return super.getAttributes(arg0);
	}

	
	
	
	
}
