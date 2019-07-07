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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.EntryNotFoundException;
import org.springframework.ldap.LdapOperations;
import org.springframework.ldap.support.DirContextOperations;

import com.fontar.data.impl.domain.ldap.GrupoInstrumentable;
import com.fontar.data.impl.domain.ldap.Permiso;
import com.fontar.data.impl.domain.ldap.Usuario;

/**
 * Maps from DirContextOperations (DirContextAdapters, really) to Group objects.
 * A DN for a group will be of the form <code>cn=[name],ou=groups</code>
 * 
 * @author Ulrik Sandberg
 */
public class GrupoInstrumentableContextMapper implements ContextMapper {

    LdapOperations ldapOperations;
    
    public GrupoInstrumentableContextMapper(LdapOperations ldapOperations) {
		this.ldapOperations= ldapOperations;
	}
    
    public GrupoInstrumentableContextMapper() {
	}    
	
	
    /*
     * @see org.springframework.ldap.ContextMapper#mapFromContext(java.lang.Object)
     */
    public Object mapFromContext(Object ctx) {
        DirContextOperations dirContext = (DirContextOperations) ctx;
        GrupoInstrumentable group = new GrupoInstrumentable();
        group.setNombre(dirContext.getStringAttribute("nombre"));
        String[] usuariosArray = dirContext.getStringAttributes("usuario");
        if (usuariosArray != null) {
        	Set<Usuario> usuarios = new HashSet<Usuario>();
        	UsuarioDaoImpl usuarioDaoImpl = new UsuarioDaoImpl(ldapOperations);
        	UsuarioDao usuarioDao = (UsuarioDao)usuarioDaoImpl ;
        	for (int i = 0; i < usuariosArray.length; i++) {
				try{
					String userDN= usuariosArray[i];
					usuarios.add(usuarioDao.findByDn(userDN));
				}catch (EntryNotFoundException e) {

				}
			}
        	group.setUsuarios(usuarios);
        }else{
        	group.setUsuarios( new HashSet<Usuario>());
        }
        
        String[] permisosArray = dirContext.getStringAttributes("permiso");
        if (permisosArray != null) {
        	Permiso[] permisos = new Permiso[permisosArray.length];
        	PermisoDaoImpl permisoDaoImpl = new PermisoDaoImpl(ldapOperations);
        	PermisoDao permisoDao = (PermisoDao)permisoDaoImpl ;
        	for (int i = 0; i < permisosArray.length; i++) {
				String permisoDN = permisosArray[i];
				permisos[i]= permisoDao.findByDn(permisoDN);
			}
        	List <Permiso> list = Arrays.asList(permisos);
            group.setPermisos(new HashSet<Permiso> (list));
        }
        
        group.setIdGrupo(dirContext.getStringAttribute("idGrupo"));
        //group.setIdInstrumento(dirContext.getStringAttribute("idInstrumento"));        
        return group;
    }
}
