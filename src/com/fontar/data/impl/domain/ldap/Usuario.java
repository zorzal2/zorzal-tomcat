/*
 * Copyright 2005-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fontar.data.impl.domain.ldap;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

import javax.naming.directory.Attributes;
import javax.naming.ldap.Control;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsImpl;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fontar.data.impl.dao.ldap.GrupoConInstrumentoDao;
import com.fontar.data.impl.dao.ldap.GrupoDao;
import com.pragma.util.ContextUtil;

/**
 * Simple class representing an access group.
 * 
 * @author Ulrik Sandberg
 */
public class Usuario implements Serializable  , LdapUserDetails {
    
	
	private static final long serialVersionUID = 8454418324353951084L;
	
	private LdapUserDetails ldapUserDetails;

	private String nombre;
	private String apellido;
	private Set<GrupoAbstracto> grupos;
	private byte[] publicKeyByteArray;
	private byte[] certifiedPublicKeyByteArray;
	
	//KeyPair
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	private UserStatus status;


	public static String buildDn(String uid){
		return "uid=" + uid + ", ou=Usuarios, o=fontar, dc=org";
	}
	
    public LdapUserDetails getLdapUserDetails() {
		return ldapUserDetails;
	}

	public void setLdapUserDetails(LdapUserDetails ldapUserDetails) {
		this.ldapUserDetails = ldapUserDetails;
	}

	public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Set getGrupos() {
		return this.grupos!=null ? grupos : this.inicializarGrupos();
	}

	@SuppressWarnings("unchecked")
	private Set inicializarGrupos() {
		GrupoDao grupoDao = (GrupoDao) ContextUtil.getBean("grupoLdapDao");
		GrupoConInstrumentoDao grupoConInstrumentoDao = (GrupoConInstrumentoDao) ContextUtil.getBean("grupoConInstrumentoLdapDao");
		this.grupos = new HashSet();
		this.grupos.addAll(grupoDao.getGroupMembership( this.getDnAsString()));
		this.grupos.addAll(grupoConInstrumentoDao.getGroupMembership( this.getDnAsString()));
		return this.grupos;
	}

	public void setGrupos(Set<GrupoAbstracto> grupos) {
		this.grupos = grupos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return this.ldapUserDetails.getPassword();
	}

	@Deprecated
	public void setPassword(String password) {

	}

	public String getUserId() {
		return this.getUsername();
	}

	@Deprecated
	public void setUserId(String userId) {
	
	}

	public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

	public String getDnAsString() {
		return this.getDn() == null ? buildDn(this.getUserId()) : this.getDn();
	}

	public String getNombreCompleto(){
		return this.apellido + ", " + this.nombre;
	}


	public Boolean initialized(){
		return this.status!=null && this.status.equals( UserStatus.INISTIALIZED);
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public boolean isInitialized() {
		return this.initialized();
	}

	public byte[] getCertifiedPublicKeyByteArray() {
		return certifiedPublicKeyByteArray;
	}

	public void setCertifiedPublicKeyByteArray(byte[] certifiedPublicKeyByteArray) {
		this.certifiedPublicKeyByteArray = certifiedPublicKeyByteArray;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
		this.publicKeyByteArray = publicKey.getEncoded();
	}

	public byte[] getPublicKeyByteArray() {
		return publicKeyByteArray;
	}

	public void setPublicKeyByteArray(byte[] publicKeyByteArray) {
		this.publicKeyByteArray = publicKeyByteArray;
	}

	public Attributes getAttributes() {
		return ldapUserDetails.getAttributes();
	}

	public GrantedAuthority[] getAuthorities() {
		return ldapUserDetails.getAuthorities();
	}

	public Control[] getControls() {
		return ldapUserDetails.getControls();
	}

	public String getDn() {
		return ldapUserDetails.getDn();
	}

	public String getUsername() {
		return ldapUserDetails.getUsername();
	}

	
	public boolean isAccountNonExpired() {
		return ldapUserDetails.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return ldapUserDetails.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return ldapUserDetails.isCredentialsNonExpired();
	}

	public boolean isEnabled() {
		return ldapUserDetails.isEnabled();
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	
	public static class Essence {
		LdapUserDetailsImpl.Essence essence  = new LdapUserDetailsImpl.Essence();
		Usuario instance =  createTarget();
		
		public void setDn(String dn) {
			essence.setDn(dn);
		}
		
		private Usuario createTarget() {
			Usuario usuario = new Usuario();
			if(usuario.status == null)
				usuario.setStatus(UserStatus.NEW);
			return usuario;
		}

		public void setPassword(String password) {
			essence.setPassword(password);
		}
		
		public void setUsername(String username) {
			essence.setUsername(username);
		}
		
		public void setNombre(String nombre){
			instance.nombre = nombre;
		}
		
		public void setApellido(String apellido){
			instance.apellido = apellido;
		}
		
		public void setGrupos(Set<GrupoAbstracto> grupos ){
			instance.grupos = grupos;
		}
		
		public void setStatus(UserStatus userStatus){
			instance.status = userStatus;
		}
		
		public Usuario createUserDetails() {
			Usuario newInstance = instance;
			newInstance.setLdapUserDetails( essence.createUserDetails() );
			essence = null;
			instance = null;
			return newInstance;
		}

		public void setUserId(String username) {
			essence.setUsername( username);
		}

		public void setPublicKey(PublicKey publicKey) {
			instance.publicKey = publicKey;
		}

		public void setCertifiedPublicKeyByteArray(byte[] bs) {
			instance.certifiedPublicKeyByteArray = bs;
		}
		
	}
	
	
}
