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
package com.fontar.data.impl.domain.ldap;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/**
 * Simple class representing an access group.
 * 
 * @author Ulrik Sandberg
 */
public abstract class GrupoAbstracto implements Serializable {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 8381938714340829593L;
	
	private String nombre;
	private String idGrupo;

    private Set<Usuario> usuarios;
    private Set<Permiso> permisos;

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


	public Set<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(Set<Permiso> permisos) {
		this.permisos = permisos;
	}
    
	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
	
	public String getDnAsString() {
		return "idGrupo="+this.idGrupo+", ou=Grupos, o=fontar, dc=org";
	}

	public void remove(Usuario usuario) {
		Iterator iterator = this.getUsuarios().iterator();
		while (iterator.hasNext()) {
			Usuario current = (Usuario) iterator.next();
			if(current.getDnAsString().equals(usuario.getDnAsString()))
				iterator.remove();
		}
	}
	
	public boolean equals(Object object){
		if(object instanceof GrupoAbstracto){
			return this.idGrupo.equals( ((GrupoAbstracto)object).getIdGrupo());
		}else
			return false;
	}

	@Override
	public int hashCode() {
		return this.idGrupo.hashCode();
	}

	
	
}
