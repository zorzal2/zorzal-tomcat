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

import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.LdapOperations;
import org.springframework.ldap.support.DirContextAdapter;
import org.springframework.ldap.support.DirContextOperations;
import org.springframework.ldap.support.DistinguishedName;
import org.springframework.ldap.support.filter.AndFilter;
import org.springframework.ldap.support.filter.EqualsFilter;
import org.springframework.ldap.support.filter.WhitespaceWildcardsFilter;

import com.fontar.data.impl.domain.ldap.Permiso;
import com.fontar.data.impl.domain.ldap.SearchCriteria;

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
public class PermisoDaoImpl implements PermisoDao {

    /**
     * The template object that performs all data access work.
     */
    LdapOperations ldapOperations;

    public PermisoDaoImpl(LdapOperations ldapOperations2) {
		this.ldapOperations= ldapOperations2;
	}
    
    public PermisoDaoImpl() {
		
	}    

	DistinguishedName buildDn(Permiso permiso) {
        DistinguishedName dn = new DistinguishedName();
        dn.add("ou", "Permisos");
        dn.add("idPermiso", permiso.getIdPermiso());
        return dn;
    }

    DirContextOperations setAttributes(DirContextOperations adapter, Permiso permiso) {
        adapter.setAttributeValues("objectclass", new String[] { "top",
            //    "permisoOfUniqueNames", "grupoFontar" });
        	"permisoFontar" });
        adapter.setAttributeValue("idPermiso", permiso.getIdPermiso());
        adapter.setAttributeValue("modulo", permiso.getModulo());
        return adapter;
    }

    ContextMapper getContextMapper() {
        return new PermisoContextMapper();
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.PermisoDao#create(org.springframework.ldap.samples.person.domain.Permiso)
     */
    public void create(Permiso permiso) {
        ldapOperations.bind(buildDn(permiso), setAttributes(
                new DirContextAdapter(), permiso), null);
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.PermisoDao#delete(org.springframework.ldap.samples.person.domain.Permiso)
     */
    public void delete(Permiso permiso) {
        ldapOperations.unbind(buildDn(permiso));
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.PermisoDao#find(org.springframework.ldap.samples.person.domain.SearchCriteria)
     */
    public List find(SearchCriteria criteria) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "permisoFontar"));
        filter.and(new WhitespaceWildcardsFilter("idPermiso", criteria.getName()));
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.PermisoDao#findAll()
     */
    public List findAll() {
        EqualsFilter filter = new EqualsFilter("objectclass","permisoFontar");
        return ldapOperations.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.PermisoDao#findByPrimaryKey(java.lang.String)
     */
    public Permiso findByPrimaryKey(String name) {
        DistinguishedName dn = new DistinguishedName("ou=Permisos");
        dn.add("idPermiso", name);
        return (Permiso) ldapOperations.lookup(dn, getContextMapper());
    }

    /*
     * @see org.springframework.ldap.samples.person.dao.PermisoDao#update(org.springframework.ldap.samples.person.domain.Permiso)
     */
    public void update(Permiso permiso) {
        DistinguishedName dn = buildDn(permiso);
        DirContextOperations adapter = (DirContextOperations) ldapOperations
                .lookup(dn);
        adapter = setAttributes(adapter, permiso);
        ldapOperations.modifyAttributes(dn, adapter.getModificationItems());
    }

    public void setLdapOperations(LdapOperations ldapOperations) {
        this.ldapOperations = ldapOperations;
    }

	public Permiso findByDn(String permisoDN) {
        DistinguishedName dn = new DistinguishedName(permisoDN);
        String permisoID = dn.getLdapRdn(3).getComponent().getValue();
        return (Permiso) this.findByPrimaryKey(permisoID);
	}
	
}
