package com.fontar.web.seguridad;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttributeDefinition;

import com.fontar.seguridad.acegi.AccessController;

public class AuthorizationTagAccessController  extends AccessController {

	

	public Boolean decide(AuthorizationTag authorizationTag){
		
		ConfigAttributeDefinition defintion = this.getObjectDefinitionSource().getAttributes( authorizationTag );
		Authentication authentication = this.getAuthentication();
		
		try {
		 this.getAccessDecisionManager().decide( authentication , authorizationTag , defintion );
		}catch(AccessDeniedException e){
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}
