package com.fontar.web.decorator.link.impl;

import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;

public class ResetClaveLink extends BaseFontarObjectTargetLink {

	static private final String IMG = "images/cambiarContrasenia.gif";
	
	public ResetClaveLink(String target, String description, Long id) {
		super(target, description, id);
	}
	
	public ResetClaveLink(String target, String description, String id) {
		super(target, description, id);
	}

	@Override
	protected String getImageSource() {
		return IMG;
	}
}
