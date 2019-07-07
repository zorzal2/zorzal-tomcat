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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.naming.directory.ModificationItem;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.LdapOperations;
import org.springframework.ldap.support.DirContextAdapter;
import org.springframework.ldap.support.DirContextOperations;
import org.springframework.ldap.support.DistinguishedName;
import org.springframework.ldap.support.filter.AndFilter;
import org.springframework.ldap.support.filter.EqualsFilter;
import org.springframework.ldap.support.filter.WhitespaceWildcardsFilter;

import com.fontar.data.impl.domain.ldap.Grupo;
import com.fontar.data.impl.domain.ldap.Permiso;
import com.fontar.data.impl.domain.ldap.SearchCriteria;
import com.fontar.data.impl.domain.ldap.Usuario;

/**
 * Default implementation of GroupDao. This implementation uses
 * DirContextOperations (DirContextAdapter really, but for mock testing purposes
 * we use the interface) for managing attribute values. It has been specified in
 * the Spring Context that the DirObjectFactory should be used when creating
 * objects from contexts, which defaults to creating DirContextAdapter objects.
 * This means that we can use a ContextMapper to map from the found contexts to
 * our domain objects.
 * 
 * @author Ulrik Sandberg
 */
public class GrupoDaoImpl implements GrupoDao {

	static final String OBJECT_CLASS = "grupoFontar"; 

	 private UsuarioDao usuarioDao;

	
    /**
     * The template object that performs all data access work.
     */
    LdapOperations ldapOperations;

    DistinguishedName buildDn(Grupo group) {
        DistinguishedName dn = new DistinguishedName();
        dn.add("ou", "Grupos");
        dn.add("idGrupo", group.getIdGrupo());
        return dn;
    }

    
    public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}


	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}



	DirContextOperations setAttributes(DirContextOperations adapter, Grupo group) {
        adapter.setAttributeValues("objectclass", new String[] { "top","grupoFontar" });
        
        if (group.getUsuarios() != null && group.getUsuarios().size() > 0) {
        	Set <String> usuariosString = new TreeSet<String>();
        	for (Iterator iter = group.getUsuarios().iterator(); iter.hasNext();) {
				Usuario usuario = (Usuario) iter.next();
				usuariosString.add(usuario.getDnAsString());
			}
            adapter.setAttributeValues("usuario", usuariosString.toArray(new String[0]));
        } else {
        	adapter.setAttributeValues("usuario", new String[0]);
        }
        
        if (group.getPermisos() != null && group.getPermisos().size() > 0) {
        	Set <String> permisosString = new TreeSet<String>();
        	for (Iterator iter = group.getPermisos().iterator(); iter.hasNext();) {
				Permiso permiso = (Permiso) iter.next();
				permisosString.add(permiso.getDnAsString());
			}
            adapter.setAttributeValues("permiso", permisosString.toArray(new String[0]));
        }else {
        	adapter.setAttributeValues("permiso", new String[0]);
        }
        adapter.setAttributeValue("nombre", group.getNombre());
        adapter.setAttributeValue("idGrupo", group.getIdGrupo());
     
        return adapter;
    }
    
    ModificationItem[] updateAttributes(DirContextOperations adapter, Grupo group) {
    	
        adapter.setAttributeValue("nombre", group.getNombre());
    	
        if( !group.getUsuarios().isEmpty()) {
        	Set <String> usuariosString = new TreeSet<String>();
        	for (Iterator iter = group.getUsuarios().iterator(); iter.hasNext();) {
				Usuario usuario = (Usuario) iter.next();
				usuariosString.add(usuario.getDnAsString());
			}
            adapter.setAttributeValues("usuario", usuariosString.toArray(new String[0]));
        }else
        	adapter.setAttributeValues("usuario", new String[0]);
        
        if ( !group.getPermisos().isEmpty() ) {
        	Set <String> permisosString = new TreeSet<String>();
        	for (Iterator iter = group.getPermisos().iterator(); iter.hasNext();) {
				Permiso permiso = (Permiso) iter.next();
				permisosString.add(permiso.getDnAsString());
			}
            adapter.setAttributeValues("permiso", permisosString.toArray(new String[0]));
        }else
        	adapter.setAttributeValues("permiso", new String[0]);
        
        return adapter.getModificationItems();
    }
    

    ContextMapper getContextMapper() {
        return new GrupoContextMapper(ldapOperations);
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#create(org.springframework.ldap.samples.person.domain.Grupo)
     */
    public void create(Grupo group) {
        ldapOperations.bind(buildDn(group), setAttributes(
                new DirContextAdapter(), group), null);
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#delete(org.springframework.ldap.samples.person.domain.Grupo)
     */
    public void delete(Grupo group) {
        ldapOperations.unbind(buildDn(group));
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#find(org.springframework.ldap.samples.person.domain.SearchCriteria)
     */
    public List find(SearchCriteria criteria) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "grupoFontar"));
        filter.and(new WhitespaceWildcardsFilter("idGrupo", criteria.getName()));
        
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#findAll()
     */
    public List findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass",
                "grupoFontar");
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter
                .encode(), getContextMapper());
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#findByPrimaryKey(java.lang.String)
     */
    public Grupo findByPrimaryKey(String name) {
        DistinguishedName dn = new DistinguishedName("ou=Grupos");
        dn.add("idGrupo", name);
        return (Grupo) ldapOperations.lookup(dn, getContextMapper());
    }

    @SuppressWarnings("unchecked")
	public List<Grupo> filterBy(String userDn, String permission) {
    	AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", OBJECT_CLASS));
        filter.and(new EqualsFilter("usuario", userDn));
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), this.getContextMapper() );
    }
    
    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#update(org.springframework.ldap.samples.person.domain.Grupo)
     */
    public void update(Grupo group) {
        DistinguishedName dn = buildDn(group);
        DirContextOperations adapter = (DirContextOperations) ldapOperations
                .lookup(dn);
        ModificationItem[] modificationItems  = this.updateAttributes(adapter, group);
        ArrayUtils.reverse( modificationItems );
        ldapOperations.modifyAttributes(dn, modificationItems );
    }

    public void setLdapOperations(LdapOperations ldapOperations) {
        this.ldapOperations = ldapOperations;
    }
    
    
   
    @SuppressWarnings("unchecked")
	public List<Grupo> getGroupMembership(String userDn) {
    	AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", OBJECT_CLASS));
        filter.and(new EqualsFilter("usuario", userDn));
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), this.getContextMapper() );
    }



    @SuppressWarnings("unchecked")
	public Set<String> usersGranted(String permissionName,String instrumento) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", OBJECT_CLASS));
		filter.and(new EqualsFilter("permiso", "idPermiso="+permissionName+", ou=Permisos, o=fontar, dc=org"));
		List<Collection> users = ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), new UserProjection(this.usuarioDao) );
		Set<String> usersGranted = new HashSet<String>();
		for (Collection granted: users)
			usersGranted.addAll(granted);
		return usersGranted;
	}
}
