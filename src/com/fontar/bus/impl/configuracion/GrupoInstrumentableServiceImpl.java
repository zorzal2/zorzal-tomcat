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
package com.fontar.bus.impl.configuracion;

import java.util.List;
import java.util.Set;

import com.fontar.bus.api.configuracion.GrupoInstrumentableService;
import com.fontar.data.impl.dao.ldap.GrupoInstrumentableDao;
import com.fontar.data.impl.domain.ldap.GrupoInstrumentable;
import com.fontar.data.impl.domain.ldap.SearchCriteria;

/**
 * Service implementation for managing the Group entity.
 */
public class GrupoInstrumentableServiceImpl implements GrupoInstrumentableService {

    private GrupoInstrumentableDao grupoDao;

    public void setGrupoInstrumentableDao(GrupoInstrumentableDao groupDao) {
        this.grupoDao = groupDao;
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoInstrumentableService#create(java.lang.String,
     *      java.util.Set)
     */
    public void create(String name, String idGrupo, Set usuarios, Set permisos) {

        GrupoInstrumentable group = new GrupoInstrumentable();
        group.setNombre(name);
        group.setIdGrupo(idGrupo);
        //group.setIdInstrumento(idInstrumento); 
        group.setUsuarios(usuarios);
        group.setPermisos(permisos);        

        grupoDao.create(group);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoInstrumentableService#update(org.springframework.ldap.samples.person.domain.GrupoInstrumentable)
     */
    public void update(GrupoInstrumentable group) {
        grupoDao.update(group);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoInstrumentableService#delete(org.springframework.ldap.samples.person.domain.GrupoInstrumentable)
     */
    public void delete(GrupoInstrumentable group) {
        grupoDao.delete(group);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoInstrumentableService#findByPrimaryKey(java.lang.String)
     */
    public GrupoInstrumentable findByPrimaryKey(String name) {
        return grupoDao.findByPrimaryKey(name);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoInstrumentableService#findAll()
     */
    public List findAll() {
        return grupoDao.findAll();
    }

    /*
     * @see org.springframework.ldap.samples.person.service.GrupoInstrumentableService#find(org.springframework.ldap.samples.person.domain.SearchCriteria)
     */
    public List find(SearchCriteria criteria) {
        return grupoDao.find(criteria);
    }

}
