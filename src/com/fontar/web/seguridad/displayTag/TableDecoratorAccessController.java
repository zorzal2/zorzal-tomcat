package com.fontar.web.seguridad.displayTag;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttributeDefinition;

import com.fontar.seguridad.acegi.AccessController;

public class TableDecoratorAccessController extends AccessController  {

	
	public Boolean decide(RestrictedLink restrictedLink){
		
		ConfigAttributeDefinition defintion = this.getObjectDefinitionSource().getAttributes( restrictedLink );
		Authentication authentication = this.getAuthentication();
		
		try {
		 this.getAccessDecisionManager().decide( authentication , restrictedLink , defintion );
		}catch(AccessDeniedException e){
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	
}
