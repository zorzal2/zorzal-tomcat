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

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.ldap.AttributesMapper;
import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.LdapOperations;
import org.springframework.ldap.support.DirContextAdapter;
import org.springframework.ldap.support.DirContextOperations;
import org.springframework.ldap.support.DistinguishedName;
import org.springframework.ldap.support.filter.AndFilter;
import org.springframework.ldap.support.filter.EqualsFilter;
import org.springframework.ldap.support.filter.WhitespaceWildcardsFilter;

import com.fontar.data.impl.domain.ldap.GrupoConInstrumento;
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
public class GrupoConInstrumentoDaoImpl implements GrupoConInstrumentoDao {

    /**
     * The template object that performs all data access work.
     */
    LdapOperations ldapOperations;
    
    private UsuarioDao usuarioDao;

    
    public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	DistinguishedName buildDn(GrupoConInstrumento group) {
        DistinguishedName dn = new DistinguishedName();
        dn.add("ou", "Grupos");
        dn.add("idGrupo", group.getIdGrupo());
        return dn;
    }

    DirContextOperations setAttributes(DirContextOperations adapter, GrupoConInstrumento group) {
    	
    	//Clase
        adapter.setAttributeValues("objectclass", new String[] { "top","grupoInstrumentoFontar" });
        
        //Usuarios
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
        
        if ( !group.getPermisos().isEmpty() ){
        	Set <String> permisosString = new TreeSet<String>();
        	for(Permiso permiso : group.getPermisos()) {
				permisosString.add(permiso.getDnAsString());
			}
            adapter.setAttributeValues("permiso", permisosString.toArray(new String[0]));
        }else
        	adapter.setAttributeValues("permiso", new String[0]);
        
        //Datos generales
        adapter.setAttributeValue("nombre", group.getNombre());
        adapter.setAttributeValue("idGrupo", group.getIdGrupo());
        adapter.setAttributeValue("idInstrumento", String.valueOf(group.getIdInstrumento()));
        return adapter;
    }
    
    
    ModificationItem[] updateAttributes(DirContextOperations adapter, GrupoConInstrumento group) {
    	
    	  adapter.setAttributeValue("nombre", group.getNombre());
    	
    	//Reescribe la lista de usuarios
        if( !group.getUsuarios().isEmpty() ){
        	Set <String> usuariosString = new TreeSet<String>();
        	for (Iterator iter = group.getUsuarios().iterator(); iter.hasNext();) {
				Usuario usuario = (Usuario) iter.next();
				usuariosString.add(usuario.getDnAsString());
			}
            adapter.setAttributeValues("usuario", usuariosString.toArray(new String[0]));
        }else 
        	adapter.setAttributeValues("usuario", new String[0]);
        
        //Sobreescribe la lista de permisos
        if ( !group.getPermisos().isEmpty() ){
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
        return new GrupoConInstrumentoContextMapper(this.ldapOperations);
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#create(org.springframework.ldap.samples.person.domain.Grupo)
     */
    public void create(GrupoConInstrumento group) {
   		ldapOperations.bind(buildDn(group), setAttributes(
               new DirContextAdapter(), group), null);
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#delete(org.springframework.ldap.samples.person.domain.Grupo)
     */
    public void delete(GrupoConInstrumento group) {
        ldapOperations.unbind(buildDn(group));
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#find(org.springframework.ldap.samples.person.domain.SearchCriteria)
     */
    public List find(SearchCriteria criteria) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "grupoInstrumentoFontar"));
        filter.and(new WhitespaceWildcardsFilter("idGrupo", criteria.getName()));
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter
                .encode(), getContextMapper());
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#findAll()
     */
    public List findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass",
                "grupoInstrumentoFontar");
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter
                .encode(), getContextMapper());
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#findByPrimaryKey(java.lang.String)
     */
    public GrupoConInstrumento findByPrimaryKey(String name) {
        DistinguishedName dn = new DistinguishedName("ou=Grupos");
        dn.add("idGrupo", name);
        return (GrupoConInstrumento) ldapOperations.lookup(dn, getContextMapper());
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.GrupoDao#update(org.springframework.ldap.samples.person.domain.Grupo)
     */
    public void update(GrupoConInstrumento group) {
        DistinguishedName dn = buildDn(group);
        DirContextOperations adapter = (DirContextOperations) ldapOperations.lookup(dn);
        ModificationItem[] modifications = updateAttributes(adapter, group);
        ArrayUtils.reverse( modifications ); //Esto resuelve un bug de spring con el orden de modificaciones
        ldapOperations.modifyAttributes(dn, modifications);
    }

    public void setLdapOperations(LdapOperations ldapOperations) {
        this.ldapOperations = ldapOperations;
    }

	public List instrumentosGranted(String userDn, String permissionName) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "grupoInstrumentoFontar"));
		filter.and(new EqualsFilter("usuario", userDn));
		filter.and(new EqualsFilter("permiso", "idPermiso="+permissionName+", ou=Permisos, o=fontar, dc=org"));
		return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), new InstrumentoProjection() );
	}

	
	private class InstrumentoProjection implements AttributesMapper {

		public Object mapFromAttributes(Attributes attributes) throws NamingException {
			return String.valueOf(attributes.get("idInstrumento").get());
		}
	}

	
	
	
	public void delete(String grupo) {
		this.delete( this.findByPrimaryKey( grupo ));
	}

	public Collection<GrupoConInstrumento> getGroupMembership(String userDn) {
		AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "grupoInstrumentoFontar"));
        filter.and(new EqualsFilter("usuario", userDn));
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), this.getContextMapper() );
	}

	public boolean instrumentoGranted(String userDn, String permissionName, Long instrumento) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "grupoInstrumentoFontar"));
		filter.and(new EqualsFilter("usuario", userDn));
		filter.and(new EqualsFilter("permiso", "idPermiso="+permissionName+", ou=Permisos, o=fontar, dc=org"));
		if(instrumento!=null)
			filter.and(new EqualsFilter("idInstrumento", instrumento.toString()));
		return !ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), new InstrumentoProjection() ).isEmpty();
	}

	public boolean permissionGranted(String userDn, String permissionName) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "grupoInstrumentoFontar"));
		filter.and(new EqualsFilter("usuario", userDn));
		filter.and(new EqualsFilter("permiso", "idPermiso="+permissionName+", ou=Permisos, o=fontar, dc=org"));
		return !ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), new InstrumentoProjection() ).isEmpty();
	}

	@SuppressWarnings("unchecked")
	public Set<String> usersGranted(String permissionName,String instrumento) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "grupoInstrumentoFontar"));
		filter.and(new EqualsFilter("idInstrumento", instrumento));
		filter.and(new EqualsFilter("permiso", "idPermiso="+permissionName+", ou=Permisos, o=fontar, dc=org"));
		List<Collection> users = ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), new UserProjection(this.usuarioDao) );
		Set<String> usersGranted = new HashSet<String>();
		for (Collection granted: users)
			usersGranted.addAll(granted);
		return usersGranted;
	}
}
