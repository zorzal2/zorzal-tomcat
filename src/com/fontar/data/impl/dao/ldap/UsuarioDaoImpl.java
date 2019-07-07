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
package com.fontar.data.impl.dao.ldap;

import java.util.List;

import org.acegisecurity.userdetails.ldap.LdapUserDetails;
import org.acegisecurity.userdetails.ldap.LdapUserDetailsImpl;
import org.springframework.ldap.LdapOperations;
import org.springframework.ldap.support.DirContextAdapter;
import org.springframework.ldap.support.DirContextOperations;
import org.springframework.ldap.support.DistinguishedName;
import org.springframework.ldap.support.filter.AndFilter;
import org.springframework.ldap.support.filter.EqualsFilter;
import org.springframework.ldap.support.filter.WhitespaceWildcardsFilter;

import com.fontar.bus.api.configuracion.GrupoService;
import com.fontar.data.impl.domain.ldap.SearchCriteria;
import com.fontar.data.impl.domain.ldap.UserStatus;
import com.fontar.data.impl.domain.ldap.Usuario;


public class UsuarioDaoImpl implements UsuarioDao {

    /**
     * The template object that performs all data access work.
     */
    LdapOperations ldapOperations;
    
    GrupoService grupoService;

    public UsuarioDaoImpl(LdapOperations ldapOperations2) {
    	this.ldapOperations= ldapOperations2;
	}
    
    public UsuarioDaoImpl() {
	}
    
    

	public GrupoService getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	DistinguishedName buildDn(Usuario usuario) {
        DistinguishedName dn = new DistinguishedName();
        dn.add("ou", "Usuarios");
        dn.add("uid", usuario.getUserId());
        return dn;
    }

    DirContextOperations setAttributes(DirContextOperations adapter, Usuario usuario) {
        adapter.setAttributeValues("objectclass", new String[] { "top","usuarioFontar" });
        adapter.setAttributeValue("uid", usuario.getUserId());
        adapter.setAttributeValue("cn", usuario.getNombre());
        adapter.setAttributeValue("sn", usuario.getApellido());
        adapter.setAttributeValue("userPassword", usuario.getPassword());
        if(usuario.getStatus()!=null)
        	adapter.setAttributeValue("userStatus", usuario.getStatus().getName());
        else
        	adapter.setAttributeValue("userStatus", UserStatus.INISTIALIZED.getName());
        if(usuario.getPublicKey()!=null)
        	adapter.setAttributeValue("publicKey", usuario.getPublicKey().getEncoded() );
        
        
        return adapter;
    }

   

    /*
     * @see org.springframework.ldap.samples.person.dao.UsuarioDao#create(org.springframework.ldap.samples.person.domain.Usuario)
     */
    @SuppressWarnings("unchecked")
	public void create(Usuario usuario) {
    	this.bindGroups( usuario );
        ldapOperations.bind(buildDn(usuario), setAttributes(new DirContextAdapter(), usuario), null);
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.UsuarioDao#delete(org.springframework.ldap.samples.person.domain.Usuario)
     */
    @SuppressWarnings("unchecked")
	public void delete(Usuario usuario) {
    	this.unbindGroups( usuario );
        ldapOperations.unbind(buildDn(usuario));
    }

    
	private void unbindGroups(Usuario usuario){
    	this.grupoService.removeMemberShip( usuario );
    }
    
    
	@SuppressWarnings("unchecked")
	private void bindGroups(Usuario usuario){
    	this.grupoService.addMemberShip(usuario, usuario.getGrupos());
    }
    
    /*
     * @see org.springframework.ldap.samples.person.dao.UsuarioDao#find(org.springframework.ldap.samples.person.domain.SearchCriteria)
     */
    public List find(SearchCriteria criteria) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "usuarioFontar"));
        filter.and(new WhitespaceWildcardsFilter("uid", criteria.getName()));
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter
                .encode(), new UsuarioAttributesMapper());
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.UsuarioDao#findAll()
     */
    public List findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass",
                "usuarioFontar");
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter
                .encode(),  new UsuarioAttributesMapper());
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.UsuarioDao#findByPrimaryKey(java.lang.String)
     */
    public Usuario findByPrimaryKey(String name) {
        DistinguishedName dn = new DistinguishedName("ou=Usuarios");
        dn.add("uid", name);
        return (Usuario) ldapOperations.lookup(dn, new UsuarioAttributesMapper( ) );
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.UsuarioDao#update(org.springframework.ldap.samples.person.domain.Usuario)
     */
    public void update(Usuario usuario, boolean recursive) {
        DistinguishedName dn = buildDn(usuario);
        if(recursive){
        	usuario.getGrupos();
        	this.unbindGroups( usuario );
        	this.bindGroups( usuario );
        }
        DirContextOperations adapter = (DirContextOperations) ldapOperations.lookup(dn);
        adapter = setAttributes(adapter, usuario);
        ldapOperations.modifyAttributes(dn, adapter.getModificationItems());
    }

    public void update(Usuario usuario) {
    	this.update(usuario, true);
    }
    
    public void setLdapOperations(LdapOperations ldapOperations) {
        this.ldapOperations = ldapOperations;
    }
    
    
    /*
     * @see org.springframework.ldap.samples.person.dao.UsuarioDao#findByPrimaryKey(java.lang.String)
     */
    public Usuario findByDn(String userDn) {
        DistinguishedName dn = new DistinguishedName(userDn);
        String userID = dn.getLdapRdn(3).getComponent().getValue();
        return (Usuario) this.findByPrimaryKey(userID);
    }
    
	public LdapUserDetails searchForUser(String username) {
		return this.findByPrimaryKey( username);
	}
	
	public void updateAuthenticationPassword(String userId, String password) {
		Usuario usuario = this.findByPrimaryKey(userId);
		LdapUserDetailsImpl.Essence essence = new LdapUserDetailsImpl.Essence(usuario.getLdapUserDetails());
		essence.setPassword( password);
		usuario.setLdapUserDetails( essence.createUserDetails());
		this.update(usuario,false);
	}    
}