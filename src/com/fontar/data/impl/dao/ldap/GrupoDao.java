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
import java.util.List;
import java.util.Set;

import com.fontar.data.impl.domain.ldap.Grupo;
import com.fontar.data.impl.domain.ldap.SearchCriteria;

/**
 * Data Access Object interface for the Group entity.
 * 
 * @author Ulrik Sandberg
 */
public interface GrupoDao {
    public void create(Grupo group);

    public void update(Grupo group);

    public void delete(Grupo group);

    public Grupo findByPrimaryKey(String name);

    public List findAll();

    public List find(SearchCriteria criteria);
		
	/**
	 * Retorna los grupos a los que pertenece un usuario
	 **/
	public Collection<Grupo> getGroupMembership(String userDn);
	
	/** 
	 * Retorna los grupos de un usuario que contengan el permiso @permission
	 **/
	public List<Grupo> filterBy(String userDn, String permission);
	
	public Set<String> usersGranted(String permissionName , String instrumento);
}
