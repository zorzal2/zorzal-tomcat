package com.fontar.seguridad.acegi.voter;

import org.acegisecurity.Authentication;

import com.fontar.seguridad.FontarSecurityAttributeType;
import com.fontar.seguridad.acegi.FontarSecurityConfig;

public class InventarioAccessVoter extends FontarAccessVoter {

	@Override
	protected Boolean grantedAccess(Authentication authentication, FontarSecurityConfig config) {
		return getAuthorizationService().grantedPermissionForAnyInstrumento(config.getName(), authentication);
	}

	@Override
	protected boolean supportsAttributeType(FontarSecurityAttributeType attributeType) {
		return true;
	}

}
