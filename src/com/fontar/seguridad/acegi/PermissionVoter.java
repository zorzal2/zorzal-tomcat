package com.fontar.seguridad.acegi;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.vote.RoleVoter;

@Deprecated
public class PermissionVoter extends RoleVoter {

	public PermissionVoter(){
		super();
		this.setRolePrefix("PERMISSION-");
	}
	
	/*** 
	 * Si no hay ningun atributo en la definicion entonces otorga el acceso, caso contrario
	 * delega la votacion a la super clase.
	 * **/
	@Override
	public int vote(Authentication authentication, Object object, ConfigAttributeDefinition config) {
		  return (config.size() == 0)? ACCESS_GRANTED : super.vote(authentication,object,config);
	}
	
}
