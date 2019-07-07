package com.fontar.web.decorator.link.impl.base;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.acegisecurity.ConfigAttribute;

import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.link.api.AbstractLink;
import com.fontar.web.seguridad.displayTag.TableDecoratorAccessController;
import com.pragma.toolbar.util.CollectionUtils;
import com.pragma.util.ContextUtil;

public abstract class BaseAbstractLink  implements AbstractLink {

	private String target;
	private boolean asPopup = false;
	
	private Set<ConfigAttribute> securityAttributeRequired = null;
	
	private String descriptionCode;
	
	public BaseAbstractLink(String target, String descriptionCode) {
		super();
		this.target = target;
		this.descriptionCode = descriptionCode;
	}

	/* (non-Javadoc)
	 * @see com.fontar.web.decorator.impl.AbstractLink2#getDescriptionCode()
	 */
	public String getDescriptionCode() {
		return descriptionCode;
	}

	/* (non-Javadoc)
	 * @see com.fontar.web.decorator.impl.AbstractLink2#setDescriptionCode(java.lang.String)
	 */
	public void setDescriptionCode(String descriptionCode) {
		this.descriptionCode = descriptionCode;
	}

	/* (non-Javadoc)
	 * @see com.fontar.web.decorator.impl.AbstractLink2#getTarget()
	 */
	public String getTarget() {
		return target;
	}

	/* (non-Javadoc)
	 * @see com.fontar.web.decorator.impl.AbstractLink2#setTarget(java.lang.String)
	 */
	public void setTarget(String target) {
		this.target = target;
	}


	/* (non-Javadoc)
	 * @see com.fontar.web.decorator.impl.AbstractLink2#getSecurityAttributesRequired()
	 */
	public Set<ConfigAttribute> getSecurityAttributesRequired() {
		return securityAttributeRequired;
	}
	
	public void setSecurityAttributesRequired(ConfigAttribute... attributes) {
		securityAttributeRequired = com.pragma.util.CollectionUtils.setWith(attributes);
	}
	
	public void addSecurityAttributeRequired(ConfigAttribute attribute) {
		if (securityAttributeRequired==null) 
			this.setSecurityAttributesRequired(attribute);
		else
			securityAttributeRequired.add(attribute);
	}
	
	public void removeSecurityAttributeRequired() {
		securityAttributeRequired = null;
	}

	/* (non-Javadoc)
	 * @see com.fontar.web.decorator.impl.AbstractLink2#getDescription()
	 */
	public String getDescription(){
		return ResourceManager.getAltResource( this.getDescriptionCode() );
	}
	
	protected abstract String displayValueImpl() throws UnsupportedEncodingException;
	
	/* (non-Javadoc)
	 * @see com.fontar.web.decorator.impl.AbstractLink2#displayValue()
	 */
	public String displayValue(){
		if( this.isAllowed()){
			try {
				return this.displayValueImpl();
			}catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return " ";
	}
	
	private Boolean isAllowed(){
		return this.getAccessController().decide( this );
	}

	private TableDecoratorAccessController getAccessController(){
		return (TableDecoratorAccessController) ContextUtil.getBean("tableDecoratorAccessController");
	}

	public AbstractLink openAsPopup() {
		asPopup = true;
		return this;
	}
	public boolean isPopup() {
		return asPopup;
	}	
}
