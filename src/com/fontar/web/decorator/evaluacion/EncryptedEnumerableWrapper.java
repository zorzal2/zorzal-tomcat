package com.fontar.web.decorator.evaluacion;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.ObjectUtils;

public class EncryptedEnumerableWrapper implements DisplaytagColumnDecorator {

	public Object decorate(Object object, PageContext arg1, MediaTypeEnum arg2) throws DecoratorException {
		String description = null;
		if( object == null)
			description = "";
		else
			description = ObjectUtils.encriptedEnumSafeGet( (EncryptedObject) object );
		
		return description;
	}

}
