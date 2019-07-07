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


public class Permiso implements Serializable , Comparable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 8454418324353951084L;

	private String idPermiso;
	private String modulo;


    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ((Permiso) obj).getIdPermiso().equals(this.getIdPermiso());
    }

    public int hashCode() {
        return this.getIdPermiso().hashCode();
    }

	public String getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(String idPermiso) {
		this.idPermiso = idPermiso;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	
	public String getDnAsString() {
		//FIXME: JC - Traer la base DN del LDAP.properties
		return "idPermiso="+this.idPermiso+", ou=Permisos, o=fontar, dc=org";
	}

	public int compareTo(Object object) {
		if(object instanceof Permiso){
			Permiso permiso = (Permiso) object;
			return this.getIdPermiso().compareTo( permiso.getIdPermiso());
		}else
			throw new RuntimeException("Unsupported comparation");
	}

	@Override
	public String toString() {
		return this.getIdPermiso();
	}	
	


}
