package com.fontar.web.decorator.link.impl;

import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;


public class AnularLink extends BaseFontarObjectTargetLink {

	static private final String IMG = "images/eliminar.gif";
	
	public AnularLink(String target, String description, Long id) {
		super(target, description, id);
	}

	@Override
	protected String getImageSource() {
		return AnularLink.IMG;
	}

	
	
}
