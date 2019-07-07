package com.fontar.web.decorator.link.impl;

import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;


public class PedirDesembolsoLink extends BaseFontarObjectTargetLink {

	static private final String IMG = "images/pedir_desembolso.gif";
	
	public PedirDesembolsoLink(String target, String description, Long id) {
		super(target, description, id);
	}

	@Override
	protected String getImageSource() {
		return PedirDesembolsoLink.IMG;
	}

	
	
}
