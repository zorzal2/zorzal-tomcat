package com.fontar.web.decorator.link.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;

public class AdministrarBitacoraLink extends BaseFontarObjectTargetLink {

	static private final String IMG = "images/tareas.gif";
	
	private String clase;
	
	@Override
	protected String getImageSource() {
		return IMG;
	}

	public AdministrarBitacoraLink(String target, String description, Long id) {
		super(target, description, id);
	}
	
	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	@Override
	protected String displayValueImpl() throws UnsupportedEncodingException {
		StringBuffer link = new StringBuffer();
		link.append("<a href=\"");
		link.append(this.getTarget());
		link.append("?id=");
		link.append( URLEncoder.encode(String.valueOf(this.getId()), "UTF-8"));
		link.append("&clase=");
		link.append(this.getClase());
		link.append("\"><img src='");
		link.append(getImageSource());
		link.append("' title=");
		link.append("'" + this.getDescription() + "' border=0 /></a>");
		return link.toString();
	}
	
}
