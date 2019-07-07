package com.fontar.web.decorator.link.impl.base;

import org.acegisecurity.ConfigAttribute;

import com.fontar.seguridad.acegi.InstrumentoSecurityConfig;
import com.fontar.seguridad.acegi.SimplePermissionSecurityConfig;
import com.fontar.web.decorator.link.api.FontarRestrictedLink;

public abstract class BaseFontarRestrictedLink extends BaseAbstractLink implements FontarRestrictedLink {

	public BaseFontarRestrictedLink(String target, String description) {
		super(target, description);
	}
	/* (non-Javadoc)
	 * @see com.fontar.web.decorator.impl.AbstractLink2#addSimplePermissionsRequired(java.lang.String)
	 */
	public void setSimplePermissionsRequired(String... permissionsRequired) {
		ConfigAttribute permissions[] = new ConfigAttribute[permissionsRequired.length]; 
		for (int i = 0; i < permissions.length; i++) {
			permissions[i] = new SimplePermissionSecurityConfig(permissionsRequired[i]);
		}
		setSecurityAttributesRequired(permissions);
	}
	/* (non-Javadoc)
	 * @see com.fontar.web.decorator.impl.AbstractLink2#addPermissionsByInstrumentoRequired(java.lang.Long, java.lang.String)
	*/
	public void setPermissionsByInstrumentoRequired(Long idInstrumento, String... permissionsRequired) {
		if(idInstrumento==null) setSimplePermissionsRequired(permissionsRequired);
		else{
			ConfigAttribute permissions[] = new ConfigAttribute[permissionsRequired.length]; 
			for (int i = 0; i < permissions.length; i++) {
				permissions[i] = new InstrumentoSecurityConfig(permissionsRequired[i], idInstrumento);
			}
			setSecurityAttributesRequired(permissions);
		}
	}
}
