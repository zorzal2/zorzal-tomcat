package com.fontar.seguridad;

import org.acegisecurity.context.SecurityContextHolder;

import com.fontar.data.impl.domain.ldap.Usuario;

public class AuthenticationServiceImpl extends AuthorizationServiceImpl implements AuthenticationService {

	
	/* (non-Javadoc)
	 * @see com.fontar.seguridad.AuthenticationService#getUserName()
	 */
	public String getUserName() {
		return this.getUserDetails().getUsername();
	}

	
	/* (non-Javadoc)
	 * @see com.fontar.seguridad.AuthenticationService#getUserId()
	 */
	public String getUserId() {
		return this.getUserName();
	}

	/* (non-Javadoc)
	 * @see com.fontar.seguridad.AuthenticationService#getUserDetails()
	 */
	public Usuario getUserDetails() {
		return (Usuario) this.getPrincipal();
	}
	
	private Object getPrincipal(){
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
