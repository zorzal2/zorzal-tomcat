package com.fontar.web.seguridad.menu;

import java.util.Iterator;

import net.sf.navigator.menu.MenuComponent;

import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.SecurityConfig;
import org.acegisecurity.intercept.ObjectDefinitionSource;
import org.acegisecurity.vote.AuthenticatedVoter;

import com.fontar.seguridad.acegi.SimplePermissionSecurityConfig;

public class MenuDefinitionSource implements ObjectDefinitionSource {

	static final SecurityConfig IS_AUTHENTICATED_REMEMBERED = new SecurityConfig(AuthenticatedVoter.IS_AUTHENTICATED_REMEMBERED); 
	
	static final ConfigAttribute ANY_MENU =  new SimplePermissionSecurityConfig("MENU-ALL");
	
	public ConfigAttributeDefinition getAttributes(Object object) throws IllegalArgumentException {
		
		ConfigAttributeDefinition definition =  new ConfigAttributeDefinition();
		MenuComponent menuComponent = (MenuComponent) object;
		//Solo es requerido para los hijos
		if(menuComponent.getComponents().isEmpty())
			this.setConfigAttribute( menuComponent , definition);
		
		return definition;
	}
	
	private void setConfigAttribute(MenuComponent menuComponent, ConfigAttributeDefinition definition){
		definition.addConfigAttribute( ANY_MENU );
		
		//Esto es para soportar multiples entradas de menú con un mismo permiso. 
		String config=menuComponent.getName(); 
		if (menuComponent.getName().startsWith("MenuEntidades"))
			config= "MenuEntidades";
		
		definition.addConfigAttribute( new MenuPermissionSecurityConfig(config ));
	}

	public Iterator getConfigAttributeDefinitions() {
		return null;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(MenuComponent.class);
	}

}
