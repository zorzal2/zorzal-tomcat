package com.fontar.web.decorator.link.impl;

import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;



public class AdjuntosLink extends BaseFontarObjectTargetLink {

	static private final String IMG = "images/adjuntos.gif";
	
	public AdjuntosLink(Long id, Class clazz) {
		super(clazz.getSimpleName() + "InventarioAdjuntoAction.do", "app.alt.administrarAdjuntos", id);
	}

	@Override
	protected String getImageSource() {
		return AdjuntosLink.IMG;
	}

}
