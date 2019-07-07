package com.fontar.data.impl.dao.ldap;

import java.util.HashSet;
import java.util.Set;

import org.springframework.ldap.ContextMapper;
import org.springframework.ldap.EntryNotFoundException;
import org.springframework.ldap.LdapOperations;
import org.springframework.ldap.support.DirContextOperations;

import com.fontar.data.impl.domain.ldap.Grupo;
import com.fontar.data.impl.domain.ldap.Permiso;
import com.fontar.data.impl.domain.ldap.Usuario;

/**
 * Maps from DirContextOperations (DirContextAdapters, really) to Group objects.
 * A DN for a group will be of the form <code>cn=[name],ou=groups</code>
 * 
 * @author Ulrik Sandberg
 */
public class GrupoContextMapper implements ContextMapper {

    /**
     * The template object that performs all data access work.
     */
    LdapOperations ldapOperations;
    
    public GrupoContextMapper(LdapOperations ldapOperations) {
		this.ldapOperations= ldapOperations;
	}
    
    public GrupoContextMapper() {
	}    

	/*
     * @see org.springframework.ldap.ContextMapper#mapFromContext(java.lang.Object)
     */
    public Object mapFromContext(Object ctx) {
        DirContextOperations dirContext = (DirContextOperations) ctx;
        Grupo group = new Grupo();
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
        Set<Permiso> permisos = new HashSet<Permiso>();
        if (permisosArray != null) {
        	PermisoDaoImpl permisoDaoImpl = new PermisoDaoImpl(ldapOperations);
        	PermisoDao permisoDao = (PermisoDao)permisoDaoImpl ;
        	for (int i = 0; i < permisosArray.length; i++) {
				String permisoDN = permisosArray[i];
				try{
					Permiso permiso = permisoDao.findByDn(permisoDN);
					permisos.add(permiso);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
        }
        group.setPermisos( permisos );
        
        group.setIdGrupo(dirContext.getStringAttribute("idGrupo"));
        //group.setIdInstrumento(dirContext.getStringAttribute("idInstrumento"));        
        return group;
    }
}
