package com.fontar.web.decorator.link.impl;

import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;


public class PagarLink extends BaseFontarObjectTargetLink {

	static private final String IMG = "images/pagar.gif";
	
	public PagarLink(String target, String description, Long id) {
		super(target, description, id);
	}

	@Override
	protected String getImageSource() {
		return PagarLink.IMG;
	}

	
	
}
