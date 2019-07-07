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

import com.fontar.data.impl.domain.ldap.GrupoConInstrumento;
import com.fontar.data.impl.domain.ldap.SearchCriteria;

/**
 * Data Access Object interface for the Group entity.
 * 
 * @author Ulrik Sandberg
 */
public interface GrupoConInstrumentoDao {
	
    public void create(GrupoConInstrumento group);

    public void update(GrupoConInstrumento group);

    public void delete(GrupoConInstrumento group);

    public GrupoConInstrumento findByPrimaryKey(String name);

    public List findAll();

    public List find(SearchCriteria criteria);
    
    public List instrumentosGranted(String userDn, String permissionName);
    
    public Set<String> usersGranted(String permissionName , String instrumento);
    
    public boolean instrumentoGranted(String userDn, String permissionName , Long instrumento);
    
    public boolean permissionGranted(String userDn, String permissionName);

	public void delete(String grupo);

	public Collection<GrupoConInstrumento> getGroupMembership(String dnAsString);
    
}
