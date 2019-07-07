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

import org.acegisecurity.ldap.LdapUserSearch;

import com.fontar.data.impl.domain.ldap.SearchCriteria;
import com.fontar.data.impl.domain.ldap.Usuario;

/**
 * Data Access Object interface for the Group entity.
 * 
 * @author Ulrik Sandberg
 */
public interface UsuarioDao extends LdapUserSearch {
	
    public void create(Usuario usuario);

    public void update(Usuario usuario);

    public void delete(Usuario usuario);

    public Usuario findByPrimaryKey(String userId);

    public List findAll();

    public List find(SearchCriteria criteria);
    
    public Usuario findByDn(String userDn);

	public void updateAuthenticationPassword(String userId, String password);
}
