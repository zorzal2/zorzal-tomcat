package com.fontar.seguridad.acegi.voter;

import org.acegisecurity.Authentication;

import com.fontar.seguridad.FontarSecurityAttributeType;
import com.fontar.seguridad.acegi.FontarSecurityConfig;
import com.fontar.seguridad.acegi.InstrumentoSecurityConfig;

/**
 * Vota sobre permisos por instrumento. Se abstiene en otros casos.
 * @author llobeto
 *
 */
public final class InstrumentoAccessVoter extends FontarAccessVoter {

	@Override
	protected Boolean grantedAccess(Authentication authentication, FontarSecurityConfig config) {
		return getAuthorizationService()
			.grantedPermissionByInstrumento(					
					config.getName(), 
					((InstrumentoSecurityConfig)config).getIdInstrumento(), 
					authentication);
	}

	@Override
	protected boolean supportsAttributeType(FontarSecurityAttributeType attributeType) {
		return FontarSecurityAttributeType.PERMISSION_BY_INSTRUMENTO==attributeType;
	}
}
