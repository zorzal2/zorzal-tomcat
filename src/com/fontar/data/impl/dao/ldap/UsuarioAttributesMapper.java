package com.fontar.data.impl.dao.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.acegisecurity.userdetails.ldap.LdapUserDetailsImpl;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsMapper;
import org.springframework.ldap.AttributesMapper;

import com.fontar.data.impl.domain.ldap.UserStatus;
import com.fontar.data.impl.domain.ldap.Usuario;

public class UsuarioAttributesMapper implements AttributesMapper {

	private LdapUserDetailsMapper userDetailsMapper = new LdapUserDetailsMapper();

	
	private String buildDn(String uid){
		return "uid=" + uid + ", ou=Usuarios, o=fontar, dc=org";
		
	}

	public Object mapFromAttributes(Attributes attributes) throws NamingException {
		Usuario usuario = new Usuario();
		String dn = this.buildDn((String) attributes.get("uid").get());
		LdapUserDetailsImpl.Essence user = (LdapUserDetailsImpl.Essence) this.userDetailsMapper.mapAttributes(dn,attributes);
		user.setUsername( (String) attributes.get("uid").get());
		usuario.setLdapUserDetails( user.createUserDetails() );
		usuario.setNombre((String) attributes.get("cn").get());
		usuario.setApellido((String) attributes.get("sn").get());
		Attribute status = attributes.get("userStatus");
		if(status!=null)
        	usuario.setStatus( UserStatus.valueOf( this.getString( status)));
        
        Attribute retrievedPublicKey = attributes.get("publicKey");
        if(retrievedPublicKey!=null)
        	usuario.setPublicKeyByteArray( this.getBytes( retrievedPublicKey) );
        
   
        Attribute retrievedCertifiedPublicKey = attributes.get("certifiedPublicKey");
        if(retrievedCertifiedPublicKey!=null)
        	usuario.setPublicKeyByteArray( this.getBytes( retrievedCertifiedPublicKey) );
        
        return usuario;
	}
	
	private byte[] getBytes(Attribute attribute) throws NamingException{
		String value = (String) attribute.get();
		return value.getBytes();
	}
	
	private String getString(Attribute attribute) throws NamingException{
		return (String) attribute.get();
	}

}
