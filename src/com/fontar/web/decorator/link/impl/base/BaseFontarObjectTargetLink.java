package com.fontar.web.decorator.link.impl.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.web.decorator.link.api.FontarObjectTargetLink;

public abstract class BaseFontarObjectTargetLink extends BaseFontarRestrictedLink implements FontarObjectTargetLink {

	private String id;
	
	public BaseFontarObjectTargetLink(String target, String description, Long id) {
		this(target, description, String.valueOf(id));
	}
	
	public BaseFontarObjectTargetLink(String target, String description, String id) {
		super(target, description);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	protected String displayValueImpl() throws UnsupportedEncodingException {
		
		String url = 	this.getTarget()
					+	(this.getTarget().contains("?")? '&':'?')
					+	"id="
					+	URLEncoder.encode(this.getId(), "UTF-8");
		
		StringBuilder link = new StringBuilder();
		link.append("<a href=\"");
		if(isPopup()) popupUrl(url, link);
		else link.append(url);
		link.append("\"><img class=\"imageButton\" src='");
		link.append(getImageSource());
		link.append("' title=");
		link.append("'" + this.getDescription() + "' border=0 /></a>");
		return link.toString();
	}
	
	protected abstract String getImageSource();
	
	protected String popupUrl(String url) {
		StringBuilder builder = new StringBuilder();
		popupUrl(url, builder);
		return builder.toString();
	}
	
	protected void popupUrl(String url, StringBuilder builder) {
		builder.append("javascript: AbrirPopup('");
		builder.append(url);
		builder.append("','600','350',false); undefined;");
	}
}
