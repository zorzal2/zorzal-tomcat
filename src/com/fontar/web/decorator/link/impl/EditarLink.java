package com.fontar.web.decorator.link.impl;

import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;


public class EditarLink extends BaseFontarObjectTargetLink {

	static private final String IMG = "images/edicion.gif";
	
	public EditarLink(String target, String description, Long id) {
		super(target, description, id);
	}
	
	public EditarLink(String target, String description, String id) {
		super(target, description, id);
	}

	@Override
	protected String getImageSource() {
		return EditarLink.IMG;
	}
}
