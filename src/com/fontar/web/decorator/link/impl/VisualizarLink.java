package com.fontar.web.decorator.link.impl;

import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;



public class VisualizarLink extends BaseFontarObjectTargetLink {

	static private final String IMG = "images/visualizar.gif";
	
	public VisualizarLink(String target, String description, Long id) {
		super(target, description, id);
	}
	
	public VisualizarLink(String target, String description, String id) {
		super(target, description, id);
	}

	@Override
	protected String getImageSource() {
		return VisualizarLink.IMG;
	}

	public void setSimplePermissionsRequired(String permissionRequired) {
		// TODO Auto-generated method stub
		
	}

}
