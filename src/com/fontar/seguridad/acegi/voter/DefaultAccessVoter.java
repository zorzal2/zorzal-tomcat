package com.fontar.seguridad.acegi.voter;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.vote.AccessDecisionVoter;

/**
 * Votante que acepta si no hay ningun atributo de seguridad. Si hay al menos
 * un atributo se abstiene para dejarle la posibilidad de aceptar al resto.
 * @author llobeto
 *
 */
public final class DefaultAccessVoter implements AccessDecisionVoter {

	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	public boolean supports(Class arg0) {
		return true;
	}

	public int vote(Authentication arg0, Object arg1, ConfigAttributeDefinition arg2) {
		return (arg2.size()==0) ? ACCESS_GRANTED : ACCESS_ABSTAIN;
	}
}
