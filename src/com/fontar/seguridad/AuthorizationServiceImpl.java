package com.fontar.seguridad;

import java.util.Set;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;

import com.fontar.data.impl.dao.ldap.GrupoConInstrumentoDao;
import com.fontar.data.impl.dao.ldap.GrupoDao;
import com.fontar.seguridad.acegi.FontarGrantedAuthority;
import com.fontar.seguridad.acegi.FontarSecurityConfig;
import com.fontar.seguridad.acegi.GrantedPermissionByInstrumento;
import com.fontar.seguridad.acegi.GrantedSimplePermission;
import com.fontar.seguridad.acegi.InstrumentoSecurityConfig;
import com.pragma.util.exception.IllegalArgumentException;

public class AuthorizationServiceImpl implements AuthorizationService  {

	private GrupoConInstrumentoDao grupoConInstrumentoDao;
	
	private GrupoDao grupoDao;
	
	
	public GrupoDao getGrupoDao() {
		return grupoDao;
	}

	public void setGrupoDao(GrupoDao grupoDao) {
		this.grupoDao = grupoDao;
	}

	public GrupoConInstrumentoDao getGrupoConInstrumentoDao() {
		return grupoConInstrumentoDao;
	}

	public void setGrupoConInstrumentoDao(GrupoConInstrumentoDao grupoConInstrumentoDao) {
		this.grupoConInstrumentoDao = grupoConInstrumentoDao;
	}

	/* (non-Javadoc)
	 * @see com.fontar.seguridad.AuthorizationService#getPooledActors(java.lang.String, java.lang.Long)
	 */
	public String[] getPooledActors(String idActionWorkflow, Long idInstrumento) {
		String permissionsRequired = idActionWorkflow.replaceAll("_","-");
		String instrumento = String.valueOf(idInstrumento);
		Set<String> grantedUsers = this.grupoConInstrumentoDao.usersGranted(permissionsRequired,instrumento);
		grantedUsers.addAll( this.grupoDao.usersGranted( permissionsRequired , instrumento) );
		return  grantedUsers.toArray(new String[]{});
	}
	
	/**
	 * Determina si el usuario tiene el permiso con el nombre dado habiliatado
	 * para todos los instrumentos.
	 */
	public Boolean grantedSimplePermission(String permissionName, Authentication authentication) {
		GrantedAuthority[] grantedAuthorities = authentication.getAuthorities();
		for (GrantedAuthority authority : grantedAuthorities) {
			if(authority instanceof GrantedSimplePermission
				&& ((GrantedSimplePermission)authority).authorityDescriptionIs(permissionName)) {
				return true;
			}			
		}
		return false;
	}
	
	/**
	 * Determinas si el usuario tiene el permiso con el nombre dado y ese permiso
	 * es aplicable al instrumento dado. Si el instrumento pasado es null, devuelve
	 * lo mismo que grantedSimplePermission.
	 */
	public Boolean grantedPermissionByInstrumento(String permissionName, Long idInstrumento, Authentication authentication) {
		if(idInstrumento!=null) {
			GrantedAuthority[] grantedAuthorities = authentication.getAuthorities();
			for (GrantedAuthority authority : grantedAuthorities) {
				if(authority instanceof GrantedPermissionByInstrumento) {
					GrantedPermissionByInstrumento fontarGrantedAuthority = (GrantedPermissionByInstrumento)authority;
					if(
							fontarGrantedAuthority.authorityDescriptionIs(permissionName) &&
							fontarGrantedAuthority.getIdInstrumento().equals(idInstrumento))
						return true;
				}
			}
		}
		return grantedSimplePermission(permissionName, authentication);
	}

	public Boolean grantedAttribute(FontarSecurityConfig attribute) {
		return grantedAttribute(attribute, currentAuthentication());
	}

	private Authentication currentAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public Boolean grantedAttribute(FontarSecurityConfig attribute, Authentication authentication) {
		//Horrible, pero que le vamos a hacer...
		
		switch (attribute.getType()) {
		case SIMPLE_PERMISSION:
			return grantedSimplePermission(attribute.getName(), authentication);
		case PERMISSION_BY_INSTRUMENTO:
			return grantedPermissionByInstrumento(attribute.getName(), ((InstrumentoSecurityConfig)attribute).getIdInstrumento(), authentication);
		default:
			throw new IllegalArgumentException("Configuracion de seguridad no implementada: " + attribute);
		}
	}

	public Boolean grantedSimplePermission(String permissionName) {
		return grantedSimplePermission(permissionName, currentAuthentication());
	}

	public Boolean grantedPermissionByInstrumento(String permissionName, Long idInstrumento) {
		return grantedPermissionByInstrumento(permissionName, idInstrumento, currentAuthentication());
	}

	public Boolean grantedPermissionForAnyInstrumento(String permissionName) {
		return grantedPermissionForAnyInstrumento(permissionName, currentAuthentication());
	}

	public Boolean grantedPermissionForAnyInstrumento(String permissionName, Authentication authentication) {
		GrantedAuthority[] grantedAuthorities = authentication.getAuthorities();
		for (GrantedAuthority authority : grantedAuthorities) {
			if(authority instanceof FontarGrantedAuthority
					&& ((FontarGrantedAuthority)authority).authorityDescriptionIs(permissionName)) {
					return true;
				}
		}
		return false;
	}
}
