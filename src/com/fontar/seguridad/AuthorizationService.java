package com.fontar.seguridad;

import org.acegisecurity.Authentication;

import com.fontar.seguridad.acegi.FontarSecurityConfig;


public interface AuthorizationService {

	public abstract String[] getPooledActors(String idActionWorkflow, Long idInstrumento);
	/**
	 * Determina si el usuario actual tiene los provilegios determinados por el atributo
	 * de configuracion dado.
	 * @param attribute
	 * @return
	 */
	public abstract Boolean grantedAttribute(FontarSecurityConfig attribute);
	/**
	 * Determina si el objeto authentificacion especificado tiene los privilegios 
	 * determinados por el atributo
	 * de configuracion dado.
	 * @param attribute
	 * @param authentication
	 * @return
	 */
	public abstract Boolean grantedAttribute(FontarSecurityConfig attribute, Authentication authentication);
	
	public abstract Boolean grantedSimplePermission(String permissionName, Authentication authentication);
	
	public abstract Boolean grantedPermissionByInstrumento(String permissionName, Long idInstrumento, Authentication authentication);
	
	public abstract Boolean grantedSimplePermission(String permissionName);
	
	public abstract Boolean grantedPermissionByInstrumento(String permissionName, Long idInstrumento);
	
	public abstract Boolean grantedPermissionForAnyInstrumento(String permissionName);

	public abstract Boolean grantedPermissionForAnyInstrumento(String permissionName, Authentication authentication);
}