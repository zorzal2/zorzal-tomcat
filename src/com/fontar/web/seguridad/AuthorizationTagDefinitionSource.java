package com.fontar.web.seguridad;

import java.util.Iterator;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.intercept.ObjectDefinitionSource;

import com.fontar.seguridad.acegi.SimplePermissionSecurityConfig;

public class AuthorizationTagDefinitionSource implements ObjectDefinitionSource {

	public ConfigAttributeDefinition getAttributes(Object object) throws IllegalArgumentException {
		ConfigAttributeDefinition definition =  this.getAttributes( (AuthorizationTag) object );
		definition.addConfigAttribute(new SimplePermissionSecurityConfig("ANY_LINK"));
		return definition;
	}
	
	private ConfigAttributeDefinition getAttributes(AuthorizationTag tag) throws IllegalArgumentException {
		ConfigAttributeDefinition definition =  new ConfigAttributeDefinition();
		String[] permissions = tag.getPermissionsRequired();
		for(int i=0;i<permissions.length;i++){
			definition.addConfigAttribute( new SimplePermissionSecurityConfig(permissions[i]) );
		}
		return definition;
	}
	
	public Iterator getConfigAttributeDefinitions() {
		return null;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(AuthorizationTag.class);
	}

}
