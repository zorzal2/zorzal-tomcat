package com.fontar.seguridad.acegi.voter;

import org.acegisecurity.Authentication;

import com.fontar.seguridad.FontarSecurityAttributeType;
import com.fontar.seguridad.acegi.FontarSecurityConfig;

/**
 * Vota sobre permisos simples. Se abstiene en cualquier otro caso.
 * @author llobeto
 *
 */
public final class SimplePermissionAccessVoter extends FontarAccessVoter {

	@Override
	protected Boolean grantedAccess(Authentication authentication, FontarSecurityConfig config) {
		return getAuthorizationService()
			.grantedSimplePermission(
					config.getName(), 
					authentication);
	}

	@Override
	protected boolean supportsAttributeType(FontarSecurityAttributeType attributeType) {
		return FontarSecurityAttributeType.SIMPLE_PERMISSION==attributeType;
	}
}
