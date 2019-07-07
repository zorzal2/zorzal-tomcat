package com.fontar.web.seguridad.displayTag;

import java.util.Iterator;

import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.intercept.ObjectDefinitionSource;

public class TableDecoratorDefinitionSource implements ObjectDefinitionSource {

	
	public ConfigAttributeDefinition getAttributes(Object object) throws IllegalArgumentException {
		
		
		RestrictedLink restrictedLink = (RestrictedLink)object;
		ConfigAttributeDefinition definition =  new ConfigAttributeDefinition();
		if(restrictedLink.getSecurityAttributesRequired()!=null)
			for(ConfigAttribute attribute : restrictedLink.getSecurityAttributesRequired()){
				definition.addConfigAttribute(attribute);
			}			
		return definition;
	}
	

	public Iterator getConfigAttributeDefinitions() {
		return null;
	}

	public boolean supports(Class clazz) {
		return true;//FIXME
	}

}
