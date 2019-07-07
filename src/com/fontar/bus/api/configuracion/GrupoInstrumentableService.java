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
package com.fontar.bus.api.configuracion;

import java.util.List;
import java.util.Set;

import com.fontar.data.impl.domain.ldap.GrupoInstrumentable;
import com.fontar.data.impl.domain.ldap.SearchCriteria;

/**
 * Servico para administrar las entidades de grupos de usuarios en realaci�n a la seguridad del sistema.
 * 
 */
public interface GrupoInstrumentableService {

   public void create(String name, String idGrupo, Set usuarios,  Set permisos);

    public void update(GrupoInstrumentable group);

    public void delete(GrupoInstrumentable group);

    public GrupoInstrumentable findByPrimaryKey(String name);

    public List find(SearchCriteria criteria);

    public List findAll();
}
