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

import com.fontar.data.impl.domain.ldap.Permiso;
import com.fontar.data.impl.domain.ldap.SearchCriteria;

/**
 * Data Access Object interface for the Group entity.
 * 
 * @author Ulrik Sandberg
 */
public interface PermisoDao {
    public void create(Permiso permiso);

    public void update(Permiso permiso);

    public void delete(Permiso permiso);

    public Permiso findByPrimaryKey(String idPermiso);

    public List<Permiso> findAll();

    public List<Permiso> find(SearchCriteria criteria);

	public Permiso findByDn(String permisoDN);
}
