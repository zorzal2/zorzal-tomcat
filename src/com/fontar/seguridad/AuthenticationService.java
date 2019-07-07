package com.fontar.seguridad;

import com.fontar.data.impl.domain.ldap.Usuario;

public interface AuthenticationService {

	public abstract String getUserName();

	public abstract String getUserId();

	public abstract Usuario getUserDetails();

}