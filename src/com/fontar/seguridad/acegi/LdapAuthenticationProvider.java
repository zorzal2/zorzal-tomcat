package com.fontar.seguridad.acegi;

import org.acegisecurity.providers.ldap.LdapAuthenticator;
import org.acegisecurity.providers.ldap.LdapAuthoritiesPopulator;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;

import com.fontar.data.impl.domain.ldap.Usuario;

public class LdapAuthenticationProvider extends org.acegisecurity.providers.ldap.LdapAuthenticationProvider {

	public LdapAuthenticationProvider(LdapAuthenticator authenticator, LdapAuthoritiesPopulator authoritiesPopulator) {
		super(authenticator, authoritiesPopulator);
	}

	@Override
	protected UserDetails createUserDetails(LdapUserDetails ldapUser, String username, String password) {
		Usuario ldapUsuario = (Usuario) ldapUser;
		Usuario usuario = new Usuario();
		usuario.setLdapUserDetails( (LdapUserDetails) super.createUserDetails(ldapUser, username, password) );
		usuario.setCertifiedPublicKeyByteArray( ldapUsuario.getCertifiedPublicKeyByteArray() );
		return usuario;
	}
	
	

}
