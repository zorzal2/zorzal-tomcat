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

import com.fontar.bus.api.configuracion.PermisoService;
import com.fontar.data.impl.dao.ldap.PermisoDao;
import com.fontar.data.impl.domain.ldap.Permiso;
import com.fontar.data.impl.domain.ldap.SearchCriteria;

/**
 * Service implementation for managing the Group entity.
 * 
 * @author Ulrik Sandberg
 */
public class PermisoServiceImpl implements PermisoService {

    private PermisoDao permisoDao;

    public void setPermisoDao(PermisoDao permisoDao) {
        this.permisoDao = permisoDao;
    }

    /*
     * @see org.springframework.ldap.samples.person.service.PermisoService#create(java.lang.String,
     *      java.util.Set)
     */
    public void create(String idPermiso, String modulo) {

        Permiso permiso = new Permiso();
        permiso.setIdPermiso(idPermiso);
        permiso.setModulo(modulo);

        permisoDao.create(permiso);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.PermisoService#update(org.springframework.ldap.samples.person.domain.Permiso)
     */
    public void update(Permiso permiso) {
        permisoDao.update(permiso);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.PermisoService#delete(org.springframework.ldap.samples.person.domain.Permiso)
     */
    public void delete(Permiso permiso) {
        permisoDao.delete(permiso);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.PermisoService#findByPrimaryKey(java.lang.String)
     */
    public Permiso findByPrimaryKey(String idPermiso) {
        return permisoDao.findByPrimaryKey(idPermiso);
    }

    /*
     * @see org.springframework.ldap.samples.person.service.PermisoService#findAll()
     */
    public List findAll() {
        return permisoDao.findAll();
    }

    /*
     * @see org.springframework.ldap.samples.person.service.PermisoService#find(org.springframework.ldap.samples.person.domain.SearchCriteria)
     */
    public List find(SearchCriteria criteria) {
        return permisoDao.find(criteria);
    }

}
