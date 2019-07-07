package com.fontar.web.decorator.link.api;

import com.fontar.web.seguridad.displayTag.RestrictedLink;

public interface AbstractLink extends RestrictedLink {

	public abstract String getDescriptionCode();

	public abstract void setDescriptionCode(String descriptionCode);

	public abstract String getTarget();

	public abstract void setTarget(String target);

	public abstract String getDescription();

	public abstract String displayValue();

	public abstract AbstractLink openAsPopup();
	
	public abstract boolean isPopup();
}