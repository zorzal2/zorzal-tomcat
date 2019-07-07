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

import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.support.DirContextOperations;

import com.fontar.data.impl.domain.ldap.UserStatus;
import com.fontar.data.impl.domain.ldap.Usuario;

/**
 * Maps from DirContextOperations (DirContextAdapters, really) to Group objects.
 * A DN for a group will be of the form <code>cn=[name],ou=groups</code>
 * 
 * @author Ulrik Sandberg
 */
public class UsuarioContextMapper implements ContextMapper {

	
	
	
    /*
     * @see org.springframework.ldap.ContextMapper#mapFromContext(java.lang.Object)
     */
    public Object mapFromContext(Object ctx) {
        DirContextOperations dirContext = (DirContextOperations) ctx;
        Usuario usuario = new Usuario();
        usuario.setNombre(dirContext.getStringAttribute("cn"));
        usuario.setApellido(dirContext.getStringAttribute("sn"));
        usuario.setUserId(dirContext.getStringAttribute("uid"));
        
        usuario.setPassword(LdapContexMapperUtils.resolveOctetString(dirContext,"userPassword" ));

        String status = dirContext.getStringAttribute("userStatus");
        if(status!=null)
        	usuario.setStatus( UserStatus.valueOf( status ));
        
        String retrievedPublicKey = dirContext.getStringAttribute("publicKey");
        if(retrievedPublicKey!=null)
        	usuario.setPublicKeyByteArray( retrievedPublicKey.getBytes() );
        
   
        String retrievedCertifiedPublicKey = dirContext.getStringAttribute("certifiedPublicKey");
        if(retrievedCertifiedPublicKey!=null)
        	usuario.setPublicKeyByteArray( retrievedCertifiedPublicKey.getBytes() );
        
        return usuario;
    }
}
