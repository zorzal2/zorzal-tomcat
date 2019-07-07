package com.fontar.web.decorator.link.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;


public class EditarJavaScriptLink extends BaseFontarObjectTargetLink {

	static private final String IMG = "images/edicion.gif";
	
	public EditarJavaScriptLink(String target, String description, Long id) {
		super(target, description, id);
	}
	
	@Override
	protected String getImageSource() {
		return EditarJavaScriptLink.IMG;
	}

	@Override
	public String displayValueImpl() throws UnsupportedEncodingException {
		StringBuffer link = new StringBuffer();
		
		link.append("<a href=\"#\" ");
		link.append("onclick=\"javascript:");
		link.append(this.getTarget()); 
		link.append("('");
		link.append(URLEncoder.encode(String.valueOf(this.getId()), "UTF-8"));
		link.append("');\" >");
		link.append("<img class=\"imageButton\" src='images/edicion.gif' ");
		link.append("title='" + this.getDescription() + "' border=0 /></a>");

		return link.toString();
	}
}
