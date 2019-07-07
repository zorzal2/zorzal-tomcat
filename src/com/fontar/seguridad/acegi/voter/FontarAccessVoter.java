package com.fontar.seguridad.acegi.voter;

import java.util.Iterator;
import java.util.Set;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.vote.AccessDecisionVoter;

import com.fontar.seguridad.AuthorizationService;
import com.fontar.seguridad.FontarSecurityAttributeType;
import com.fontar.seguridad.acegi.FontarSecurityConfig;
import com.pragma.util.ContextUtil;

/**
 * Votante básico que opina solo sobre ConfigAttributes de clase
 * FontarSecurityConfig de tipos especificos. En caso de que no 
 * haya atributos de seguridad del tipo que le interesan se abstiene.
 * Si hay algun atributo que le interesa lo evalua. Si alguno que le
 * interesa tiene acceso garantizado acepta. Si ninguno de los que le
 * interesan esta garantizado deniega.
 * @author llobeto
 *
 */

public abstract class FontarAccessVoter implements AccessDecisionVoter {

	private AuthorizationService authorizationService;

	public boolean supports(Class clazz) {
		Set<Class> supportedClasses = getSupportedClasses();
		return supportedClasses==null || supportedClasses.contains(clazz);
	}

	@SuppressWarnings("unchecked")
	public int vote(Authentication authentication, Object object, ConfigAttributeDefinition config) {
		//Puedo votar sobre el objeto dado?
		if(!supports(object.getClass())) return ACCESS_ABSTAIN;

		Iterator<ConfigAttribute> iter = config.getConfigAttributes();
		while (iter.hasNext()) {
			ConfigAttribute attribute = iter.next();
			if (this.supports(attribute)) {
				if (this.grantedAccess( authentication, object, (FontarSecurityConfig) attribute)) 
					return ACCESS_GRANTED;
			}
		}
		return ACCESS_DENIED;
	}
	/**
	 * Overridable.
	 * @param authentication
	 * @param object
	 * @param config
	 * @return
	 */
	protected Boolean grantedAccess(Authentication authentication, Object object, FontarSecurityConfig config) {
		return grantedAccess(authentication, config);
	}
	protected abstract Boolean grantedAccess(Authentication authentication, FontarSecurityConfig config);
	/**
	 * Overridable.
	 * Determina el subconjunto de clases que este objeto puede manejar. Si no
	 * tiene limitaciones dadas por las clases debe devolver null. 
	 * @return
	 */
	protected Set<Class> getSupportedClasses() {
		return null;
	}

	protected AuthorizationService getAuthorizationService() {
		if(authorizationService==null) 
			authorizationService = (AuthorizationService)ContextUtil.getBean("authorizationService"); 
		return authorizationService;
	}

	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}
	public boolean supports(ConfigAttribute attribute) {
		if(attribute instanceof FontarSecurityConfig){
			FontarSecurityConfig config = (FontarSecurityConfig) attribute;
			if(supportsAttributeType(config.getType())) return true;
			else return false;
		} else return false;
	}
	protected abstract boolean supportsAttributeType(FontarSecurityAttributeType attributeType);
}