package com.fontar.web.seguridad.menu;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.PermissionsAdapter;

import org.acegisecurity.AccessDecisionManager;
import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.intercept.ObjectDefinitionSource;

import com.fontar.seguridad.acegi.AccessController;

public class MenuPermissionAdapter   extends AccessController implements PermissionsAdapter {

	
	public MenuPermissionAdapter(AccessDecisionManager accessDecisionManager, ObjectDefinitionSource objectDefinitionSource){
		super(accessDecisionManager , objectDefinitionSource);
	}
	
	/**
     * If the menu is allowed, this should return true.
     *
     * @return whether or not the menu is allowed.
     */
    public boolean isAllowed(MenuComponent menuComponent) {
    	try{
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    		this.getAccessDecisionManager().decide( authentication, menuComponent , this.getObjectDefinitionSource().getAttributes(menuComponent));
    	}catch (AccessDeniedException e) {
    		return false;
		}
    	return true;
    }

}
