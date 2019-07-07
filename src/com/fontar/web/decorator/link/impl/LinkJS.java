package com.fontar.web.decorator.link.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.fontar.util.ResourceManager;
import com.fontar.web.decorator.link.impl.base.BaseFontarObjectTargetLink;

public class LinkJS extends BaseFontarObjectTargetLink{

private String IMG = "";
private String toolTip = "";
private String ActionOnClick = "";

	public LinkJS(String actionOnClick, String descriptionCode, String image) {
		super("", descriptionCode, "0");
		this.IMG = image;
		this.ActionOnClick = actionOnClick;
	}

	@Override
	protected String getImageSource() {
		return IMG;
	}

	
	protected String getActionOnClick(){
		return ActionOnClick;
	}
	
	@Override
	public String displayValueImpl() throws UnsupportedEncodingException {
		StringBuilder link = new StringBuilder();
		
		link.append("<img class=\"imageButton\" src='");
		link.append(this.getImageSource());
		link.append("' onClick='" + this.getActionOnClick());
		link.append("' title=");
		link.append("'" + this.getDescription() + "' border=0 />");
		return link.toString();		
	}
	
	@Override
	public String displayValue(){
		return super.displayValue();
	}
	


}

