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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.Predicate;

import com.fontar.data.impl.domain.ldap.Grupo;
import com.fontar.data.impl.domain.ldap.GrupoAbstracto;
import com.fontar.data.impl.domain.ldap.GrupoConInstrumento;
import com.fontar.data.impl.domain.ldap.Permiso;
import com.fontar.data.impl.domain.ldap.SearchCriteria;
import com.fontar.data.impl.domain.ldap.Usuario;

/**
 * Servico para la administración de grupos de usuarios en realación a la seguridad del sistema.
 * 
 */
public interface GrupoService {

	public void create(String name, String idGrupo, Set<Usuario> usuarios,  Set<Permiso> permisos);

    public void update(Grupo group);

    public void delete(Grupo group);

    public Grupo findByPrimaryKey(String name);
    
    public GrupoAbstracto getGrupo(String name);
    
    //FIXME no deberia hacer falta la distincion
    public GrupoConInstrumento findInstrumentableByPrimaryKey(String name);

    public List find(SearchCriteria criteria);

    public Collection<Grupo> findAll();

	public void delete(String grupo);

	public void create(String name);
	
	public List<Permiso> getPermissionsAsignable(Grupo group);
	
	public List<Permiso> getPermissionsAsignable(String grupo);

	public void create(String string, String[] permisos);

	public void create(String nombre, Long instrumento, String[] permisosSeleccionados);

	public void deleteInstrumentable(String grupo);

	public void update(GrupoAbstracto grupo);

	public Collection getPermissionsAsignable(GrupoAbstracto grupo);

	public void update(String idGrupo, String[] permisosSeleccionados);
	
	public void removeMemberShip(Usuario usuario);
	
	public void addMemberShip(Usuario usuario, Collection<GrupoAbstracto> grupos);

	public void update(String idGrupo, String nombre, String[] permisosSeleccionados);
	
	public GrupoAbstracto findByName(String name);

	public Collection<Grupo> filter(Predicate predicate);
}


